package com.gabriel_barros.domain_portout.query

import com.gabriel_barros.domain.entity.Entrega
import com.gabriel_barros.domain.entity.ItensEntrega

interface EntregaQueryBuilder {
    fun getEntregaById(vararg entregaId: Long): EntregaQueryBuilder
    fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder
    fun getAllEntregas(): EntregaQueryBuilder
    fun getAllEntregasByFrete(pedidoId: Long): EntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): Entrega
    suspend fun buildExecuteAsSList(): List<Entrega>
}

interface ItemEntregaQueryBuilder {

    fun getAllItensByEntregaId(vararg entregaId: Long): ItemEntregaQueryBuilder
    fun getAllItensByItemPedidoId(vararg itemPedidoId: Long): ItemEntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): ItensEntrega
    suspend fun buildExecuteAsSList(): List<ItensEntrega>
}