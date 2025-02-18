package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable

data class Endereco (
    val id: Long = 0,
    val cliente_id: Long = 0,
    val cep: String = "",
    val rua: String = "",
    val bairro: String = "",
    val complemento: String = "",
    val numero: String = "",
)
