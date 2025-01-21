package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Pagamento(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val pedidoId: Long,
    val data: String,
    val valor: Double
)
