package com.gabriel_barros.domain_portout.query

import com.gabriel_barros.domain.entity.Divida
import com.gabriel_barros.domain.entity.Pagamento


interface DividaQueryBuilder{
    fun byId(id: Long): DividaQueryBuilder
    fun byPedidoId(pedidoId: Long): DividaQueryBuilder
    suspend fun getSigle(): Divida
}

interface PagamentoQueryBuilder {
    fun getPagamentoById(vararg pagamentoId: Long): PagamentoQueryBuilder
    fun getPagamentosByDivida(vararg pedidoId: Long): PagamentoQueryBuilder
    fun getAllPagamentos(): PagamentoQueryBuilder

    suspend fun buildExecuteAsSingle(): Pagamento
    suspend fun buildExecuteAsSList(): List<Pagamento>

}