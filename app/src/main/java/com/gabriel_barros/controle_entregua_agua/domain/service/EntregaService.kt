package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase

class EntregaService(
    private val entregaOut: EntregaPortOut,
    private val produtoService: ProdutoUseCase,
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
        //TODO atualizar estoque
        //TODO criar um get produtos of pedido

//        val produtos = produtosEntregues.map { produtoService.getProdutoById(it.) }
//        return entregaOut.saveEntrega(entrega, produtosEntregues)
        return null
    }


    override suspend fun deleteEntrega(id: Long): Entrega? {
        return entregaOut.deleteEntrega(id)
    }
}