package com.gabriel_barros.domain.domain.portout.query

import com.gabriel_barros.domain.domain.entity.Cliente
import kotlin.reflect.KProperty1

interface ClienteFilterBuilder {
    fun getClienteById(vararg id: Long): ClienteFilterBuilder

    fun getAllClientes(): ClienteFilterBuilder

    suspend fun buildExecuteAsSingle(): Cliente
    suspend fun buildExecuteAsSList(): List<Cliente>

}



interface ClienteSelecBuilder {
    fun get(vararg props: KProperty1<Cliente, *>): ClienteFilterBuilder

    fun get(vararg props: String): ClienteFilterBuilder
}
