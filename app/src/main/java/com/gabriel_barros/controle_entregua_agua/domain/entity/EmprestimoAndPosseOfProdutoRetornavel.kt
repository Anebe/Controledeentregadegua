package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class EmprestimoAndPosseOfProdutoRetornavel(
    val qtd_emprestado: Int,
    val qtd_posse: Int,
    val cliente_id: Long,
    val produto_id: Long
) {
    companion object {
        fun emptyEmprestimoPosse(): EmprestimoAndPosseOfProdutoRetornavel {
            return EmprestimoAndPosseOfProdutoRetornavel(
                qtd_emprestado = 0,
                qtd_posse = 0,
                cliente_id = 0,
                produto_id = 0,
            )
        }
    }
}