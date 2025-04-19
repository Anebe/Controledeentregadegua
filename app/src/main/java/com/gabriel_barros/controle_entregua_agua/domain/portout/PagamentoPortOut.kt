package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento

interface PagamentoPortOut {
    suspend fun savePagamento(pagamento: Pagamento): Pagamento

    suspend fun deletePagamento(id: Long): Pagamento
}

@Deprecated("Substituido por Query Builder")
interface PagamentoGet {
    suspend fun getPagamentoById(id: Long): Pagamento?

    suspend fun getAllPagamentos(): List<Pagamento>

    suspend fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento>
}