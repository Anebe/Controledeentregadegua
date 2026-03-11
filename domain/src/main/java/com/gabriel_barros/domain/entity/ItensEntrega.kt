package com.gabriel_barros.domain.entity


@Deprecated("")
data class ItensEntrega(
//    val entrega: Entrega,
//    val ItemPedido: ItensPedido,
    val entrega_id: Long = 0,
    val itemPedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
)
