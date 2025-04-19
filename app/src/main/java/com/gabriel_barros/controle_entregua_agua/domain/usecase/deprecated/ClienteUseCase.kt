package com.gabriel_barros.controle_entregua_agua.domain.usecase.deprecated

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente

@Deprecated("Subsituido por Manager e Query Builder")
interface ClienteUseCase {
    suspend fun getAllClientesNomes(): List<Pair<Long, String>>

    suspend fun getClienteById(id: Long): Cliente?

    suspend fun getAllClientes(): List<Cliente>

    suspend fun saveCliente(cliente: Cliente): Cliente?

    suspend fun deleteCliente(id: Long): Cliente?
}

