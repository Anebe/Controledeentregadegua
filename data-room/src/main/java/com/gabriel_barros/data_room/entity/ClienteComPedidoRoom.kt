package com.gabriel_barros.data_room.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.gabriel_barros.database.room.entity.PedidoRoom

data class ClienteComPedidoRoom(
    @Embedded val clienteRoom: ClienteRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "clienteId"
    )
    val pedidoRooms: List<PedidoRoom>
)