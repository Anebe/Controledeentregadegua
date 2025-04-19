package com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PagamentoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

class PagamentoQuery: PagamentoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pagamentos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPagamentoById(pagamentoId: Long): PagamentoQueryBuilder {
        query.add { eq(Pagamento::id.name, pagamentoId) }
        return this
    }

    override fun getPagamentosByPedido(pedidoId: Long): PagamentoQueryBuilder {
        query.add { eq(Pagamento::pedido_id.name, pedidoId) }
        return this
    }

    override fun getAllPagamentos(): PagamentoQueryBuilder {
        return this
    }

    override suspend fun buildExecuteAsSingle(): Pagamento {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Pagamento>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Pagamento> {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Pagamento>()
        query.clear()
        return result
    }
}