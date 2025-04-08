package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.domain.entity.Categoria
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
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
