package com.gabriel_barros.data_supabase.entity

import com.gabriel_barros.data_supabase.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ProdutoSupabase(
    val id: Long = 0,
    @Serializable(with = BigDecimalSerializer::class)
    val preco: BigDecimal,
    val nome: String,
    @Serializable(with = BigDecimalSerializer::class)
    val custo: BigDecimal,
    val estoque: Int,
    val descricao: String,
    val reservado: Int,
)
