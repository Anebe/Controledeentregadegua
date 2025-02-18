package com.gabriel_barros.controle_entregua_agua.domain.entity

data class ItensEntrega(
//    val entrega: Entrega,
//    val ItemPedido: ItensPedido,
    val entrega_id: Long,
    val itemPedido_id: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
)
