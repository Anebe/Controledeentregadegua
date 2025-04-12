package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase

class ClienteService(private val clienteOut: ClientePortOut): ClienteUseCase {
    override suspend fun getAllClientesNomes(): List<Pair<Long, String>>{
        return clienteOut.getAllClientesNomes()
    }

    override suspend fun getClienteById(id: Long): Cliente? {
        return clienteOut.getClienteById(id)
    }

    override suspend fun getAllClientes(): List<Cliente> {
        return clienteOut.getAllClientes()
    }

    override suspend fun saveCliente(cliente: Cliente): Cliente? {
        return clienteOut.saveCliente(cliente)
    }

    override suspend fun deleteCliente(id: Long): Cliente? {
        return clienteOut.deleteCliente(id)
    }

}