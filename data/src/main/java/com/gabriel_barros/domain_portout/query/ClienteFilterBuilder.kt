package com.gabriel_barros.domain_portout.query

import com.gabriel_barros.domain.entity.Cliente
import kotlin.reflect.KProperty1

interface ClienteFilterBuilder {
    fun getClienteById(vararg id: Long): ClienteFilterBuilder

    fun getAllClientes(): ClienteFilterBuilder

    suspend fun buildExecuteAsSingle(): Cliente
    suspend fun buildExecuteAsSList(): List<Cliente>

}



//TODO não usar por enquanto, pois existem operação que podem ser afetada(ex: update, pois os valores não selecionados no select vão ter valor padrão/null e o update vai atualizar pra esses valores
interface ClienteSelecBuilder {
    fun get(vararg props: KProperty1<Cliente, *>): ClienteFilterBuilder

    fun get(vararg props: String): ClienteFilterBuilder
}
