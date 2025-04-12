package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoPortOut {
    suspend fun getProdutoById(id: Long): Produto?

    suspend fun getAllProdutos(): List<Produto>

    suspend fun saveProduto(pedido: Produto): Produto?

    suspend fun deleteProduto(id: Long): Produto?

}