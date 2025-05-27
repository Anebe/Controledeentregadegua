package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.PagamentoSupabase
import com.gabriel_barros.domain.entity.Pagamento
import com.gabriel_barros.domain_portout.query.PagamentoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult

internal class PagamentoQuery : PagamentoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pagamentos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPagamentoById(vararg pagamentoId: Long): PagamentoQueryBuilder {
        query.add { isIn(PagamentoSupabase::id.name, pagamentoId.toList()) }
        return this
    }

    override fun getPagamentosByDivida(vararg dividaId: Long): PagamentoQueryBuilder {
        query.add { isIn(PagamentoSupabase::divida_id.name, dividaId.toList()) }
        return this
    }

    override fun getAllPagamentos(): PagamentoQueryBuilder {
        return this
    }

    private suspend fun build(): PostgrestResult{
        return supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }
    }

    override suspend fun buildExecuteAsSingle(): Pagamento {
        val result = build().decodeSingle<PagamentoSupabase>()
        query.clear()
        return Mapper.toPagamento(result)
    }

    override suspend fun buildExecuteAsSList(): List<Pagamento> {
        val result = build().decodeList<PagamentoSupabase>()
        query.clear()
        return result.map { Mapper.toPagamento(it) }
    }
}