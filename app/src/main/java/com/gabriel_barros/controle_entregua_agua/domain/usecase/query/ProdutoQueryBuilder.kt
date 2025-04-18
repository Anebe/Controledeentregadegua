package com.gabriel_barros.controle_entregua_agua.domain.usecase.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoQueryBuilder {
    fun getProdutoById(id: Long): ProdutoQueryBuilder
    fun getAllProdutosNomes(): ProdutoQueryBuilder
    fun getAllProdutos(): ProdutoQueryBuilder

    suspend fun buildExecuteAsSingle(): Produto
    suspend fun buildExecuteAsSList(): List<Produto>

}