package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.PagamentoSupabase
import com.gabriel_barros.domain.entity.Pagamento
import com.gabriel_barros.domain.error.BadRequestException
import com.gabriel_barros.domain_portout.PagamentoPortOut
import io.github.jan.supabase.exceptions.HttpRequestException
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.postgrest.from
import io.ktor.client.plugins.HttpRequestTimeoutException

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
    override suspend fun savePagamento(pagamento: Pagamento): Pagamento {
        try {
            val response = supabase
                .from(schema, TABLE)
                .upsert(Mapper.toPagamentoSupabase(pagamento)) { select() }
                .decodeSingle<PagamentoSupabase>()
            return Mapper.toPagamento(response)

        }
        catch (exception: RestException) {
            //TODO entender melhor esse exception e adaptar
            throw BadRequestException("fiz merda")
        }
        catch (exception: HttpRequestTimeoutException) {
            throw BadRequestException("Problema interno. Tente novamente mais tarde")
        }
        catch (exception: HttpRequestException) {
            throw BadRequestException("Sem acesso à internet")

        }
        throw BadRequestException("Não foi possível adicionar pagamento")
    }

    //TODO tratar excpetions
//    override suspend fun deletePagamento(id: Long): Pagamento {
//        try {
//            val pagamento = supabase.from(schema, TABLE).delete {
//                filter { Pagamento::id eq id }
//                select()
//            }.decodeSingleOrNull<Pagamento>()
//            pagamento?.let { return it }
//        } catch (exception: Exception) {
//        }
//        throw BadRequestException("Não foi possível deletar pagamento")
//    }

//    override suspend fun getPagamentosByPedidoId(pedidoId: Long): List<Pagamento> {
//        val pagamento = supabase.from(schema, TABLE).select() {
//                filter { Pagamento::pedido_id eq pedidoId }
//            }.decodeList<Pagamento>()
//        return pagamento
//
//    }

}