package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente

interface ClienteUseCase {
    fun getAllClientesNomes(): List<Pair<Long, String>>

    fun getClienteById(id: Long): Cliente?

    fun getAllClientes(): List<Cliente>

    fun saveCliente(cliente: Cliente): Cliente?

    fun deleteCliente(id: Long): Cliente?
}