package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.EntregaSupabase
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking


class EntregaDao(): EntregaPortOut
{
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"

    override fun getEntregaById(id: Long): Entrega? {
        return runBlocking{
            val entrega = supabase.from(schema, TABLE)
                .select() {
                    filter { EntregaSupabase::id eq id }
                }
                .decodeSingleOrNull<EntregaSupabase>()
            return@runBlocking entrega?.let { Mapper.toEntrega(it) }
        }
    }

    override fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        return runBlocking{
            val entregas = supabase.from(schema, TABLE)
                .select() {
                    filter { EntregaSupabase::pedido_id eq pedidoId }
                }
                .decodeList<EntregaSupabase>()
            return@runBlocking entregas.map { Mapper.toEntrega(it) }
        }
    }

    override fun getAllEntregas(): List<Entrega> {
        return runBlocking{
            val entregas = supabase.from(schema, TABLE)
                .select()
                .decodeList<EntregaSupabase>()
            return@runBlocking entregas.map { Mapper.toEntrega(it) }
        }
    }

    override fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega> {
        return runBlocking {
            val itens = supabase.from(schema, "itens_entregas")
                .select {filter {
                    ItensEntrega::entrega_id eq entregaId
                } }.decodeList<ItensEntregaSupabase>()
            return@runBlocking itens.map { Mapper.toItensEntrega(it) }
        }
    }

    override fun getAllEntregasByPedido(pedidoId: Long): List<Entrega> {
        return runBlocking {
            val entregas = supabase.from(schema, TABLE)
                .select { filter {
                    EntregaSupabase::pedido_id eq pedidoId
                } }.decodeList<EntregaSupabase>()

            return@runBlocking entregas.map { Mapper.toEntrega(it) }
        }
    }


    override fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega? {
        return runBlocking{
            val novaEntrega = supabase.from(schema, TABLE)
                .upsert(Mapper.toEntregaSupabase(entrega)){ select() }
            .decodeSingleOrNull<EntregaSupabase>()
            novaEntrega?.let { ent ->
                supabase.from(schema, TABLE)
                    .insert(itens.map { Mapper.toItensEntregaSupabase(it.copy(entrega_id = ent.id)) })
            }
            return@runBlocking novaEntrega?.let { Mapper.toEntrega(it) }
        }
    }


    override fun deleteEntrega(id: Long): Entrega? {
        return runBlocking{
            val entrega = supabase.from(schema, TABLE)
                .delete {
                    filter { EntregaSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<EntregaSupabase>()
            return@runBlocking entrega?.let { Mapper.toEntrega(it) }
        }
    }
}
