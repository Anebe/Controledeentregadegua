package com.gabriel_barros.domain.domain.entity

import com.gabriel_barros.domain.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Pedido(
    val id: Long = 0,
    val cliente_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val data_agendada_para_entrega: LocalDate, //TODO alterar pra um nome que dê a ideia de data escolhida pelo cliente
    val status: StatusPedido,
    val valor_total: Double,
    val status_pagamento: StatusPedido = StatusPedido.PENDENTE,
    val status_entrega: StatusPedido = StatusPedido.PENDENTE,
) {
    companion object {
        fun emptyPedido(): Pedido {
            return Pedido(
                cliente_id = 0,
                data = LocalDate.now(),
                data_agendada_para_entrega = LocalDate.now(),
                status = StatusPedido.PENDENTE,
                valor_total = 0.0,
            )
        }
    }
}

enum class StatusPedido {
    PENDENTE,
    FINALIZADO,
}
