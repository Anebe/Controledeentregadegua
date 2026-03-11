package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.ClienteSupabase
import com.gabriel_barros.domain.entity.Cliente
import com.gabriel_barros.domain_portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain_portout.query.ClienteSelecBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlin.reflect.KProperty1

internal class ClienteQuery: ClienteFilterBuilder, ClienteSelecBuilder{
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
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

    private suspend fun execute(): PostgrestResult {
        var supabaseColumns = if(columns.isEmpty()) Columns.ALL else Columns.list(columns)

        return supabase.from(schema, TABLE).select(supabaseColumns) {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }
    }
    override suspend fun buildExecuteAsSingle(): Cliente {
        val result = execute().decodeSingle<ClienteSupabase>()

        query.clear()
        columns.clear()
        return Mapper.toCliente(result)
    }

    override suspend fun buildExecuteAsSList(): List<Cliente> {
        val result = execute().decodeList<ClienteSupabase>()

        query.clear()
        columns.clear()
        return result.map { Mapper.toCliente(it) }
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