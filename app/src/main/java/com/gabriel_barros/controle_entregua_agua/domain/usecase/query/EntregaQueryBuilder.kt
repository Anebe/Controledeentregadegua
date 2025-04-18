package com.gabriel_barros.controle_entregua_agua.domain.usecase.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega

interface EntregaQueryBuilder {
    fun getEntregaById(entregaId: Long): EntregaQueryBuilder
    fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder
    fun getAllEntregas(): EntregaQueryBuilder
    fun getAllEntregasByPedido(pedidoId: Long): EntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): Entrega
    suspend fun buildExecuteAsSList(): List<Entrega>
}

interface ItemEntregaQueryBuilder {

    fun getAllItensByEntregaId(entregaId: Long): EntregaQueryBuilder

    suspend fun buildExecuteAsSingle(): ItensEntrega
    suspend fun buildExecuteAsSList(): List<ItensEntrega>
}