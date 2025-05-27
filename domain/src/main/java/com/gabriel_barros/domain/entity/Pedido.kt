package com.gabriel_barros.domain.entity

import java.time.LocalDate

enum class StatusPedido {
    PENDENTE,
    FINALIZADO,
}


class ProdutoPedido(
    val produtoId: Id,
    val pedidoId: Id,
    val qtd: Int
){
    init {
        require(qtd > 0) { "Quantidade nula ou negativa" }
    }
}
class Pedido(
    val id: Id = Id(0),
    val cliente_id: Id,
    val dataPedido: LocalDate = LocalDate.now(),
    val status: StatusPedido = StatusPedido.PENDENTE,
) {
    private val produtos: MutableSet<ProdutoPedido> = mutableSetOf()

    init {
        require(cliente_id > 0) { "ID do cliente não pode ser zero ou negativo." }
    }

    fun addProduto(produto: Produto, qtd: Int) {
        require(produtos.any { it.produtoId == produto.id }) { "O produto já está no pedido" }

        produtos.add(ProdutoPedido(
            produtoId = produto.id,
            pedidoId = this.id,
            qtd = qtd
        ))
    }

//    fun atualizaProduto(produto: Produto, qtd: Int) {
//        require(qtd > 0) { "A quantidade deve ser maior que 0." }
//        require(produtos.containsKey(produto)) { "O produto não está no pedido" }
//
//        val qtdAParaDesreservar = produtos[produto] ?: 0 //TODO esse 0 nunca vai ser atribuido por causa do require, mas procurar uma forma melhor de evitar o nullcheck
//        produto.desreservarProduto(qtdAParaDesreservar)
//        produto.reservarProduto(qtd)
//        produtos[produto] = qtd
//    }

//    fun removeProduto(produto: Produto) {
//        val qtd = produtos[produto]
//        qtd?.let {
//            produto.desreservarProduto(qtd)
//            produtos.remove(produto)
//        }
//    }
    fun getProdutos(): Set<ProdutoPedido> = produtos
}
