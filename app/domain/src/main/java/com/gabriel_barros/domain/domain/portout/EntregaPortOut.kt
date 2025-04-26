package com.gabriel_barros.domain.domain.portout

import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega

interface EntregaPortOut {

    suspend fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega

    suspend fun deleteEntrega(id: Long): Entrega
}

@Deprecated("Substituido por Query Builder")
interface EntregaGet {
    suspend fun getEntregaById(id: Long): Entrega?

    suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega>

    suspend fun getAllEntregas(): List<Entrega>

    suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega>

    suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega>
}