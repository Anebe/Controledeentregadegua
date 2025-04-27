package com.gabriel_barros.supabase.dao

import com.gabriel_barros.domain.domain.entity.PagamentoEntity
import com.gabriel_barros.domain.domain.error.BadRequestException
import com.gabriel_barros.domain.domain.portout.PagamentoPortOut
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.postgrest.from

internal class PagamentoDao : PagamentoPortOut {
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
    override suspend fun savePagamento(pagamento: PagamentoEntity): PagamentoEntity {
        try {
            val response =
                supabase.from(schema, TABLE).upsert(pagamento) { select() }
                    .decodeSingleOrNull<PagamentoEntity>()
            response?.let { return it }

        } catch (exception: Exception) {
        }
        throw BadRequestException("Não foi possível adicionar pagamento")
    }

    //TODO tratar excpetions
    override suspend fun deletePagamento(id: Long): PagamentoEntity {
        try {
            val pagamento = supabase.from(schema, TABLE).delete {
                filter { PagamentoEntity::id eq id }
                select()
            }.decodeSingleOrNull<PagamentoEntity>()
            pagamento?.let { return it }
        } catch (exception: Exception) {
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