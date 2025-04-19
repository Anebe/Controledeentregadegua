package com.gabriel_barros.controle_entregua_agua.domain.portout.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento

interface PagamentoQueryBuilder {
    fun getPagamentoById(pagamentoId: Long): PagamentoQueryBuilder
    fun getPagamentosByPedido(pedidoId: Long): PagamentoQueryBuilder
    fun getAllPagamentos(): PagamentoQueryBuilder

    suspend fun buildExecuteAsSingle(): Pagamento
    suspend fun buildExecuteAsSList(): List<Pagamento>

}