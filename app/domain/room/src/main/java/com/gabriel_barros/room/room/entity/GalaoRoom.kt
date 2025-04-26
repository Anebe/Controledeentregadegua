package com.gabriel_barros.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GalaoRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val preco: Double,
    val nome: String
)
