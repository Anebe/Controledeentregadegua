package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Produto(
    val id: Long = 0,
    val preco: Double,
    val nome: String,
    val custo: Double,
    val estoque: Int,
    val descricao: String,
    val reservado: Int,
) {
    companion object{
        fun emptyProduto(): Produto{
            return Produto(
                id = 0,
                preco = 0.0,
                nome = "",
                custo = 0.0,
                estoque = 0,
                descricao = "",
                reservado = 0,
            )
        }
    }
}