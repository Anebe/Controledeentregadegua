package com.gabriel_barros.controle_entregua_agua.model

data class Cliente(
    val id: Int = -1,
    val nome: String = "",
    val ano: String = "",
    val qtd_galao: Int = 0,
    val qtd_galao_emprestado: Int = 0,
    val media_de_entrega: Int = 0,
    val caixa: Int = 0,
    val descricao: String = ""
) {
}