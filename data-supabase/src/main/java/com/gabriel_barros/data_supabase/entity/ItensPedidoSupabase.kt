package com.gabriel_barros.data_supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class ItensPedidoSupabase (
    val id: Long = 0,
    val pedido_id: Long,
    val produto_id: Long,
    val qtd: Int

)
