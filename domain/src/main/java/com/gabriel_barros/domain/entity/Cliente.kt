package com.gabriel_barros.domain.entity


class Cliente(
    val id: Long = 0,
    val nome: String,
    val credito: Double = 0.0,
    val apelidos: List<String> = emptyList(),
    val descricao: String = "",
    val enderecos: List<Endereco> = emptyList(),
) {
    init {
        require(nome.isNotBlank() || apelidos.isNotEmpty()) { "Nome vazio"}
        require(credito >= 0.0) { "Crédito negativo" }
    }
}