package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega

interface EntregaQueryBuilder {
    fun getEntregaById(vararg entregaId: Long): EntregaQueryBuilder
    fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder
    fun getAllEntregas(): EntregaQueryBuilder
    fun getAllEntregasByPedido(pedidoId: Long): EntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): Entrega
    suspend fun buildExecuteAsSList(): List<Entrega>
}

interface ItemEntregaQueryBuilder {

    fun getAllItensByEntregaId(vararg entregaId: Long): ItemEntregaQueryBuilder
    fun getAllItensByItemPedidoId(vararg itemPedidoId: Long): ItemEntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): ItensEntrega
    suspend fun buildExecuteAsSList(): List<ItensEntrega>
}