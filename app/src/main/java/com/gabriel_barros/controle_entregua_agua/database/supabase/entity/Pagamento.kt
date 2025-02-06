package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Pagamento(
    val id: Long = 0,
    val pedidoId: Long,
    val data: String,
    val valor: Double
)
