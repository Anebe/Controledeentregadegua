package com.gabriel_barros.controle_entregua_agua.domain.service.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.error.BadRequestException
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoManager
import java.time.LocalDate

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
        //TODO adicionar logica pra produtos retornaveis(galão seco)
        val itensDoPedido = itemPedidoQuery.getAllItensByPedidoId(entrega.pedido_id).buildExecuteAsSList()
        val entregasOfActualPedido = entregaQuery.getAllEntregasByPedido(entrega.pedido_id).buildExecuteAsSList()
        //Valida Entrega
        val isFutureEntrega = entrega.data.isAfter(LocalDate.now())
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
            isFutureEntrega ||
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
}