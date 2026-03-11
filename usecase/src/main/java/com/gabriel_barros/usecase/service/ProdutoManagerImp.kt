package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain_portout.ProdutoPortOut
import com.gabriel_barros.usecase.dto.ProdutoInput
import com.gabriel_barros.usecase.dto.ProdutoOutput
import com.gabriel_barros.usecase.usecase.ProdutoManager

internal class ProdutoManagerImp(
    private val produtoOut: ProdutoPortOut
): ProdutoManager {

    override suspend fun registerProduto(produto: ProdutoInput): ProdutoOutput {
        val produto = produtoOut.saveProduto(produto.to())
        return ProdutoOutput(produto)
    }

    override suspend fun updateProduto(produto: Produto) {
        produtoOut.saveProduto(produto)
    }

    override suspend fun delete(id: Long) {
        produtoOut.deleteProduto(id)
    }
}