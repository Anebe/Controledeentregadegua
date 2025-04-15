package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase

class EntregaService(
    private val entregaOut: EntregaPortOut,
    private val produtoService: ProdutoUseCase,
    private val pedidoService: PedidoUseCase,
): EntregaUseCase {
    override suspend fun getEntregaById(id: Long): Entrega? {
        return entregaOut.getEntregaById(id)
    }

    override suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        return entregaOut.getEntregaByPedidoId(pedidoId)
    }

    override suspend fun getAllEntregas(): List<Entrega> {
        return entregaOut.getAllEntregas()
    }

    override suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega> {
        return entregaOut.getAllItensByEntregaId(entregaId)
    }

    override suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega> {
        return entregaOut.getAllEntregasByPedido(pedidoId)
    }

    override suspend fun saveEntrega(entrega: Entrega, produtosEntregues: List<ItensEntrega>): Entrega? {
        //TODO adicionar logica pra produtos retornaveis(galão seco)
        val itensDoPedido = pedidoService.getAllItensByPedidoId(entrega.pedido_id)
        val entregasOfActualPedido = this.getAllEntregasByPedido(entrega.pedido_id)
        //Valida Entrega
        val isFutureEntrega = entrega.data.isAfter(java.time.LocalDate.now())
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
                val itensEntrega = this.getAllItensByEntregaId(entrega.id)
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
            return null
        }

        //Salva
        val entregaSalva = entregaOut.saveEntrega(entrega, produtosEntregues)

        //Atualiza Estoque
        itensDoPedido.forEach { itemDoPedido ->
            var produtoOutDated = produtoService.getProdutoById(itemDoPedido.produto_id)
            val produtoEntregue = produtosEntregues.find { prodEntregue -> prodEntregue.itemPedido_id == itemDoPedido.id}
            if(produtoEntregue != null && produtoOutDated != null){
                val newEstoque = produtoOutDated.estoque - produtoEntregue.qtdEntregue
                produtoOutDated = produtoOutDated.copy(estoque = newEstoque)
                produtoService.saveProduto(produtoOutDated)
            }
        }
        return entregaSalva
    }


    override suspend fun deleteEntrega(id: Long): Entrega? {
        return entregaOut.deleteEntrega(id)
    }
}