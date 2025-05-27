package com.gabriel_barros.domain_portout.query

import com.gabriel_barros.domain.entity.Produto

interface ProdutoQueryBuilder {
    //TODO lançar exceção quando não encontrar pelo menos 1
    fun getProdutoById(vararg id: Long): ProdutoQueryBuilder
    fun getProdutoById(id: List<Long>): ProdutoQueryBuilder

    suspend fun buildExecuteAsSingle(): Produto
    suspend fun buildExecuteAsSList(): List<Produto>

}