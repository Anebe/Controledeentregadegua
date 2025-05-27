package com.gabriel_barros.domain.entity

import kotlinx.serialization.Serializable

@Deprecated("")
@Serializable
data class ItensEntrega(
//    val entrega: Entrega,
//    val ItemPedido: ItensPedido,
    val entrega_id: Long = 0,
    val itemPedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
)
