package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase

class PedidoService(
    private val pedidoOut: PedidoPortOut
) :PedidoUseCase{

    override fun getPedidoById(id: Long): Pedido? {
        return pedidoOut.getPedidoById(id)
    }



    override fun getPedidoByClienteId(clienteId: Long): List<Pedido> {
        return pedidoOut.getPedidoByClienteId(clienteId)
    }

    override fun getAllPedidos(): List<Pedido> {
        return pedidoOut.getAllPedidos()
    }

    override fun savePedido(pedido: Pedido, itensPedido: Set<ItensPedido>): Pedido? {
        if (itensPedido.isEmpty() || pedido.troco < 0){
            return null
        }
        return pedidoOut.savePedido(pedido, itensPedido)
    }


    override fun deletePedido(id: Long): Pedido? {
        return pedidoOut.deletePedido(id)
    }
}