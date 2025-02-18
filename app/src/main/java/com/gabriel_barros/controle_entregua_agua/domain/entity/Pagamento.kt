package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Pagamento(
    val id: Long,
//    val pedido: Pedido?,
    val pedido_id: Long,
    val data: LocalDate,
    val valor: Double,
    val pagamento: TipoPagamento
)

enum class TipoPagamento(tipo: String) {
    PIX("PIX"),
    DINHEIRO("DINHEIRO"),
    CARTAO("CARTAO"),
}