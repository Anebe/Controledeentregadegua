package com.gabriel_barros.domain.domain.entity

import com.gabriel_barros.domain.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Pagamento(
    val id: Long = 0,
    val pedido_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate,//TODO alterar nome para algo dê a entender que é a data do registro do pagamento e não do pagamento em si
    val valor: Double,
    val pagamento: TipoPagamento,
) {
    companion object {
        fun emptyPagamento(): Pagamento {
            return Pagamento(
                pedido_id = 0,
                data = LocalDate.now(),
                valor = 0.0,
                pagamento = TipoPagamento.DINHEIRO,
            )
        }
    }
}

enum class TipoPagamento(tipo: String) {
    PIX("PIX"),
    DINHEIRO("DINHEIRO"),
    CARTAO("CARTAO"),
}