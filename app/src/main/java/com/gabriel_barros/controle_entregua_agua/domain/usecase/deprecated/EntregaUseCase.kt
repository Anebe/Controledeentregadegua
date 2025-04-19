package com.gabriel_barros.controle_entregua_agua.domain.usecase.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega

@Deprecated("Subsituido por Manager e Query Builder")
interface EntregaUseCase {

    suspend fun getEntregaById(id: Long): Entrega?

    suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    suspend fun getAllEntregas(): List<Entrega>

    suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega>

    suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega>

    suspend fun saveEntrega(entrega: Entrega, produtosEntregues: List<ItensEntrega>): Entrega?


    suspend fun deleteEntrega(id: Long): Entrega?
}



