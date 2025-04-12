package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ClienteSupabase
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.EnderecoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import io.github.jan.supabase.postgrest.from

class ClienteDAO() : ClientePortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "clientes"

    override suspend fun getClienteById(id: Long): Cliente? {
        val cliente = supabase.from(schema, TABLE).select() {
                filter { ClienteSupabase::id eq id }
            }.decodeSingleOrNull<ClienteSupabase>()
        return cliente?.let { Mapper.toCliente(it) }

    }

    override suspend fun getAllClientes(): List<Cliente> {
        val clientes = supabase.from(schema, TABLE).select().decodeList<ClienteSupabase>()
        return clientes.map { Mapper.toCliente(it) }

    }

    override suspend fun saveCliente(cliente: Cliente): Cliente? {
        val response =
            supabase.from(schema, TABLE).upsert(Mapper.toClienteSupabase(cliente)) { select() }
                .decodeSingleOrNull<ClienteSupabase>()

        response?.let {
            var novoCliente = Mapper.toCliente(response)
            if (cliente.enderecos.isNotEmpty()) {
                val enderecosAtualizados =
                    cliente.enderecos.map { it.copy(cliente_id = response.id) }

                val enderecosSupabase = supabase.from(schema, "enderecos")
                    .upsert(enderecosAtualizados.map { Mapper.toEnderecoSupabase(it) }) { select() }
                    .decodeList<EnderecoSupabase>()

                val enderecos = enderecosSupabase.map { Mapper.toEndereco(it) }
                novoCliente = novoCliente.copy(enderecos = enderecos)
            }
            return novoCliente
        }
        return null
    }

    override suspend fun deleteCliente(id: Long): Cliente? {
        val cliente = supabase.from(schema, TABLE).delete {
                filter { ClienteSupabase::id eq id }
                select()
            }.decodeSingleOrNull<ClienteSupabase>()
        return cliente?.let { Mapper.toCliente(it) }

    }

    override suspend fun getAllClientesNomes(): List<Pair<Long, String>> {
        val clientes = this.getAllClientes()

        val clienteMap = clientes.flatMap { cliente ->
            val apelidosMap = cliente.apelidos.takeIf {
                it.isNotEmpty()
            }?.map { cliente.id to it } ?: emptyList()
            listOf(cliente.id to cliente.nome) + apelidosMap
        }.toList()
        return clienteMap
    }
}
