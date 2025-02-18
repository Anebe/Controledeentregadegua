package com.gabriel_barros.controle_entregua_agua.database

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PedidoDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PagamentoSupabase

object ProcessarPagamento {
    val pedidosDao = PedidoDAO(SupabaseClientProvider.supabase)
    suspend fun processar(pagamento: PagamentoSupabase, clienteId: Long){
        val pedidos = pedidosDao.getPedidoByClienteId(clienteId)

        pedidos.forEach {

        }
    }
}