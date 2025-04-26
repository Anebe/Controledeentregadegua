package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.domain.entity.Produto
import com.gabriel_barros.domain.domain.portout.ProdutoPortOut
import com.gabriel_barros.domain.domain.usecase.ProdutoManager

class ProdutoManagerImp(
    private val produtoOut: ProdutoPortOut
): ProdutoManager {

    override suspend fun registerProduto(produto: Produto): Produto {
        return produtoOut.saveProduto(produto)
    }
}