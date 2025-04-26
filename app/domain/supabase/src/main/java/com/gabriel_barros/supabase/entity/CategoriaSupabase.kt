package com.gabriel_barros.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class CategoriaSupabase(
    val id: Long,
    val categoria_pai: Long,
    val nome: String
)
