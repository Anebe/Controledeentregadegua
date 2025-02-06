package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ClienteDAO(private val supabase: SupabaseClient) {
    suspend fun getClienteById(id: Long): Cliente? {
        return supabase.from("Cliente")
            .select(columns = Columns.list("*")) {
                filter { Cliente::id eq id }
            }
            .decodeSingleOrNull()
    }

    suspend fun insertCliente(cliente: Cliente): Cliente? {
        return supabase.from("Cliente")
            .insert(cliente) { select() }
            .decodeSingleOrNull()
    }

    suspend fun updateCliente(cliente: Cliente): Cliente? {
        return supabase.from("Cliente")
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
        return supabase.from("Cliente")
            .delete {
                filter { Cliente::id eq id }
                select()
            }
            .decodeSingleOrNull()
    }
}
