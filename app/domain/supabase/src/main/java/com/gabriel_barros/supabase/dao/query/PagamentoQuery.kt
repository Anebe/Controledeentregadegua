package com.gabriel_barros.supabase.dao.query

import com.gabriel_barros.domain.domain.entity.PagamentoEntity
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

internal class PagamentoQuery : PagamentoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pagamentos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPagamentoById(vararg pagamentoId: Long): PagamentoQueryBuilder {
        query.add { isIn(PagamentoEntity::id.name, pagamentoId.toList()) }
        return this
    }

    override fun getPagamentosByPedido(vararg pedidoId: Long): PagamentoQueryBuilder {
        query.add { isIn(PagamentoEntity::pedido_id.name, pedidoId.toList()) }
        return this
    }

    override fun getAllPagamentos(): PagamentoQueryBuilder {
        return this
    }

    override suspend fun buildExecuteAsSingle(): PagamentoEntity {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<PagamentoEntity>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<PagamentoEntity> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<PagamentoEntity>()
        query.clear()
        return result
    }
}