package com.gabriel_barros.domain.domain.entity

import com.gabriel_barros.domain.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PagamentoEntity(
    val id: Long = 0,
    val pedido_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate,//TODO alterar nome para algo dê a entender que é a data do registro do pagamento e não do pagamento em si
    val valor: Double,
    val pagamento: TipoPagamento,
) {
    companion object {
        fun emptyPagamento(): PagamentoEntity {
            return PagamentoEntity(
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

class Pagamento(
    val id: Long = 0,
    val divida_id: Long,
    val data: LocalDate,//TODO alterar nome para algo dê a entender que é a data do registro do pagamento e não do pagamento em si
    val valor: Double,
    val pagamento: TipoPagamento,
) {
    init {
        require(divida_id > 0) { "ID da dívida deve ser positivo" }
        require(valor > 0) { "Valor do pagamento deve ser positivo" }
        require(!data.isAfter(LocalDate.now())) { "Data efetiva de pagamento não pode ser futura" }
    }
}

class Divida(
    val id: Long = 0,
    val pedido_id: Long,
    val valor: Double,
    private val pagamentos: MutableList<Pagamento>,
    private val isPagamentoCompleto: Boolean
) {
    init {
        require(pedido_id > 0) { "ID do pedido deve ser positivo" }
        require(valor > 0) { "Valor original deve ser positivo" }
    }

    fun isPagamentoCompleto() = isPagamentoCompleto

    fun registrarPagamento(pagamento: Pagamento, onExcedeValor: (troco: Double) -> Unit) {
        if (faltaPagar() > pagamento.valor) {
            onExcedeValor(faltaPagar() - pagamento.valor)
            pagamentos.add(
                Pagamento(
                    id = pagamento.id,
                    divida_id = pagamento.divida_id,
                    data = pagamento.data,
                    valor = pagamento.valor,
                    pagamento = pagamento.pagamento
                )
            )
        }else{
            pagamentos.add(pagamento)
        }
    }

    fun valorPago() = pagamentos.sumOf { it.valor }

    fun faltaPagar() = valor - valorPago()
}