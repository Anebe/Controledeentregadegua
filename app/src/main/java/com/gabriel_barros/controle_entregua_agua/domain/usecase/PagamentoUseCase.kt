package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento

interface PagamentoUseCase {
    fun getPagamentoById(id: Long): Pagamento?

    fun getPagamentosByPedido(pedidoId: Long): List<Pagamento>

    fun getAllPagamentos(): List<Pagamento>

    fun savePagamento(clienteId: Long, valor: Double, tipoPagamento: TipoPagamento): List<Pagamento>

    fun deletePagamento(id: Long): Pagamento?
}