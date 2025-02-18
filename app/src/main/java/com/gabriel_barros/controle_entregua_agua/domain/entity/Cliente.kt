package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Cliente(
    val id: Long = 0L,
    val nome: String = "",
    val credito: Double = 0.0,
    val apelidos: List<String> = emptyList(),
    val descricao: String? = null,
    val enderecos: List<Endereco> = emptyList(),
)