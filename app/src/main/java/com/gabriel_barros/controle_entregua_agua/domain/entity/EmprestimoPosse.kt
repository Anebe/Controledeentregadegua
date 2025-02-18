package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable

data class EmprestimoPosse(
    val qtd_emprestado: Int = 0,
    val qtd_posse: Int = 0,
//    val cliente: Cliente,
//    val produto: Produto
    val cliente_id: Long,
    val produto_id: Long
)
