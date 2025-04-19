package com.gabriel_barros.controle_entregua_agua.domain.service.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoManager

class ProdutoManagerImp(
    private val produtoOut: ProdutoPortOut
): ProdutoManager {

    override suspend fun registerProduto(produto: Produto): Produto {
        return produtoOut.saveProduto(produto)
    }
}