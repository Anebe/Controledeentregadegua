package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Produto(
    val id: Long = 0,
    val preco: Double,
    val nome: String,
    val custo: Double,
    val estoque: Int,
    val descricao: String,
//    val categorias: List<Categoria>?,
) {
}