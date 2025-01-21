package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Entrega(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pedidoId: Long,
    val galaoId: Long,
    val data: String,
    val qtdEntregue: Int,
    val qtdSeco: Int,
    val idGalao: Long
)
