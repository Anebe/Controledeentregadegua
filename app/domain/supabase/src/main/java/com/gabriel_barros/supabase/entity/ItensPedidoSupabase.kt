package com.gabriel_barros.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class ItensPedidoSupabase (
    val id: Long,
    val pedido_id: Long,
    val produto_id: Long,
    val qtd: Int

)
