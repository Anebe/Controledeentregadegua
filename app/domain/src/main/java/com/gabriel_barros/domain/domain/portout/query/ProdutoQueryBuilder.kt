package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.ProdutoEntity

interface ProdutoQueryBuilder {
    fun getProdutoById(vararg id: Long): ProdutoQueryBuilder
    fun getProdutoById(id: List<Long>): ProdutoQueryBuilder

    suspend fun buildExecuteAsSingle(): ProdutoEntity
    suspend fun buildExecuteAsSList(): List<ProdutoEntity>

}