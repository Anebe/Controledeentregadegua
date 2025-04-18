package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Endereco(
    val id: Long,
    val cliente_id: Long,
    val cep: String,
    val rua: String,
    val bairro: String,
    val complemento: String,
    val numero: String,
) {
    companion object {
        fun emptyEndereco(): Endereco {
            return Endereco(
                id = 0,
                cliente_id = 0,
                cep = "",
                rua = "",
                bairro = "",
                complemento = "",
                numero = "",
            )
        }
    }
}