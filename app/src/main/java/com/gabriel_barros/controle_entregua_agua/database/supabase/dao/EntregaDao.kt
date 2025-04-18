package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder


class EntregaDao() : EntregaPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"

    override suspend fun getEntregaById(id: Long): Entrega? {
        val entrega = supabase.from(schema, TABLE).select {
                filter { Entrega::id eq id }
            }.decodeSingleOrNull<Entrega>()
        return entrega

    }

    override suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select {
                filter { Entrega::pedido_id eq pedidoId }
            }.decodeList<Entrega>()
        return entregas
    }


    override suspend fun getAllEntregas(): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select().decodeList<Entrega>()
        return entregas

    }

    override suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega> {
        val itens = supabase.from(schema, "itens_entregas").select {
                filter {
                    ItensEntrega::entrega_id eq entregaId
                }
            }.decodeList<ItensEntrega>()
        return itens

    }

    override suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select {
                filter {
                    Entrega::pedido_id eq pedidoId
                }
            }.decodeList<Entrega>()

        return entregas

    }


    override suspend fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega? {
        val novaEntrega =
            supabase.from(schema, TABLE).upsert(entrega) { select() }
                .decodeSingleOrNull<Entrega>()
        novaEntrega?.let { ent ->
            supabase.from(schema, "itens_entregas")
                .insert(itens.map { Mapper.toItensEntregaSupabase(it.copy(entrega_id = ent.id)) })
        }
        return novaEntrega

    }


    override suspend fun deleteEntrega(id: Long): Entrega? {
        val entrega = supabase.from(schema, TABLE).delete {
                filter { Entrega::id eq id }
                select()
            }.decodeSingleOrNull<Entrega>()
        return entrega

    }
}

interface And<Type,Value>{
    fun and(table: String, value: Value): Type
}

class AndImp: And<PostgrestFilterBuilder.() -> Unit, Long>{
    override fun and(
        table: String,
        value: Long
    ): PostgrestFilterBuilder.() -> Unit {
        return { eq(table, value) }
    }

}