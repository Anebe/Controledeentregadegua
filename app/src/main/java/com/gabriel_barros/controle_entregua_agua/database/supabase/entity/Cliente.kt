package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
data class Cliente(
    val id: Long = 0,
    val nome: String = "",
    val ano_validade_galao: Int? = 0,
    val qtd_galao: Int? = 0,
    val qtd_galao_emprestado: Int? = 0,
    val media_de_entrega: Float ? = 0f,
    val caixa: Float ? = 0f,
    val apelidos: List<String>? = emptyList<String>(),
    val descricao: String? = "",
)
