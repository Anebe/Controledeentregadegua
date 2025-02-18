package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase

class ProdutoService(
    private val produtoOut: ProdutoPortOut
): ProdutoUseCase {
    override fun getProdutoById(id: Long): Produto? {
        return produtoOut.getProdutoById(id)
    }

    override fun getAllProdutos(): List<Produto> {
        return produtoOut.getAllProdutos()
    }

    override fun saveProduto(produto: Produto): Produto? {
        return produtoOut.saveProduto(produto)
    }

    override fun deleteProduto(id: Long): Produto? {
        return produtoOut.deleteProduto(id)
    }

    override fun getAllProdutosNomes(): List<Pair<Long, String>> {
        return this.getAllProdutos().map { it.id to it.nome }
    }
}