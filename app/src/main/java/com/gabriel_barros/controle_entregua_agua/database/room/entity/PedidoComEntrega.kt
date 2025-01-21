package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoComEntrega(
    @Embedded
    val pedido: Pedido,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val entregas: List<Entrega>
)
