package com.gabriel_barros.domain.domain.portout

import com.gabriel_barros.domain.domain.entity.Produto

interface ProdutoPortOut {

    suspend fun saveProduto(pedido: Produto): Produto

    suspend fun deleteProduto(id: Long): Produto

}
@Deprecated("Substituido por Query Builder")
interface ProdutoGet{
    suspend fun getProdutoById(id: Long): Produto?

    suspend fun getAllProdutos(): List<Produto>
}