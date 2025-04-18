package com.gabriel_barros.controle_entregua_agua.domain.usecase.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido


interface PedidoQueryBuilder{
    fun getPedidoById(id: Long): PedidoQueryBuilder

    fun getPedidoByClienteId(clienteId: Long): PedidoQueryBuilder

    fun getPedidoByStatus(statusPedido: StatusPedido): PedidoQueryBuilder

    fun getAllPedidos(): PedidoQueryBuilder

    suspend fun buildExecuteAsSingle(): Pedido
    suspend fun buildExecuteAsSList(): List<Pedido>
}

interface ItemPedidoQueryBuilder{
    fun getAllItensByPedidoId(pedidoId: Long): ItemPedidoQueryBuilder

    suspend fun buildExecuteAsSList(): List<ItensPedido>
}
