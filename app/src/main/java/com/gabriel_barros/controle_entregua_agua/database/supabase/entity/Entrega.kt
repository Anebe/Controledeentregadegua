package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Entrega(
    val id: Long = 0,
    val pedidoId: Long,
    val galaoId: Long,
    val data: String,
    val qtdEntregue: Int,
    val qtdSeco: Int,
    val idGalao: Long
)
