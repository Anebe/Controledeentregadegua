package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GalaoComEntregaRoom(
    @Embedded
    val galaoRoom: GalaoRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "galaoId"
    )
    val entregaRoom: EntregaRoom
)
