package com.gabriel_barros.controle_entregua_agua.domain.portout.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoQueryBuilder {
    fun getProdutoById(vararg id: Long): ProdutoQueryBuilder
    fun getAllProdutos(): ProdutoQueryBuilder

    suspend fun buildExecuteAsSingle(): Produto
    suspend fun buildExecuteAsSList(): List<Produto>

}