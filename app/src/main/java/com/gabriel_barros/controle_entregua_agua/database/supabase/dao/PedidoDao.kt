package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.error.BadRequestException
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import io.github.jan.supabase.postgrest.from

class PedidoDAO : PedidoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"


//    override suspend fun getPedidoById(id: Long): Pedido? {
//        val pedido = supabase.from(schema, TABLE).select() {
//                filter { Pedido::id eq id }
//            }.decodeSingleOrNull<Pedido>()
//        return pedido
//
//    }

//    override suspend fun getPedidoByIdWithCliente(id: Long): Pedido? {
//
//            val pedido = supabase.from(schema, TABLE)
//                .select() {
//                    filter { Pedido::id eq id }
//                }.decodeSingleOrNull<Pedido>()
//
//
//            val cliente = supabase.from(schema, "clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id eq pedido?.cliente_id
//                    }
//                }.decodeSingle<ClienteSupabase>().let{ ClienteSupabase.to(it) }
//
//            return pedido?.to()
//
//
//    }

//    override suspend fun getPedidoByClienteId(clienteId: Long): List<Pedido> {
//        val pedido = supabase.from(schema, TABLE).select() {
//                filter { Pedido::cliente_id eq clienteId }
//            }.decodeList<Pedido>()
//        return pedido
//
//    }


//    override suspend fun getPedidosPendentes(): List<Pedido> {
//        val pedidos = supabase.from(schema, TABLE).select {
//                filter {
//                    Pedido::status eq StatusPedido.PENDENTE
//                }
//            }.decodeList<Pedido>()
//
//        return pedidos
//
//    }

//    override suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido> {
//        val itensPedidos = supabase.from(schema, "itens_pedidos").select {
//                filter {
//                    ItensPedido::pedido_id eq pedidoId
//                }
//            }.decodeList<ItensPedido>()
//        return itensPedidos
//
//    }

//    override suspend fun getAllPedidos(): List<Pedido> {
//        val pedidos = supabase.from(schema, TABLE).select().decodeList<Pedido>()
//        return pedidos
//
//    }

//    override suspend fun getAllPedidosWithClientes(): List<Pedido> {
//
//            val pedidos = supabase.from(schema, TABLE)
//                .select()
//                .decodeList<Pedido>()
//            val id_clientes = pedidos.map { it.cliente_id }
//            val clientes = supabase.from(schema, "clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id isIn listOf(id_clientes)
//                    }
//                }.decodeList<ClienteSupabase>().map { ClienteSupabase.to(it) }
//            return pedidos.map {
//                pdido -> pedido.to()
//            }
//
//        }
//    }

    override suspend fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido {
        try {
            val novoPedido = supabase
                .from(schema, TABLE)
                .upsert(pedido) { select() }
                    .decodeSingle<Pedido>()

            val item = itens.map { it.copy(pedido_id = novoPedido.id) }
            supabase.from(schema, "itens_pedidos").upsert(item)
            return novoPedido
        } catch (exception: Exception) {
            println(exception.message)
        }
        throw BadRequestException("Não foi possível adicionar pedido")
    }

    override suspend fun deletePedido(id: Long): Pedido {
        try {
            val pedido = supabase.from(schema, TABLE).delete {
                filter { Pedido::id eq id }
                select()
            }.decodeSingleOrNull<Pedido>()
            pedido?.let { return it }
        } catch (exception: Exception) {
            println(exception.toString())
        }
        throw BadRequestException("Não foi possível deletar pedido")
    }
}
