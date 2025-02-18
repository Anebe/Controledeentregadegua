package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Categoria(
    val id: Long,
//    val categoria_pai: Categoria?,
    val categoria_pai: Long?,
    val nome: String
)
