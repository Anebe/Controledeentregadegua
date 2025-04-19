package com.gabriel_barros.controle_entregua_agua.domain.usecase.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido

@Deprecated("Subsituido por Manager e Query Builder")
interface PedidoUseCase {
    suspend fun getPedidoById(id: Long): Pedido?

    suspend fun getPedidoByClienteId(clienteId: Long): List<Pedido>

    suspend fun getPedidosPendentes(): List<Pedido>

    suspend fun verificaPedidoEFinaliza(id: Long)

    suspend fun getAllPedidos(): List<Pedido>

    suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido>

    suspend fun savePedido(pedido: Pedido, itensPedido: Set<ItensPedido>): Pedido?

    suspend fun deletePedido(id: Long): Pedido?
}


