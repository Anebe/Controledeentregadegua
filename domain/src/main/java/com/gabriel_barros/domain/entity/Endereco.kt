package com.gabriel_barros.domain.entity


data class Endereco(
    val id: Long = 0,
    val cliente_id: Long = 0,
    val cep: String,
    val rua: String,
    val bairro: String,
    val complemento: String,
    val numero: String,
)