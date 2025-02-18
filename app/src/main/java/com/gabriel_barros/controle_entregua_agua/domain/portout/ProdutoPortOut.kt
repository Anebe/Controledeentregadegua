package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto

interface ProdutoPortOut {
    fun getProdutoById(id: Long): Produto?

    fun getAllProdutos(): List<Produto>

    fun saveProduto(pedido: Produto): Produto?

    fun deleteProduto(id: Long): Produto?

}