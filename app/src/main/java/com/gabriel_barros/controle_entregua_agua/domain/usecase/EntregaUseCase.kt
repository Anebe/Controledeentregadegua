package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega

interface EntregaUseCase {

    fun getEntregaById(id: Long): Entrega?

    fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    fun getAllEntregas(): List<Entrega>

    fun saveEntrega(pedido: Entrega): Entrega?


    fun deleteEntrega(id: Long): Entrega?
}