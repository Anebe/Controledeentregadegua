package com.gabriel_barros.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoComPagamentoRoom(
    @Embedded
    val pedidoRoom: PedidoRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val pagamentoRooms: List<PagamentoRoom>
)
