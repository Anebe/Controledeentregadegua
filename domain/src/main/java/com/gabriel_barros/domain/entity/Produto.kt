package com.gabriel_barros.domain.entity

import java.math.BigDecimal


//TODO criar um forma de agrupar produtos pelo preço. assim quando um pedido exigir do grupo, poderá ser entrega qualquer item desse grupo(mas será especificado qual é no momento da entrega para o calculo de lucro(cada produto tem custo diferente)
//@Serializable
//data class ProdutoEntity(
//    val id: Long = 0,
//    val preco: Double,
//    val nome: String,
//    val custo: Double,
//    val estoque: Int,
//    val descricao: String,
//    val reservado: Int,
//) {
//
//}

class Produto(
    val id: Id = Id(0),
    val preco: BigDecimal,
    val nome: String,
    val custo: BigDecimal,
    private var estoque: Int,
    private var reservado: Int,
    var descricao: String = "",
) {
    val disponivel: Int
        get() = estoque - reservado
    
    init {
        require(preco >= BigDecimal.ZERO) { "Preço não pode ser negativo." }
        require(custo >= BigDecimal.ZERO) { "Custo não pode ser negativo." }
        require(estoque >= 0) { "Estoque não pode ser negativo." }
        require(reservado >= 0) { "Reservado não pode ser negativo." }
    }

    fun reservado() = reservado
    fun estoque() = estoque

    fun adicionarEstoque(qtd: Int) {
        require(qtd > 0) { "Quantidade a ser adicionada deve ser maior que 0." }
        estoque += qtd
    }

    fun removerEstoque(qtd: Int) {
        require(qtd > 0) { "Quantidade a ser removida deve ser maior que 0." }
        if (qtd > estoque) {
            throw IllegalArgumentException("Quantidade a ser removida excede o estoque disponível.")
        }
        estoque -= qtd
    }

    fun reservarProduto(qtd: Int) {
        require(qtd > 0) { "Quantidade a ser reservada deve ser maior que 0." }
        //TODO não lançar exception, mas de alguma forma avisar que o estoque é insuficiente
//        require(qtd <= estoque) { "Quantidade a ser reservada excede o estoque disponível." }

        reservado += qtd
        estoque -= qtd
        if(estoque < 0) estoque = 0
    }

    fun desreservarProduto(qtd: Int) {
        require(qtd > 0) { "Quantidade a ser desreservada deve ser maior que 0." }
        require(qtd <= reservado) { "Quantidade a ser desreservada excede o que foi reservado." }

        reservado -= qtd
        estoque += qtd
    }
}