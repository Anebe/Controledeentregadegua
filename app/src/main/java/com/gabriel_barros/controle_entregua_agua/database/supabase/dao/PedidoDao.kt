package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ItensPedidoSupabase
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PedidoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PedidoDAO() : PedidoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"

    override suspend fun getPedidoById(id: Long): Pedido? {
        val pedido = supabase.from(schema, TABLE).select() {
                filter { PedidoSupabase::id eq id }
            }.decodeSingleOrNull<PedidoSupabase>()
        return pedido?.let { Mapper.toPedido(pedido) }

    }


//    override suspend fun getPedidoByIdWithCliente(id: Long): Pedido? {
//
//            val pedido = supabase.from(schema, TABLE)
//                .select() {
//                    filter { PedidoSupabase::id eq id }
//                }.decodeSingleOrNull<PedidoSupabase>()
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

    override suspend fun getPedidoByClienteId(clienteId: Long): List<Pedido> {
        val pedido = supabase.from(schema, TABLE).select() {
                filter { PedidoSupabase::cliente_id eq clienteId }
            }.decodeList<PedidoSupabase>()
        return pedido.map { Mapper.toPedido(it) }

    }

    override suspend fun getAllPedidosAsync(callback: (List<Pedido>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val pedidos = supabase.from(schema, TABLE).select().decodeList<PedidoSupabase>()
                .map { Mapper.toPedido(it) }

            callback(pedidos) // já está no contexto de IO
        }
    }

    override suspend fun getPedidosPendentes(): List<Pedido> {
        val pedidos = supabase.from(schema, TABLE).select {
                filter {
                    PedidoSupabase::status eq StatusPedido.PENDENTE
                }
            }.decodeList<PedidoSupabase>()

        return pedidos.map { Mapper.toPedido(it) }

    }

    override suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido> {
        val itensPedidos = supabase.from(schema, "itens_pedidos").select {
                filter {
                    ItensPedidoSupabase::pedido_id eq pedidoId
                }
            }.decodeList<ItensPedidoSupabase>()
        return itensPedidos.map { Mapper.toItensPedido(it) }

    }

    override suspend fun getAllPedidos(): List<Pedido> {
        val pedidos = supabase.from(schema, TABLE).select().decodeList<PedidoSupabase>()
        return pedidos.map { Mapper.toPedido(it) }

    }

//    override suspend fun getAllPedidosWithClientes(): List<Pedido> {
//
//            val pedidos = supabase.from(schema, TABLE)
//                .select()
//                .decodeList<PedidoSupabase>()
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

    override suspend fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido? {

        val novoPedido =
            supabase.from(schema, TABLE).upsert(Mapper.toPedidoSupabase(pedido)) { select() }
                .decodeSingleOrNull<PedidoSupabase>()
        novoPedido?.let {

            val item =
                itens.map { Mapper.toItensPedidoSupabase(it.copy(pedido_id = novoPedido.id)) }
            val itens = supabase.from(schema, "itens_pedidos").upsert(item)
        }
        return novoPedido?.let { Mapper.toPedido(it) }

    }

    override suspend fun deletePedido(id: Long): Pedido? {
        val pedido = supabase.from(schema, TABLE).delete {
                filter { PedidoSupabase::id eq id }
                select()
            }.decodeSingleOrNull<PedidoSupabase>()
        return pedido?.let { Mapper.toPedido(it) }

    }
}
