package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega

interface EntregaUseCase {

    fun getEntregaById(id: Long): Entrega?

    fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    fun getAllEntregas(): List<Entrega>

    fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega>

    fun getAllEntregasByPedido(pedidoId: Long): List<Entrega>

    fun saveEntrega(entrega: Entrega, produtosEntregues: List<ItensEntrega>): Entrega?


    fun deleteEntrega(id: Long): Entrega?
}