package com.gabriel_barros.domain.domain.entity

import com.gabriel_barros.domain.domain.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PedidoEntity(
    val id: Long = 0,
    val cliente_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val data_agendada_para_entrega: LocalDate,
    val status: StatusPedido,
    val valor_total: Double,
    val status_pagamento: StatusPedido = StatusPedido.PENDENTE,
    val status_entrega: StatusPedido = StatusPedido.PENDENTE,
) {
    companion object {
        fun emptyPedido(): PedidoEntity {
            return PedidoEntity(
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

class Pedido(
    val id: Long = 0,
    val clienteId: Long,
    valorTotal: Double = 0.0,
    val dataPedido: LocalDate = LocalDate.now(),
    val dataAgendadaParaEntrega: LocalDate = LocalDate.now(),
    val status: StatusPedido = StatusPedido.PENDENTE,
    novosProdutos: MutableMap<Produto, Int> = mutableMapOf<Produto, Int>()
){
    private val produtos: MutableMap<Produto, Int> = mutableMapOf<Produto, Int>()
    private var valorTotal: Double = 0.0

    init {
        require(id >= 0) { "ID do pedido não pode ser negativo." }
        require(clienteId > 0) { "ID do cliente não pode ser zero ou negativo." }
        require(dataAgendadaParaEntrega.isAfter(dataPedido) || dataAgendadaParaEntrega.isEqual(dataPedido)) {
            "A data agendada para entrega não pode ser anterior à data atual."
        }
        if (novosProdutos.isNotEmpty()) {
            novosProdutos.forEach { (produto, qtd) ->
                addProduto(produto, qtd)
            }
        }else{
            this.valorTotal = valorTotal
        }
    }

    fun addProduto(produto: Produto, qtd: Int){
        require(qtd > 0) { "A quantidade deve ser maior que 0." }
        produto.reservarProduto(qtd)
        produtos[produto] = qtd
        valorTotal += produto.preco * qtd
    }

    fun removeProduto(produto: Produto){
        val qtd = produtos[produto]
        qtd?.let{
            produto.desreservarProduto(qtd)
            produtos.remove(produto)
            valorTotal -= produto.preco * qtd

        }
    }

    fun valorTotal(): Double = valorTotal
}
