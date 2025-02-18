package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase

class PagametoService(
    private val pagamentoOut: PagamentoPortOut
): PagamentoUseCase {
    override fun getPagamentoById(id: Long): Pagamento? {
        return pagamentoOut.getPagamentoById(id)
    }

    override fun getAllPagamentos(): List<Pagamento> {
        return pagamentoOut.getAllPagamentos()
    }

    override fun savePagamento(pagamento: Pagamento): Pagamento? {
        return pagamentoOut.savePagamento(pagamento)
    }


    override fun deletePagamento(id: Long): Pagamento? {
        return pagamentoOut.deletePagamento(id)
    }
}