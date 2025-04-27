package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.entity.StatusPedido


interface PedidoQueryBuilder{
    fun getPedidoById(vararg id: Long): PedidoQueryBuilder

    fun getPedidoByClienteId(vararg clienteId: Long): PedidoQueryBuilder

    fun getPedidoByStatus(statusPedido: StatusPedido): PedidoQueryBuilder

    fun getAllPedidos(): PedidoQueryBuilder

    suspend fun buildExecuteAsSingle(): PedidoEntity
    suspend fun buildExecuteAsSList(): List<PedidoEntity>
}

interface ItemPedidoQueryBuilder{
    fun getAllItensByPedidoId(pedidoId: Long): ItemPedidoQueryBuilder

    suspend fun buildExecuteAsSList(): List<ItensPedido>
}
