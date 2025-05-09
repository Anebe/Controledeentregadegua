package com.gabriel_barros.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EntregaRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pedidoId: Long,
    val galaoId: Long,
    val data: String,
    val qtdEntregue: Int,
    val qtdSeco: Int,
    val idGalao: Long
)
