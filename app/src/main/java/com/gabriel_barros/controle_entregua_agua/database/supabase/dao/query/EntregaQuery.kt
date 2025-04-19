package com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query


import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.EntregaQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

class EntregaQuery : EntregaQueryBuilder {

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getEntregaById(entregaId: Long): EntregaQueryBuilder {
        query.add { eq(Entrega::id.name, entregaId) }
        return this
    }

    override fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder {
        query.add { eq(Entrega::id.name, entregaIds) }
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
            if(query.isNotEmpty()){
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
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Entrega>()
        query.clear()
        return result
    }
}

