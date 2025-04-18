package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido

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

interface PedidoManager{
    data class PedidoDTO(val clienteId: Long, val itensDoPedido: List<ItemDoPedido>)
    data class ItemDoPedido(val qtd: Int, val produtoId: Long)

    suspend fun makePedido(pedido: PedidoDTO): Pedido?
    suspend fun checkAndFinalizePedido(pedidoId: Long): Pedido?
    suspend fun validateStockForPedido(itens: List<ItensPedido>): Boolean

}
