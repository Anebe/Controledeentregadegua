package com.gabriel_barros.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClienteComPedidoRoom(
    @Embedded val clienteRoom: ClienteRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "clienteId"
    )
    val pedidoRooms: List<PedidoRoom>
)