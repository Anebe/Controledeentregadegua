package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.PagamentoEntity

interface PagamentoQueryBuilder {
    fun getPagamentoById(vararg pagamentoId: Long): PagamentoQueryBuilder
    fun getPagamentosByPedido(vararg pedidoId: Long): PagamentoQueryBuilder
    fun getAllPagamentos(): PagamentoQueryBuilder

    suspend fun buildExecuteAsSingle(): PagamentoEntity
    suspend fun buildExecuteAsSList(): List<PagamentoEntity>

}