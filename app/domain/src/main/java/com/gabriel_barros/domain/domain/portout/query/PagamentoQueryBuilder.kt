package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.Pagamento

interface PagamentoQueryBuilder {
    fun getPagamentoById(vararg pagamentoId: Long): PagamentoQueryBuilder
    fun getPagamentosByPedido(vararg pedidoId: Long): PagamentoQueryBuilder
    fun getAllPagamentos(): PagamentoQueryBuilder

    suspend fun buildExecuteAsSingle(): Pagamento
    suspend fun buildExecuteAsSList(): List<Pagamento>

}