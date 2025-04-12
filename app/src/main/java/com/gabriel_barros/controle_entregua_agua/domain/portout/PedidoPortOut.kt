package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido

interface PedidoPortOut {
    suspend fun getPedidoById(id: Long): Pedido?

//    suspend fun getPedidoByIdWithCliente(id: Long): Pedido?

    suspend fun getPedidoByClienteId(clienteId: Long): List<Pedido>

    suspend fun getAllPedidos(): List<Pedido>

    suspend fun getAllPedidosAsync(callback: (List<Pedido>) -> Unit)

    suspend fun getPedidosPendentes(): List<Pedido>

    suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido>

//    suspend fun getAllPedidosWithClientes(): List<Pedido>

    suspend fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido?

    suspend fun deletePedido(id: Long): Pedido?
}