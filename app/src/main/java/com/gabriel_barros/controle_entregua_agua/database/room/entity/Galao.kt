package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Galao(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val preco: Double,
    val nome: String
)
