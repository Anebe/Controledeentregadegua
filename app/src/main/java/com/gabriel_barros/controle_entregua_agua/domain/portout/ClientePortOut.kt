package com.gabriel_barros.controle_entregua_agua.domain.portout

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente

interface ClientePortOut {
    suspend fun saveCliente(cliente: Cliente): Cliente

    suspend fun deleteCliente(id: Long): Cliente
}

@Deprecated("Substituido por Query Builder")
interface ClienteGet {
    suspend fun getAllClientesNomes(): List<Pair<Long, String>>

    suspend fun getClienteById(id: Long): Cliente?

    suspend fun getAllClientes(): List<Cliente>
}