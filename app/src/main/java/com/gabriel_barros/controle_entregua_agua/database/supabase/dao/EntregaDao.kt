package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.EntregaSupabase
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import io.github.jan.supabase.postgrest.from


class EntregaDao() : EntregaPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"

    override suspend fun getEntregaById(id: Long): Entrega? {
        val entrega = supabase.from(schema, TABLE).select() {
                filter { EntregaSupabase::id eq id }
            }.decodeSingleOrNull<EntregaSupabase>()
        return entrega?.let { Mapper.toEntrega(it) }

    }

    override suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select() {
                filter { EntregaSupabase::pedido_id eq pedidoId }
            }.decodeList<EntregaSupabase>()
        return entregas.map { Mapper.toEntrega(it) }

    }

    override suspend fun getAllEntregas(): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select().decodeList<EntregaSupabase>()
        return entregas.map { Mapper.toEntrega(it) }

    }

    override suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega> {
        val itens = supabase.from(schema, "itens_entregas").select {
                filter {
                    ItensEntrega::entrega_id eq entregaId
                }
            }.decodeList<ItensEntregaSupabase>()
        return itens.map { Mapper.toItensEntrega(it) }

    }

    override suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega> {
        val entregas = supabase.from(schema, TABLE).select {
                filter {
                    EntregaSupabase::pedido_id eq pedidoId
                }
            }.decodeList<EntregaSupabase>()

        return entregas.map { Mapper.toEntrega(it) }

    }


    override suspend fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega? {
        val novaEntrega =
            supabase.from(schema, TABLE).upsert(Mapper.toEntregaSupabase(entrega)) { select() }
                .decodeSingleOrNull<EntregaSupabase>()
        novaEntrega?.let { ent ->
            supabase.from(schema, TABLE)
                .insert(itens.map { Mapper.toItensEntregaSupabase(it.copy(entrega_id = ent.id)) })
        }
        return novaEntrega?.let { Mapper.toEntrega(it) }

    }


    override suspend fun deleteEntrega(id: Long): Entrega? {
        val entrega = supabase.from(schema, TABLE).delete {
                filter { EntregaSupabase::id eq id }
                select()
            }.decodeSingleOrNull<EntregaSupabase>()
        return entrega?.let { Mapper.toEntrega(it) }

    }
}
