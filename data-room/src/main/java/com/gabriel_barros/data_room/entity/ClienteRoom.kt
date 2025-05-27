package com.gabriel_barros.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClienteRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val ano: Int,
    val qtdGalao: Int,
    val emprestado: Boolean,
    val mediaDeEntrega: Float,
    val caixa: String,
    val descricao: String,
)
