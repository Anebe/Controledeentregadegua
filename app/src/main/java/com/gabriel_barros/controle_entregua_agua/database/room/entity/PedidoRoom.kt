package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PedidoRoom(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val clienteId: Long,
    val qtd: Int,
    val data: String,
    val troco: Double
)
