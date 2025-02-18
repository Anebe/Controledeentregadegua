package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega

interface EntregaPortOut {

    fun getEntregaById(id: Long): Entrega?

    fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    fun getAllEntregas(): List<Entrega>

    fun saveEntrega(pedido: Entrega): Entrega?

    fun deleteEntrega(id: Long): Entrega?
}