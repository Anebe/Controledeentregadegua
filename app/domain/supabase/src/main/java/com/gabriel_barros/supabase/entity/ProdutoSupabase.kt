package com.gabriel_barros.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class ProdutoSupabase(
    val id: Long = 0,
    val preco: Double,
    val nome: String,
    val custo: Double,
    val estoque: Int,
    val descricao: String,
    val reservado: Int,
)
