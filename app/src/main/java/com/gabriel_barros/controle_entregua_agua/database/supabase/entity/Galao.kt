package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Galao(
    val id: Long = 0,
    val preco: Double,
    val nome: String
)
