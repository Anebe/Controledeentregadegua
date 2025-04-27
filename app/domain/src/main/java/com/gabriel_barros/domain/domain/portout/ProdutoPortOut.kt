package com.gabriel_barros.domain.domain.portout

import com.gabriel_barros.domain.domain.entity.ProdutoEntity

interface ProdutoPortOut {

    suspend fun saveProduto(pedido: ProdutoEntity): ProdutoEntity

    suspend fun deleteProduto(id: Long): ProdutoEntity

}
@Deprecated("Substituido por Query Builder")
interface ProdutoGet{
    suspend fun getProdutoById(id: Long): ProdutoEntity?

    suspend fun getAllProdutos(): List<ProdutoEntity>
}