package com.gabriel_barros.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class EmprestimoPosseSupabase(
    val qtd_emprestado: Int = 0,
    val qtd_posse: Int = 0,
//    val cliente: Cliente,
//    val produto: Produto
    val cliente_id: Long,
    val produto_id: Long
)
