package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class CategoriaSupabase(
    val id: Long,
    val categoria_pai: Long,
    val nome: String
)
