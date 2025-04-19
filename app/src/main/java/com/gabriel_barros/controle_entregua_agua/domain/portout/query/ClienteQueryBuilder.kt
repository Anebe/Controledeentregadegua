package com.gabriel_barros.controle_entregua_agua.domain.portout.query

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente

interface ClienteQueryBuilder {
    fun getClienteById(id: Long): ClienteQueryBuilder

    fun getAllClientes(): ClienteQueryBuilder

    suspend fun buildExecuteAsSingle(): Cliente
    suspend fun buildExecuteAsSList(): List<Cliente>

}