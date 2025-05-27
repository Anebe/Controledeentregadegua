package com.gabriel_barros.domain.entity

data class ItensPedido (
    val id: Long = 0,
    val pedido_id: Long,
    val produto_id: Long,
    val qtd: Int,
)
