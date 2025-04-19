package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import android.util.Log
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.error.BadRequestException
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import io.github.jan.supabase.postgrest.from

class PagamentoDao : PagamentoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pagamentos"

//    override suspend fun getPagamentoById(id: Long): Pagamento? {
//        val pagamento = supabase.from(schema, TABLE).select() {
//                filter { Pagamento::id eq id }
//            }.decodeSingleOrNull<Pagamento>()
//        return pagamento
//
//    }

//    override suspend fun getAllPagamentos(): List<Pagamento> {
//        val pagamento = supabase.from(schema, TABLE).select().decodeList<Pagamento>()
//        return pagamento
//
//    }

    //TODO tratar excpetions
    override suspend fun savePagamento(pagamento: Pagamento): Pagamento {
        try {
            val response =
                supabase.from(schema, TABLE).upsert(pagamento) { select() }
                    .decodeSingleOrNull<Pagamento>()
            response?.let { return it }

        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
        }
        throw BadRequestException("Não foi possível adicionar pagamento")
    }

    //TODO tratar excpetions
    override suspend fun deletePagamento(id: Long): Pagamento {
        try {
            val pagamento = supabase.from(schema, TABLE).delete {
                filter { Pagamento::id eq id }
                select()
            }.decodeSingleOrNull<Pagamento>()
            pagamento?.let { return it }
        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
        }
        throw BadRequestException("Não foi possível deletar pagamento")
    }

//    override suspend fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento> {
//        val pagamento = supabase.from(schema, TABLE).select() {
//                filter { Pagamento::pedido_id eq pedidoId }
//            }.decodeList<Pagamento>()
//        return pagamento
//
//    }

}