package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class Cliente(
    val id: Long,
    val nome: String,
    val credito: Double,
    val apelidos: List<String>,
    val descricao: String,
    //val enderecos: List<Endereco> = emptyList(),
){
    init{
        require(nome.isNotBlank()) { "Nome não pode estar em branco." }
        require(credito >= 0.0) { "Crédito não pode ser negativo"}
    }
    companion object{
        fun emptyCliente(): Cliente{
            return Cliente(
                id = 0,
                        nome = "none",
                        credito = 0.0,
                        apelidos = emptyList(),
                        descricao = "",
                        //enderecos = emptyList(),
            )
        }
    }
}