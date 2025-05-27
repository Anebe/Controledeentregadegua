package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.Mapper.toEntrega
import com.gabriel_barros.data_supabase.Mapper.toFrete
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.EntregaSupabase
import com.gabriel_barros.data_supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.domain.entity.Frete
import com.gabriel_barros.domain.entity.Status
import com.gabriel_barros.domain_portout.FreteSearch
import io.github.jan.supabase.postgrest.from
import kotlinx.serialization.Serializable

@Serializable
data class FreteSupabase(
    val id: Long = 0,
    val pedido_id: Long,
    val status: Status
)

class FreteSearchImp : FreteSearch {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val tableFretes: String = "fretes"
    private val tableEntrega: String = "entregas"
    private val tableItemEntregue: String = "itens_entregas"


    override suspend fun getFreteById(id: Long): Frete {
        val freteSupabase = supabase
            .from(schema, tableFretes)
            .select { filter { FreteSupabase::id eq id } }
            .decodeSingle<FreteSupabase>()

        return getFrete(freteSupabase)
    }

    override suspend fun getFreteByPedidoId(pedidoId: Long): Frete {
        val freteSupabase = supabase
            .from(schema, tableFretes)
            .select { filter { Frete::pedido_id eq pedidoId } }
            .decodeSingle<FreteSupabase>()

        return getFrete(freteSupabase)
    }

    private suspend fun getFrete(freteSupabase: FreteSupabase): Frete{
        val freteResult = toFrete(freteSupabase)

        val entregasSupabase = supabase
            .from(schema, tableEntrega)
            .select { filter { EntregaSupabase::frete_id eq freteSupabase.id } }
            .decodeList<EntregaSupabase>()

        entregasSupabase.forEach { entregaSupabase ->
            val entrega  = toEntrega(entregaSupabase)

            val itensEntregaSupabase = supabase.from(schema, tableItemEntregue)
                .select { filter { ItensEntregaSupabase::entrega_id eq entregaSupabase.id } }
                .decodeList<ItensEntregaSupabase>()
            itensEntregaSupabase.forEach { itemEntregaSupabase ->
                val itemEntregue = Mapper.toItemEntrega(itemEntregaSupabase)
                entrega.addProdutoEntregue(itemEntregue)
            }

            freteResult.addEntrega(entrega)
        }
        return freteResult
    }
}