package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Cliente(
    val id: Long,
    val nome: String,
    val credito: Double,
    val apelidos: List<String>,
    val descricao: String,
    val enderecos: List<Endereco>,
){
    companion object{
        fun emptyCliente(): Cliente{
            return Cliente(
                id = 0,
                        nome = "",
                        credito = 0.0,
                        apelidos = emptyList(),
                        descricao = "",
                        enderecos = emptyList(),
            )
        }
    }
}