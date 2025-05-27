package com.gabriel_barros.domain.entity

import java.math.BigDecimal

interface DividaBase {
    val pedido_id: Long
    val valor: BigDecimal
}
interface DividaRelatorio: DividaBase{
    val id: Long
    override val pedido_id: Long
    override val valor: BigDecimal

    fun isQuitada(): Boolean

    fun valorPago(): BigDecimal

    fun faltaPagar(): BigDecimal

    fun getPagamentos(): List<Pagamento>
}

class Divida(
    override val id: Long = 0,
    override val pedido_id: Long,
    override val valor: BigDecimal,
): DividaRelatorio, DividaBase{

    init {
        require(pedido_id > 0) { "ID do pedido deve ser positivo" }
        require(valor > BigDecimal.ZERO) { "Valor original deve ser positivo" }
    }

    private val pagamentos: MutableList<Pagamento> = mutableListOf()


    override fun isQuitada() = valor == pagamentos.sumOf { it.valor }

    fun registrarPagamento(pagamento: Pagamento, onExcedeValor: (troco: BigDecimal) -> Unit) {
        if (faltaPagar() < pagamento.valor) {
            onExcedeValor(faltaPagar() - pagamento.valor)
            pagamentos.add(
                Pagamento(
                    id = pagamento.id,
                    divida_id = pagamento.divida_id,
                    data = pagamento.data,
                    valor = faltaPagar(),
                    pagamento = pagamento.pagamento
                )
            )
        }else{
            pagamentos.add(pagamento)
        }
    }

    override fun valorPago() = pagamentos.sumOf { it.valor }

    override fun faltaPagar() = valor - valorPago()

    override fun getPagamentos(): List<Pagamento> = pagamentos
}