package com.gabriel_barros.supabase.dao.query

import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.ClienteSelecBuilder
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import kotlin.reflect.KProperty1

internal class ClienteQuery(_supabase: SupabaseClient): ClienteFilterBuilder, ClienteSelecBuilder{

    private val schema = "teste"
    private val supabase = _supabase
    private val TABLE: String = "clientes"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()
    private val columns = mutableListOf<String>()

    override fun getAllClientes(): ClienteFilterBuilder {
        return this
    }

    override fun getClienteById(vararg id: Long): ClienteFilterBuilder {
            query.add { isIn(Cliente::id.name, id.toList()) }
        return this
    }

    override suspend fun buildExecuteAsSingle(): Cliente {
        var supabaseColumns = if(columns.isEmpty()) Columns.ALL else Columns.list(columns)

        val result = supabase.from(schema, TABLE).select(supabaseColumns, ) {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Cliente>()
        query.clear()
        columns.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Cliente> {
        var supabaseColumns = if(columns.isEmpty()) Columns.ALL else Columns.list(columns)

        val result = supabase.from(schema, TABLE).select(supabaseColumns) {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Cliente>()
        query.clear()
        columns.clear()
        return result
    }

    override fun get(vararg props: KProperty1<Cliente, *>): ClienteFilterBuilder {
        columns.addAll(props.map { it.name })
        return this
    }

    override fun get(vararg props: String): ClienteFilterBuilder {
        columns.addAll(props)
        return this
    }

}