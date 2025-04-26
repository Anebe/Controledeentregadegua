package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.Produto

interface ProdutoQueryBuilder {
    fun getProdutoById(vararg id: Long): ProdutoQueryBuilder
    fun getProdutoById(id: List<Long>): ProdutoQueryBuilder

    suspend fun buildExecuteAsSingle(): Produto
    suspend fun buildExecuteAsSList(): List<Produto>

}