package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido

interface PedidoUseCase {
    fun getPedidoById(id: Long): Pedido?

    fun getPedidoByClienteId(clienteId: Long): List<Pedido>

    fun getAllPedidos(): List<Pedido>

    fun savePedido(pedido: Pedido, itensPedido: Set<ItensPedido>): Pedido?

    fun deletePedido(id: Long): Pedido?
}