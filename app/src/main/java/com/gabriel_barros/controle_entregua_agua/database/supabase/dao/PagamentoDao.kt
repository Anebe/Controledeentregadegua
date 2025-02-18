package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PagamentoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking

class PagamentoDao(
    private val supabase: SupabaseClient,
    private val TABLE: String = "pagamentos") : PagamentoPortOut
{
    override fun getPagamentoById(id: Long): Pagamento? {
        return runBlocking{
            val pagamento = supabase.from(TABLE)
                .select(columns = Columns.list("*")) {
                    filter { PagamentoSupabase::id eq id }
                }
                .decodeSingleOrNull<PagamentoSupabase>()
            return@runBlocking pagamento?.let{ Mapper.toPagamento(it) }
        }
    }

    override fun getAllPagamentos(): List<Pagamento> {
        return runBlocking{
            val pagamento = supabase.from(TABLE)
                .select()
                .decodeList<PagamentoSupabase>()
            return@runBlocking pagamento.map{ Mapper.toPagamento(it) }
        }
    }

    override fun savePagamento(pagamento: Pagamento): Pagamento? {
        return runBlocking{
            val response = supabase.from(TABLE)
                .insert(Mapper.toPagamentoSupabase(pagamento)) { select() }
                .decodeSingleOrNull<PagamentoSupabase>()
            return@runBlocking response?.let{ Mapper.toPagamento(it) }
        }
    }

    override fun deletePagamento(id: Long): Pagamento? {
        return runBlocking{
            val pagamento = supabase.from(TABLE)
                .delete {
                    filter { PagamentoSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<PagamentoSupabase>()
            return@runBlocking pagamento?.let{ Mapper.toPagamento(it) }
        }
    }

    override fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento>? {
        return runBlocking{
            val pagamento = supabase.from(TABLE)
                .select(columns = Columns.list("*")) {
                    filter { PagamentoSupabase::pedido_id eq pedidoId }
                }
                .decodeList<PagamentoSupabase>()
            return@runBlocking pagamento.map{ Mapper.toPagamento(it) }
        }
    }

}