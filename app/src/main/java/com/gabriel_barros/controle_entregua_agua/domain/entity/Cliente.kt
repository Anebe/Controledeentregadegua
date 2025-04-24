package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class Cliente(
    val id: Long = 0,
    val nome: String = "",
    val credito: Double = 0.0,
    val apelidos: List<String> = emptyList(),
    val descricao: String = "",
    val enderecos: List<Endereco> = emptyList(),
) {
    init {
        require(credito >= 0.0) { "Crédito não pode ser negativo" }
    }

    companion object {
        fun emptyCliente(): Cliente {
            return Cliente(
                nome = "none",
                credito = 0.0,
                apelidos = emptyList(),
                descricao = "",
                //enderecos = emptyList(),
            )
        }
    }
}