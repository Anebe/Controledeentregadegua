package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.ktor.util.reflect.instanceOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ClienteDAO(
    private val supabase: SupabaseClient,
    private val TABLE: String = "clientes")
{
//    private val coroutineScope = CoroutineScope(Dispatchers.Main)

//    init {
//        coroutineScope.launch {
//            SupabaseClientProvider.signInAnonymously()
//        }
//    }
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

    suspend fun getAllClientesNomes(): Map<String, Long> {
        val clientes = this.getAllClientes()
        val clienteMap = clientes.flatMap { cliente ->
            val apelidosMap = cliente.apelidos?.takeIf { it.isNotEmpty() }?.map { it to cliente.id } ?: emptyList()
            listOf(cliente.nome to cliente.id) + apelidosMap
        }.toMap()
        return clienteMap
    }
}
