package com.gabriel_barros.controle_entregua_agua.domain.usecase.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

@Deprecated("Subsituido por Manager e Query Builder")
interface ProdutoUseCase {
    suspend fun getProdutoById(id: Long): Produto?

    suspend fun getAllProdutosNomes(): List<Pair<Long, String>>

    suspend fun getAllProdutos(): List<Produto>

    suspend fun saveProduto(produto: Produto): Produto?

    suspend fun deleteProduto(id: Long): Produto?

}

