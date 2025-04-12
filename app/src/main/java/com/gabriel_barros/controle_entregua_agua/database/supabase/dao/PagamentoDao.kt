package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PagamentoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import io.github.jan.supabase.postgrest.from

class PagamentoDao(
) : PagamentoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pagamentos"

    override suspend fun getPagamentoById(id: Long): Pagamento? {
        val pagamento = supabase.from(schema, TABLE).select() {
                filter { PagamentoSupabase::id eq id }
            }.decodeSingleOrNull<PagamentoSupabase>()
        return pagamento?.let { Mapper.toPagamento(it) }

    }

    override suspend fun getAllPagamentos(): List<Pagamento> {
        val pagamento = supabase.from(schema, TABLE).select().decodeList<PagamentoSupabase>()
        return pagamento.map { Mapper.toPagamento(it) }

    }

    override suspend fun savePagamento(pagamento: Pagamento): Pagamento? {
        val response =
            supabase.from(schema, TABLE).upsert(Mapper.toPagamentoSupabase(pagamento)) { select() }
                .decodeSingleOrNull<PagamentoSupabase>()
        return response?.let { Mapper.toPagamento(it) }

    }

    override suspend fun deletePagamento(id: Long): Pagamento? {
        val pagamento = supabase.from(schema, TABLE).delete {
                filter { PagamentoSupabase::id eq id }
                select()
            }.decodeSingleOrNull<PagamentoSupabase>()
        return pagamento?.let { Mapper.toPagamento(it) }

    }

    override suspend fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento> {
        val pagamento = supabase.from(schema, TABLE).select() {
                filter { PagamentoSupabase::pedido_id eq pedidoId }
            }.decodeList<PagamentoSupabase>()
        return pagamento.map { Mapper.toPagamento(it) }

    }

}