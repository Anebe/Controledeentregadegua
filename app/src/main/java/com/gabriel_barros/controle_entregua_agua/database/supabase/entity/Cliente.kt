package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: Long = 0,
    val nome: String,
    val ano: Int,
    val qtdGalao: Int,
    val qtdGalaoEmprestado: Int,
    val mediaDeEntrega: Float,
    val caixa: Float,
    val apelidos: List<String>,
    val descricao: String,
)
