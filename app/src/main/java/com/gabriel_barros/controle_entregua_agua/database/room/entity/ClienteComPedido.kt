package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ClienteComPedido(
    @Embedded val cliente: Cliente,
    @Relation(
        parentColumn = "id",
        entityColumn = "clienteId"
    )
    val pedidos: List<Pedido>
)