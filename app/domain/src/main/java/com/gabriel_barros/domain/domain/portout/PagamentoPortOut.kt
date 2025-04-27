package com.gabriel_barros.domain.domain.portout

import com.gabriel_barros.domain.domain.entity.PagamentoEntity

interface PagamentoPortOut {
    suspend fun savePagamento(pagamento: PagamentoEntity): PagamentoEntity

    suspend fun deletePagamento(id: Long): PagamentoEntity
}

@Deprecated("Substituido por Query Builder")
interface PagamentoGet {
    suspend fun getPagamentoById(id: Long): PagamentoEntity?

    suspend fun getAllPagamentos(): List<PagamentoEntity>

    suspend fun getPagamentosByPedidoId(pedidoId: Long): List<PagamentoEntity>
}