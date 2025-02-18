package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido

interface PedidoPortOut {
    fun getPedidoById(id: Long): Pedido?

//    fun getPedidoByIdWithCliente(id: Long): Pedido?

    fun getPedidoByClienteId(clienteId: Long): List<Pedido>

    fun getAllPedidos(): List<Pedido>

//    fun getAllPedidosWithClientes(): List<Pedido>

    fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido?

    fun deletePedido(id: Long): Pedido?
}