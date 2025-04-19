package com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ClienteQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

class ClienteQuery: ClienteQueryBuilder{

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "clientes"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()


    override fun getAllClientes(): ClienteQueryBuilder {
        return this
    }

    override fun getClienteById(id: Long): ClienteQueryBuilder {
        query.add { eq(Cliente::id.name, id) }
        return this
    }

    override suspend fun buildExecuteAsSingle(): Cliente {

        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Cliente>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Cliente> {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Cliente>()
        query.clear()
        return result
    }

}