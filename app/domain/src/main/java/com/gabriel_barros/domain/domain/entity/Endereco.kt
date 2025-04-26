package com.gabriel_barros.domain.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Endereco(
    val id: Long = 0,
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