package com.gabriel_barros.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PagamentoRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pedidoId: Long,
    val data: String,
    val valor: Double
)
