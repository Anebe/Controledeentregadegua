package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class PedidoDAO(
    private val supabase: SupabaseClient,
    private val TABLE: String = "pedidos"
) {

    suspend fun getPedidoById(id: Long): Pedido? {
        return supabase.from(TABLE)
            .select(columns = Columns.list("*")) {
                filter { Pedido::id eq id }
            }
            .decodeSingleOrNull()
    }

    suspend fun getAllPedidos(): List<Pedido> {
        return supabase.from(TABLE)
            .select(columns = Columns.list("*"))
            .decodeList<Pedido>()
    }

    suspend fun insertPedido(pedido: Pedido): Pedido? {
        return supabase.from(TABLE)
            .insert(pedido) { select() }
            .decodeSingleOrNull()
    }

    suspend fun updatePedido(pedido: Pedido): Pedido? {
        return supabase.from(TABLE)
            .update ({
                Pedido::cliente_id setTo pedido.cliente_id
                Pedido::qtd setTo pedido.qtd
                Pedido::data setTo pedido.data
                Pedido::troco setTo pedido.troco
            }) {
                filter { Pedido::id eq pedido.id }
                select()
            }
            .decodeSingleOrNull()
    }

    suspend fun deletePedido(id: Long): Pedido? {
        return supabase.from(TABLE)
            .delete {
                filter { Pedido::id eq id }
                select()
            }
            .decodeSingleOrNull()
    }
}
