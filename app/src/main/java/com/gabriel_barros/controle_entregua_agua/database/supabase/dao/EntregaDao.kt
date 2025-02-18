package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.EntregaSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking


class EntregaDao(
    private val supabase: SupabaseClient,
    private val TABLE: String = "entregas"): EntregaPortOut
{
    private val pedidoDAO = PedidoDAO(supabase)
    private val produtoDAO = ProdutoDAO(supabase)

    override fun getEntregaById(id: Long): Entrega? {
        return runBlocking{
            val entrega = supabase.from(TABLE)
                .select(columns = Columns.list("*")) {
                    filter { EntregaSupabase::id eq id }
                }
                .decodeSingleOrNull<EntregaSupabase>()
            return@runBlocking entrega?.let { Mapper.toEntrega(it) }
        }
    }

    override fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
        return runBlocking{
            val entregas = supabase.from(TABLE)
                .select(columns = Columns.list("*")) {
                    filter { EntregaSupabase::pedido_id eq pedidoId }
                }
                .decodeList<EntregaSupabase>()
            return@runBlocking entregas.map { Mapper.toEntrega(it) }
        }
    }

    override fun getAllEntregas(): List<Entrega> {
        return runBlocking{
            val entregas = supabase.from(TABLE)
                .select(columns = Columns.list("*"))
                .decodeList<EntregaSupabase>()
            return@runBlocking entregas.map { Mapper.toEntrega(it) }
        }
    }

    override fun saveEntrega(entrega: Entrega): Entrega? {
        return runBlocking{
            val novaEntrega = supabase.from(TABLE)
                .upsert(Mapper.toEntregaSupabase(entrega)){ select() }
            .decodeSingleOrNull<EntregaSupabase>()
            return@runBlocking novaEntrega?.let { Mapper.toEntrega(it) }
        }
    }


    override fun deleteEntrega(id: Long): Entrega? {
        return runBlocking{
            val entrega = supabase.from(TABLE)
                .delete {
                    filter { EntregaSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<EntregaSupabase>()
            return@runBlocking entrega?.let { Mapper.toEntrega(it) }
        }
    }
}
