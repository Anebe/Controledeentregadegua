package com.gabriel_barros.spreedsheetgoogle

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PedidoEntityApi(
    val id: Int = -1,
    var clienteId: Int  = -1,
    var qtd_agua: Int = 0,
    @Serializable(with = LocalDateSerializer::class)
    var entrega: LocalDate = LocalDate.MIN,
    var troco: Int = 0,
)
