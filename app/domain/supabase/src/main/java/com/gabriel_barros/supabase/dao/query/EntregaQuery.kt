package com.gabriel_barros.supabase.dao.query


import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

internal class EntregaQuery : EntregaQueryBuilder {

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getEntregaById(vararg entregaId: Long): EntregaQueryBuilder {
        query.add { isIn(Entrega::id.name, entregaId.toList()) }
        return this
    }

    override fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder {
        query.add { isIn(Entrega::id.name, entregaIds) }
        return this
    }

    override fun getAllEntregas(): EntregaQueryBuilder {
        return this
    }

    override fun getAllEntregasByPedido(pedidoId: Long): EntregaQueryBuilder {
        query.add { eq(Entrega::pedido_id.name, pedidoId) }
        return this
    }

    override suspend fun buildExecuteAsSingle(): Entrega {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Entrega>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Entrega> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Entrega>()
        query.clear()
        return result
    }
}

