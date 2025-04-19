package com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemEntregaQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

class ItemEntregaQuery : ItemEntregaQueryBuilder {

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "itens_entregas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getAllItensByEntregaId(entregaId: Long): ItemEntregaQueryBuilder {
        query.add { eq(ItensEntrega::entrega_id.name, entregaId) }
        return this
    }

    override fun getAllItensByItemPedidoId(itemPedidoId: Long): ItemEntregaQueryBuilder {
        query.add { eq(ItensEntrega::itemPedido_id.name, itemPedidoId) }
        return this
    }

    override suspend fun buildExecuteAsSingle(): ItensEntrega {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
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
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<ItensEntrega>()
        query.clear()
        return result
    }
}