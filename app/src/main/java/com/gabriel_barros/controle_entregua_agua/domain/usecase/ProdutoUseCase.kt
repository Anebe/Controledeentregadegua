package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoUseCase {
    fun getProdutoById(id: Long): Produto?

    fun getAllProdutosNomes(): List<Pair<Long, String>>

    fun getAllProdutos(): List<Produto>

    fun saveProduto(produto: Produto): Produto?

    fun deleteProduto(id: Long): Produto?

}