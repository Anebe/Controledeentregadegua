package com.gabriel_barros.domain.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class ItensEntrega(
//    val entrega: Entrega,
//    val ItemPedido: ItensPedido,
    val entrega_id: Long = 0,
    val itemPedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
)
