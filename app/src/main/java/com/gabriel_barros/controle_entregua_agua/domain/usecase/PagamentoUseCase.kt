package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento

interface PagamentoUseCase {
    suspend fun getPagamentoById(id: Long): Pagamento?

    suspend fun getPagamentosByPedido(pedidoId: Long): List<Pagamento>

    suspend fun getAllPagamentos(): List<Pagamento>

    suspend fun savePagamento(clienteId: Long, valor: Double, tipoPagamento: TipoPagamento): List<Pagamento>

    suspend fun deletePagamento(id: Long): Pagamento?
}

interface PagamentoManager{
    data class PagamentoDTO(val valor: Double, val tipoPagamento: TipoPagamento)

    suspend fun processPagamento(clienteId: Long, pagamento: PagamentoDTO): List<Pagamento>
    suspend fun payPedido(pedidoId: Long, pagamento: PagamentoDTO)
    suspend fun payDebts(clienteId: Long, pagamento: PagamentoDTO)
    suspend fun increaseCredit(clienteId: Long, pagamento: PagamentoDTO)
}

interface PagamentoQuery {
    suspend fun getPagamentoById(pagamentoId: Long): Pagamento?
    suspend fun getPagamentosByPedido(pedidoId: Long): List<Pagamento>
    suspend fun getAllPagamentos(): List<Pagamento>
}