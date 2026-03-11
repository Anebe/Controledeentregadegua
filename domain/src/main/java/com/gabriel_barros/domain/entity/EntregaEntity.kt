package com.gabriel_barros.domain.entity

import java.time.LocalDate


//@Serializable
//data class EntregaEntity(
//    val id: Long = 0,
//    @Serializable(with = LocalDateSerializer::class)
//    val data: LocalDate,
//    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
//    val status: StatusEntrega = StatusEntrega.PENDENTE,
//    val pedido_id: Long,
//) {
//    companion object {
//        fun emptyEntrega(): EntregaEntity {
//            return EntregaEntity(
//                id = 0,
//                data = LocalDate.now(),
//                entregador = TipoEntregador.COMERCIO,
//                status = StatusEntrega.PENDENTE,
//                pedido_id = 0,
//            )
//        }
//    }
//}

enum class Status {
    FINALIZADO,
    PENDENTE
}

enum class TipoEntregador {
    COMERCIO,
    CLIENTE,
}

class ProdutoEntregue(
    val produto_pedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
) {
    init {
        require(qtdEntregue >= 0 || qtdRetornado >= 0) { "Precisa haver ou Quantidade Entregue ou Quantidade de retornaveis" }
        require(qtdRetornado >= 0) { "Quantidade de Retornavies não pode ser negativa" }
        require(qtdEntregue >= 0) { "Quantidade Entrege não pode ser negativa" }
    }

}

class ProdutoEntregaStatus(
    val produto_pedido_id: Long,
    private var _qtdEntregue: Int,
    private var _qtdFalta: Int,
    val total: Int
) {
    val qtdEntregue: Int get() = _qtdEntregue
    val qtdFalta: Int get() = _qtdFalta

    fun registrarEntrega(qtd: Int) {
        require(qtd > 0 && qtd <= _qtdFalta) { "Quantidade inválida" }
        _qtdEntregue += qtd
        _qtdFalta -= qtd
    }
}

class Frete(
    val id: Long = 0,
    val pedido_id: Long,
    private var status: Status,
) {
    private val produtosPedido: MutableSet<ProdutoEntregaStatus> = mutableSetOf()
    private val entregas: MutableList<Entrega> = mutableListOf()

    fun status() = status

    fun addProdutoPedido(produto_pedido_id: Long, qtdDesejado: Int) {
        require(produto_pedido_id > 0) { "id de ´produto do pedido inválido!" }
        require(qtdDesejado > 0) { "quantidade inválida!" }

        produtosPedido.add(
            ProdutoEntregaStatus(
                produto_pedido_id = produto_pedido_id,
                _qtdEntregue = 0,
                _qtdFalta = qtdDesejado,
                total = qtdDesejado
            )
        )
    }
    fun addEntrega(entrega: List<Entrega>) {
        entrega.forEach { this.addEntrega(it) }
    }
    fun addEntrega(entrega: Entrega) {
        val produtosDesejados: MutableMap<Long, ProdutoEntregaStatus> = mutableMapOf()

        entrega.getProdutosEntregue().forEach { produtoEntregue ->
            val produtoEntregaStatus = produtosPedido.find {
                it.produto_pedido_id == produtoEntregue.produto_pedido_id
            }
            require(produtoEntregaStatus != null) { "Produto precisa estar no pedido" }
            produtosDesejados[produtoEntregue.produto_pedido_id] = produtoEntregaStatus
        }
        entrega.getProdutosEntregue().forEach { produtoEntregue ->
            produtosDesejados[produtoEntregue.produto_pedido_id]?.registrarEntrega(produtoEntregue.qtdEntregue)
            entregas.add(entrega)
        }
        finish()
    }

    private fun finish(){
        val isFinish = produtosPedido.all { produtoEntregue ->
            produtoEntregue.qtdFalta == 0
        }
        if (isFinish) status = Status.FINALIZADO
    }

    fun getProdutosStatus(): Set<ProdutoEntregaStatus> = produtosPedido
    fun getEntregas(): List<Entrega> = entregas

}

class Entrega(
    val id: Long = 0,
    val data: LocalDate,
    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
    val freteId: Long,
) {
    private val produtosEntregues: MutableSet<ProdutoEntregue> = mutableSetOf()

    init {
        require(!data.isAfter(LocalDate.now())) { "Data da entrega não pode ser futura" }
    }

    fun addProdutoEntregue(produtoEntregue: Set<ProdutoEntregue>) = produtosEntregues.addAll(produtoEntregue)

    fun addProdutoEntregue(produtoEntregue: ProdutoEntregue) {
        produtosEntregues.add(produtoEntregue)
    }

    fun getProdutosEntregue() = produtosEntregues.toSet()
}