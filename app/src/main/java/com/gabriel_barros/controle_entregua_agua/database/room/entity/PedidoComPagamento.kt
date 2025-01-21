package com.gabriel_barros.controle_entregua_agua.database.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PedidoComPagamento(
    @Embedded
    val pedido: Pedido,
    @Relation(
        parentColumn = "id",
        entityColumn = "pedidoId"
    )
    val pagamentos: List<Pagamento>
)
