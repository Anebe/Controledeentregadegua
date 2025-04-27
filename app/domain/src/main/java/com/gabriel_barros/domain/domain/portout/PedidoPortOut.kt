package com.gabriel_barros.domain.domain.portout

import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PedidoEntity

interface PedidoPortOut {
    suspend fun savePedido(pedido: PedidoEntity, itens: Set<ItensPedido>): PedidoEntity

    suspend fun deletePedido(id: Long): PedidoEntity
}

@Deprecated("Substituido por Query Builder")
interface PedidoGet {
    suspend fun getPedidoById(id: Long): PedidoEntity?

//    suspend fun getPedidoByIdWithCliente(id: Long): Pedido?

    suspend fun getPedidoByClienteId(clienteId: Long): List<PedidoEntity>

    suspend fun getAllPedidos(): List<PedidoEntity>

    suspend fun getPedidosPendentes(): List<PedidoEntity>

    suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido>

//    suspend fun getAllPedidosWithClientes(): List<Pedido>
}