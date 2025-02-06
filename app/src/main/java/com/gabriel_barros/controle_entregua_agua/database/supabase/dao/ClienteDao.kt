package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ClienteDAO(private val supabase: SupabaseClient,
    private val TABLE: String = "clientes") {

    suspend fun getClienteById(id: Long): Cliente? {
        return supabase.from(TABLE)
            .select(columns = Columns.list("*")) {
                filter { Cliente::id eq id }
            }
            .decodeSingleOrNull()
    }

    suspend fun getAllClientes(): List<Cliente> {
        return supabase.from(TABLE)
            .select(columns = Columns.list("*"))
            .decodeList<Cliente>()
    }

    suspend fun insertCliente(cliente: Cliente): Cliente? {
        return supabase.from(TABLE)
            .insert(cliente) { select() }
            .decodeSingleOrNull()
    }

    suspend fun updateCliente(cliente: Cliente): Cliente? {
        return supabase.from(TABLE)
            .update ({
                Cliente::nome setTo cliente.nome
                Cliente::descricao setTo cliente.descricao
            })
            {
                filter { Cliente::id eq cliente.id }
                select()
            }
            .decodeSingleOrNull()
    }

    suspend fun deleteCliente(id: Long): Cliente? {
        return supabase.from(TABLE)
            .delete {
                filter { Cliente::id eq id }
                select()
            }
            .decodeSingleOrNull()
    }
}
