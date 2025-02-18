package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PedidoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking

class PedidoDAO(
    private val supabase: SupabaseClient,
    private val TABLE: String = "pedidos"
): PedidoPortOut {
    private val clienteDAO = ClienteDAO(supabase)

    override fun getPedidoById(id: Long): Pedido? {
        return runBlocking{
            val pedido = supabase.from(TABLE)
                .select() {
                    filter { PedidoSupabase::id eq id }
                }.decodeSingleOrNull<PedidoSupabase>()
            return@runBlocking pedido?.let { Mapper.toPedido(pedido) }
        }
    }


//    override fun getPedidoByIdWithCliente(id: Long): Pedido? {
//
//        return runBlocking{
//            val pedido = supabase.from(TABLE)
//                .select(columns = Columns.list("*")) {
//                    filter { PedidoSupabase::id eq id }
//                }.decodeSingleOrNull<PedidoSupabase>()
//
//
//            val cliente = supabase.from("clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id eq pedido?.cliente_id
//                    }
//                }.decodeSingle<ClienteSupabase>().let{ ClienteSupabase.to(it) }
//
//            return@runBlocking pedido?.to()
//        }
//
//    }

    override fun getPedidoByClienteId(clienteId: Long): List<Pedido>{
        return runBlocking {
            val pedido = supabase.from(TABLE)
                .select(columns = Columns.list("*")){
                    filter { PedidoSupabase::cliente_id eq clienteId }
                }
                .decodeList<PedidoSupabase>()
            return@runBlocking pedido.map { Mapper.toPedido(it) }

        }
    }

    override fun getAllPedidos(): List<Pedido> {
        return runBlocking{
            val pedidos = supabase.from(TABLE)
                .select(columns = Columns.list("*"))
                .decodeList<PedidoSupabase>()
            return@runBlocking pedidos.map { Mapper.toPedido(it) }
        }
    }

//    override fun getAllPedidosWithClientes(): List<Pedido> {
//
//        return runBlocking{
//            val pedidos = supabase.from(TABLE)
//                .select()
//                .decodeList<PedidoSupabase>()
//            val id_clientes = pedidos.map { it.cliente_id }
//            val clientes = supabase.from("clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id isIn listOf(id_clientes)
//                    }
//                }.decodeList<ClienteSupabase>().map { ClienteSupabase.to(it) }
//            return@runBlocking pedidos.map {
//                pedido -> pedido.to()
//            }
//
//        }
//    }

    override fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido? {

        return runBlocking{
             val novoPedido = supabase.from(TABLE)
                .upsert(Mapper.toPedidoSupabase(pedido)) { select() }
                .decodeSingleOrNull<PedidoSupabase>()
            val itens = supabase.from("itens_pedidos")
                .upsert(itens)
            return@runBlocking novoPedido?.let { Mapper.toPedido(it) }
        }
    }

    override fun deletePedido(id: Long): Pedido? {
        return runBlocking{
            val pedido = supabase.from(TABLE)
                .delete {
                    filter { PedidoSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<PedidoSupabase>()
            return@runBlocking pedido?.let { Mapper.toPedido(it) }
        }
    }
}
