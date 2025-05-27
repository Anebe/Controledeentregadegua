package com.gabriel_barros.domain.entity

import java.math.BigDecimal
import java.time.LocalDate


enum class TipoPagamento(tipo: String) {
    PIX("PIX"),
    DINHEIRO("DINHEIRO"),
}

class Pagamento(
    val id: Long = 0,
    val divida_id: Long,
    val data: LocalDate,
    val valor: BigDecimal,
    val pagamento: TipoPagamento,
) {
    init {
        require(divida_id > 0) { "ID da dívida deve ser positivo" }
        require(valor > BigDecimal.ZERO) { "Valor do pagamento deve ser positivo" }
        require(!data.isAfter(LocalDate.now())) { "Data efetiva de pagamento não pode ser futura" }
    }
}