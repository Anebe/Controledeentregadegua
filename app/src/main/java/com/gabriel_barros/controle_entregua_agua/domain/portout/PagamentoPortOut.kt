package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento

interface PagamentoPortOut {
    fun getPagamentoById(id: Long): Pagamento?

    fun getAllPagamentos(): List<Pagamento>

    fun savePagamento(pagamento: Pagamento): Pagamento?

    fun deletePagamento(id: Long): Pagamento?

    fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento>?

}