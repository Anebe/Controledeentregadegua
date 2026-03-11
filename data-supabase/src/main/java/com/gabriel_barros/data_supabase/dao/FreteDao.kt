package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.Mapper.toEntrega
import com.gabriel_barros.data_supabase.Mapper.toEntregaSupabase
import com.gabriel_barros.data_supabase.Mapper.toItemEntregaSupabase
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.dao.query.FreteSupabase
import com.gabriel_barros.data_supabase.entity.EntregaSupabase
import com.gabriel_barros.domain.entity.Entrega
import com.gabriel_barros.domain.entity.Frete
import com.gabriel_barros.domain.entity.ProdutoEntregue
import com.gabriel_barros.domain.error.BadRequestException
import com.gabriel_barros.domain_portout.FretePortOut
import io.github.jan.supabase.postgrest.from


//internal object ProdutoEntregueDao {
//    private val schema = SupabaseClientProvider.schema
//    private val supabase = SupabaseClientProvider.supabase
//    private val itemEntregueTable: String = "itens_entregas"
//
//    suspend fun save(produtos: List<ProdutoEntregue>): Set<ProdutoEntregue> {
//        val objParaSalvar = toItemEntregaSupabase(produtos)
//        val produtosEntreguesSupabase = supabase
//            .from(schema, itemEntregueTable)
//            .insert(objParaSalvar) { select() }
//            .decodeList<ItensEntregaSupabase>()
//
//        val produtosEntreguesSalvos = produtosEntreguesSupabase.map { Mapper.toItemEntrega(it) }
//        return produtosEntreguesSalvos.toSet()
//    }
//}

internal class FreteDao() : FretePortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val freteTable: String = "fretes"
    private val entregaTable: String = "entregas"
    private val itemEntregueTable: String = "itens_entregas"


    override suspend fun saveFrete(freteParaSalvar: Frete): Frete {
        try {
            val freteSupabase = supabase
                .from(schema, freteTable)
                .upsert(Mapper.toFreteSupabase(freteParaSalvar)) { select() }
                .decodeSingle<FreteSupabase>()

            val produtosSalvos = saveEntrega(freteParaSalvar.getEntregas(), freteSupabase.id)
            val freteSalvo = Mapper.toFrete(freteSupabase)
            freteSalvo.addEntrega(produtosSalvos)

            return freteSalvo
        } catch (exception: Exception) {
            throw exception
            //TODO melhorar o catch
        }
        throw BadRequestException("Não foi possível adicionar entrega")
    }

    private suspend fun saveEntrega(entregasParaSalvar: List<Entrega>, freteId: Long): List<Entrega> {
        val entregasSalvas = mutableListOf<Entrega>()
        entregasParaSalvar
            .filter { it.id < 1 }
            .forEach { entregaParaSalvar ->

                val entregaSupabase = supabase
                    .from(schema, entregaTable)
                    .upsert(toEntregaSupabase(entregaParaSalvar, freteId)) { select() }
                    .decodeSingle<EntregaSupabase>()

                val entregaSalva = toEntrega(entregaSupabase)

                val produtosEntregues = saveProdutoEntregue(entregaParaSalvar.getProdutosEntregue(), entregaSalva.id)
                entregaSalva.addProdutoEntregue(produtosEntregues)
                entregasSalvas.add(entregaSalva)
            }
        return entregasSalvas
    }

    private suspend fun saveProdutoEntregue(
        produtosEntreguesParaSalvar: Set<ProdutoEntregue>,
        entregaId: Long
    ): Set<ProdutoEntregue> {
        var produtosEntreguesSupabase =
            produtosEntreguesParaSalvar.map { toItemEntregaSupabase(it, entregaId) }

        produtosEntreguesSupabase = supabase
            .from(schema, itemEntregueTable)
            .upsert(produtosEntreguesSupabase) { select() }
            .decodeList()

        val produtosEntreguesSalvos = produtosEntreguesSupabase.map { Mapper.toItemEntrega(it) }
        return produtosEntreguesSalvos.toSet()
    }


}

