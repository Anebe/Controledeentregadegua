package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class GalaoComEntrega(
    @Embedded
    val galao: Galao,
    @Relation(
        parentColumn = "id",
        entityColumn = "galaoId"
    )
    val entrega: Entrega
)
