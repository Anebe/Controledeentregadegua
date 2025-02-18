package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento

interface PagamentoUseCase {
    fun getPagamentoById(id: Long): Pagamento?

    fun getAllPagamentos(): List<Pagamento>

    fun savePagamento(pagamento: Pagamento): Pagamento?

    fun deletePagamento(id: Long): Pagamento?
}