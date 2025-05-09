package com.gabriel_barros.supabase.dao.query

import com.gabriel_barros.domain.domain.entity.ItensEntrega
import com.gabriel_barros.domain.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

internal class ItemEntregaQuery : ItemEntregaQueryBuilder {

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "itens_entregas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getAllItensByEntregaId(vararg entregaId: Long): ItemEntregaQueryBuilder {
        query.add { isIn(ItensEntrega::entrega_id.name, entregaId.toList()) }
        return this
    }

    override fun getAllItensByItemPedidoId(vararg itemPedidoId: Long): ItemEntregaQueryBuilder {
        query.add { isIn(ItensEntrega::itemPedido_id.name, itemPedidoId.toList()) }
        return this
    }

    override suspend fun buildExecuteAsSingle(): ItensEntrega {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<ItensEntrega>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<ItensEntrega> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<ItensEntrega>()
        query.clear()
        return result
    }
}