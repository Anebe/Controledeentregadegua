package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoUseCase {
    suspend fun getProdutoById(id: Long): Produto?

    suspend fun getAllProdutosNomes(): List<Pair<Long, String>>

    suspend fun getAllProdutos(): List<Produto>

    suspend fun saveProduto(produto: Produto): Produto?

    suspend fun deleteProduto(id: Long): Produto?

}

interface ProdutoManager {
    suspend fun registerProduto(produto: Produto): Produto?

}