package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
@Serializable
data class ItensPedido (
    val id: Long = 0,
    val pedido_id: Long = 0,
    val produto_id: Long = 0,
    val qtd: Int = 0,

)
