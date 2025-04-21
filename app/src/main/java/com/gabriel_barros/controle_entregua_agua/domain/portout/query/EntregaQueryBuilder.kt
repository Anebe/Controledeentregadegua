package com.gabriel_barros.controle_entregua_agua.domain.portout.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega

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