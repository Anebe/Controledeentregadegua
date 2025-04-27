package com.gabriel_barros.domain.domain.entity

import kotlinx.serialization.Serializable


//TODO criar um forma de agrupar produtos pelo preço. assim quando um pedido exigir do grupo, poderá ser entrega qualquer item desse grupo(mas será especificado qual é no momento da entrega para o calculo de lucro(cada produto tem custo diferente)
@Serializable
data class ProdutoEntity(
    val id: Long = 0,
    val preco: Double,
    val nome: String,
    val custo: Double,
    val estoque: Int,
    val descricao: String,
    val reservado: Int,
) {
//    companion object{
//        fun emptyProduto(): Produto{
//            return Produto(
//                id = 0,
//                preco = 0.0,
//                nome = "",
//                custo = 0.0,
//                estoque = 0,
//                descricao = "",
//                reservado = 0,
//            )
//        }
//    }
}

class Produto(
    val id: Long = 0,
    val preco: Double,
    val nome: String,
    val custo: Double,
    estoque: Int,
    reservado: Int,
    var descricao: String = "",
) {

    private var estoque = 0
    private var reservado = 0

    init {
        require(preco >= 0) { "Preço não pode ser negativo." }
        require(custo >= 0) { "Custo não pode ser negativo." }
        require(estoque >= 0) { "Estoque não pode ser negativo." }
        require(reservado >= 0) { "Reservado não pode ser negativo." }

        this.estoque = estoque
        this.reservado = reservado
    }

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
        require(qtd <= estoque) { "Quantidade a ser reservada excede o estoque disponível." }

        reservado += qtd
        estoque -= qtd
    }

    fun desreservarProduto(qtd: Int) {
        require(qtd > 0) { "Quantidade a ser desreservada deve ser maior que 0." }
        require(qtd <= reservado) { "Quantidade a ser desreservada excede o que foi reservado." }

        reservado -= qtd
        estoque += qtd
    }
}