package com.gabriel_barros.supabase.dao

import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.error.BadRequestException
import com.gabriel_barros.domain.domain.portout.ClientePortOut
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from

internal class ClienteDAO: ClientePortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "clientes"

//    override suspend fun getClienteById(id: Long): Cliente? {
//        val cliente = supabase.from(schema, TABLE).select() {
//                filter { Cliente::id eq id }
//            }.decodeSingleOrNull<Cliente>()
//        return cliente
//
//    }

//    override suspend fun getAllClientes(): List<Cliente> {
//        val clientes = supabase.from(schema, TABLE).select().decodeList<Cliente>()
//        return clientes
//
//    }

    override suspend fun saveCliente(cliente: Cliente): Cliente {
        try {
            val response = supabase.from(schema, TABLE).upsert(cliente) { select() }
                .decodeSingleOrNull<Cliente>()

            response?.let {
                var novoCliente =response
//                if (cliente.enderecos.isNotEmpty()) {
//                    val enderecosAtualizados =
//                        cliente.enderecos.map { it.copy(cliente_id = response.id) }
//
//                    val enderecosSupabase = supabase.from(schema, "enderecos")
//                        .upsert(enderecosAtualizados) { select() }
//                        .decodeList<Endereco>()
//
//                    val enderecos = enderecosSupabase
//                    novoCliente = novoCliente.copy(enderecos = enderecos)
//                }
                return novoCliente
            }

        }catch (exception: Exception){
            println(exception.message)
        }
        throw BadRequestException("Não foi possível cadastrar cliente")
    }

    override suspend fun deleteCliente(id: Long): Cliente {
        require(id>0){ "Id inválido" }
        try {
            val cliente = supabase.from(schema, TABLE).delete {
                filter { Cliente::id eq id }
                select()
            }.decodeSingleOrNull<Cliente>()

            cliente?.let { return it }
        } catch (exception: Exception) {
        }
        throw BadRequestException("Não foi possível deletar cliente")

    }

//    override suspend fun getAllClientesNomes(): List<Pair<Long, String>> {
//        val clientes = this.getAllClientes()
//
//        val clienteMap = clientes.flatMap { cliente ->
//            val apelidosMap = cliente.apelidos.takeIf {
//                it.isNotEmpty()
//            }?.map { cliente.id to it } ?: emptyList()
//            listOf(cliente.id to cliente.nome) + apelidosMap
//        }.toList()
//        return clienteMap
//    }
}
