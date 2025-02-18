package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase

class EntregaService(
    private val entregaOut: EntregaPortOut
): EntregaUseCase {
    override fun getEntregaById(id: Long): Entrega? {
        return entregaOut.getEntregaById(id)
    }

    override fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        return entregaOut.getEntregaByPedidoId(pedidoId)
    }

    override fun getAllEntregas(): List<Entrega> {
        return entregaOut.getAllEntregas()
    }

    override fun saveEntrega(entrega: Entrega): Entrega? {
        return entregaOut.saveEntrega(entrega)
    }


    override fun deleteEntrega(id: Long): Entrega? {
        return entregaOut.deleteEntrega(id)
    }
}