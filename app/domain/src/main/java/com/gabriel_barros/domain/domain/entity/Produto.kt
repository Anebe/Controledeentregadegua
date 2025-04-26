package com.gabriel_barros.domain.domain.entity

import kotlinx.serialization.Serializable


//TODO criar um forma de agrupar produtos pelo preço. assim quando um pedido exigir do grupo, poderá ser entrega qualquer item desse grupo(mas será especificado qual é no momento da entrega para o calculo de lucro(cada produto tem custo diferente)
@Serializable
data class Produto(
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