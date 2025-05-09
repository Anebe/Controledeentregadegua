package com.gabriel_barros.supabase.entity

import kotlinx.serialization.Serializable

@Serializable
data class ItensEntregaSupabase(
    val entrega_id: Long,
    val itemPedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
)
