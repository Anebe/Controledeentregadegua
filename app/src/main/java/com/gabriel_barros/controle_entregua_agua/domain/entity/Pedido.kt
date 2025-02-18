package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Pedido(
    val id: Long = 0,
    val cliente_id: Long = 0,
    val data: LocalDate = LocalDate.now(),
    val data_entrega: LocalDate = LocalDate.now(),
    val troco: Double = 0.0,
    val status: StatusPedido = StatusPedido.PENDENTE,
)

enum class StatusPedido {
    PENDENTE,
    FINALIZADO,
}
