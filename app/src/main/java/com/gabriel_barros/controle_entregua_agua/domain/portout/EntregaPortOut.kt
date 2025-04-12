package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega

interface EntregaPortOut {

    suspend fun getEntregaById(id: Long): Entrega?

    suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    suspend fun getAllEntregas(): List<Entrega>

    suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega>

    suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega>

    suspend fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega?

    suspend fun deleteEntrega(id: Long): Entrega?
}