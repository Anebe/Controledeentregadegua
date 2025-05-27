package com.gabriel_barros.domain_portout

import com.gabriel_barros.domain.entity.Frete

interface FretePortOut {
    suspend fun saveFrete(entrega: Frete): Frete
}

interface FreteSearch {
    suspend fun getFreteById(id: Long): Frete

    suspend fun getFreteByPedidoId(pedidoId: Long): Frete
}