package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega
import com.gabriel_barros.domain.domain.entity.StatusEntrega
import com.gabriel_barros.domain.domain.entity.TipoEntregador
import com.gabriel_barros.domain.domain.error.BadRequestException
import com.gabriel_barros.domain.domain.portout.EntregaPortOut
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import java.time.LocalDate

//TODO adicionar logica pra produtos retornaveis(galão seco)
class EntregaManagerImp(
    private val entregaOut: EntregaPortOut,
    private val produtoQuery: ProdutoQueryBuilder,
    private val produtoService: ProdutoManager,
    private val pedidoQuery: PedidoQueryBuilder,
    private val itemPedidoQuery: ItemPedidoQueryBuilder,
    private val entregaQuery: EntregaQueryBuilder,
    private val itemEntregaQuery: ItemEntregaQueryBuilder,
): EntregaManager {


    override suspend fun registerEntrega(
        entrega: Entrega,
        produtosEntregues: List<ItensEntrega>
    ): Entrega {
        val itensDoPedido = itemPedidoQuery.getAllItensByPedidoId(entrega.pedido_id).buildExecuteAsSList()
        val entregasOfActualPedido = entregaQuery.getAllEntregasByPedido(entrega.pedido_id).buildExecuteAsSList()
        //Valida Entrega
        val isFutureDataEntrega = entrega.data.isAfter(LocalDate.now())
        produtosEntregues.isEmpty()
        val allPositiveAndAtLeastOne = produtosEntregues.all {
            (it.qtdEntregue >= 0 && it.qtdRetornado >= 0) &&
            (it.qtdEntregue > 0 || it.qtdRetornado > 0)
        }
        var isOverFlowQtdItensPedido = false
        itensDoPedido.forEach { item ->
            val qtdDesejado = item.qtd
            var qtdEntregue = 0
            entregasOfActualPedido.forEach { entrega ->
                val itensEntrega = itemEntregaQuery.getAllItensByEntregaId(entrega.id).buildExecuteAsSList()
                qtdEntregue += itensEntrega
                    .filter { itemEntrega -> itemEntrega.itemPedido_id == item.id }
                    .sumOf { it.qtdEntregue }
            }
            val entregaAtual = produtosEntregues.find { item.id == it.itemPedido_id }
            qtdEntregue += entregaAtual?.qtdEntregue ?: 0
            if (qtdDesejado < qtdEntregue)
                isOverFlowQtdItensPedido = true
        }


        if(produtosEntregues.isEmpty() ||
            !allPositiveAndAtLeastOne ||
            isFutureDataEntrega ||
            isOverFlowQtdItensPedido){
            throw BadRequestException("Não foi possível adicionar entrega")
        }

        //Salva
        val entregaSalva = entregaOut.saveEntrega(entrega, produtosEntregues)

        //Atualiza Estoque
        itensDoPedido.forEach { itemDoPedido ->
            var produtoOutDated = produtoQuery.getProdutoById(itemDoPedido.produto_id).buildExecuteAsSingle()
            val produtoEntregue = produtosEntregues.find { prodEntregue -> prodEntregue.itemPedido_id == itemDoPedido.id}
            if(produtoEntregue != null){
                val newEstoque = produtoOutDated.estoque - produtoEntregue.qtdEntregue
                produtoOutDated = produtoOutDated.copy(estoque = newEstoque)
                produtoService.registerProduto(produtoOutDated)
            }
        }
        return entregaSalva
    }

    override suspend fun registerCompleteEntrega(pedidoId: Long): Entrega {
        val itensPedidos = itemPedidoQuery
            .getAllItensByPedidoId(pedidoId)
            .buildExecuteAsSList()

        val itensEntregues = itemEntregaQuery
            .getAllItensByItemPedidoId(*itensPedidos
                .map { it.id }.toLongArray()).buildExecuteAsSList()

        val entregaParaRegistro = Entrega(
            data = LocalDate.now(),
            pedido_id = pedidoId,
            status = StatusEntrega.FINALIZADO,
            entregador = TipoEntregador.COMERCIO
        )

        val itensEntregasParaRegistro = mutableListOf<ItensEntrega>()
        itensPedidos.forEach { itemPedido ->
            val qtdFaltaEntregar = itemPedido.qtd - itensEntregues
                .filter { it.itemPedido_id == itemPedido.id }
                .sumOf { it.qtdEntregue }
            itensEntregasParaRegistro.add(ItensEntrega(
                itemPedido_id = itemPedido.id,
                qtdEntregue = qtdFaltaEntregar,
                //TODO
                qtdRetornado = 0))
        }

        return entregaOut.saveEntrega(entregaParaRegistro, itensEntregasParaRegistro)
    }
}