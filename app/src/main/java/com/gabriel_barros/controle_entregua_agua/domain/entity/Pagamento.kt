package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Pagamento(
    val id: Long,
    val pedido_id: Long,
    val data: LocalDate,
    val valor: Double,
    val pagamento: TipoPagamento,
){
    companion object{
        fun emptyPagamento(): Pagamento{
            return Pagamento(
                id = 0,
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