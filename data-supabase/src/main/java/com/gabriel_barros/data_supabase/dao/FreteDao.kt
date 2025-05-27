package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.Mapper.toEntrega
import com.gabriel_barros.data_supabase.Mapper.toEntregaSupabase
import com.gabriel_barros.data_supabase.Mapper.toItemEntregaSupabase
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.dao.query.FreteSupabase
import com.gabriel_barros.data_supabase.entity.EntregaSupabase
import com.gabriel_barros.data_supabase.entity.ItensEntregaSupabase
import com.gabriel_barros.domain.entity.Entrega
import com.gabriel_barros.domain.entity.Frete
import com.gabriel_barros.domain.entity.ProdutoEntregue
import com.gabriel_barros.domain.error.BadRequestException
import com.gabriel_barros.domain_portout.FretePortOut
import io.github.jan.supabase.postgrest.from


internal class FreteDao() : FretePortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val freteTable: String = "fretes"
    private val entregaTable: String = "entregas"
    private val itemEntregueTable: String = "itens_entregas"


//    override suspend fun getEntregaById(id: Long): Entrega? {
//        val entrega = supabase.from(schema, TABLE).select {
//                filter { Entrega::id eq id }
//            }.decodeSingleOrNull<Entrega>()
//        return entrega
//
//    }

//    override suspend fun getEntregaByPedidoId(pedidoId: Long): List<Entrega> {
//        val entregas = supabase.from(schema, TABLE).select {
//                filter { Entrega::pedido_id eq pedidoId }
//            }.decodeList<Entrega>()
//        return entregas
//    }


//    override suspend fun getAllEntregas(): List<Entrega> {
//        val entregas = supabase.from(schema, TABLE).select().decodeList<Entrega>()
//        return entregas
//
//    }

//    override suspend fun getAllItensByEntregaId(entregaId: Long): List<ItensEntrega> {
//        val itens = supabase.from(schema, "itens_entregas").select {
//                filter { ItensEntrega::entrega_id eq entregaId }
//            }.decodeList<ItensEntrega>()
//        return itens
//
//    }

//    override suspend fun getAllEntregasByPedido(pedidoId: Long): List<Entrega> {
//        val entregas = supabase.from(schema, TABLE).select {
//                filter {
//                    Entrega::pedido_id eq pedidoId
//                }
//            }.decodeList<Entrega>()
//
//        return entregas
//
//    }

//    @Deprecated("")
//    override suspend fun saveEntrega(entrega: EntregaEntity, itens: List<ItensEntrega>): EntregaEntity {
//        try {
//            val novaEntrega = supabase.from(schema, entregaTable).upsert(entrega) { select() }
//                .decodeSingleOrNull<EntregaEntity>()
//            novaEntrega?.let { ent ->
//                supabase.from(schema, "itens_entregas")
//                    .insert(itens.map { it.copy(entrega_id = ent.id) })
//            }
//            novaEntrega?.let { return it }
//        } catch (exception: Exception) {
//            //TODO melhorar o catch
//        }
//        throw BadRequestException("Não foi possível adicionar entrega")
//    }

    private fun createFrete(freteSupabase: FreteSupabase, entregasSupabase: List<EntregaSupabase>, produtosEntregues: List<ItensEntregaSupabase>){
        val frete = Mapper.toFrete(freteSupabase)

        entregasSupabase.forEach { entregaSupabase ->

        }
    }
    override suspend fun saveFrete(freteParaSalvar: Frete): Frete {
        try {
            val freteSupabase = supabase
                .from(schema, freteTable)
                .insert(Mapper.toFreteSupabase(freteParaSalvar)){ select() }
                .decodeSingle<FreteSupabase>()

            val produtosSalvos = saveEntrega(freteParaSalvar.getEntregas())
            val freteSalvo = Mapper.toFrete(freteSupabase)
            freteSalvo.addEntrega(produtosSalvos)

            return freteSalvo
        } catch (exception: Exception) {
            //TODO melhorar o catch
        }
        throw BadRequestException("Não foi possível adicionar entrega")
    }
    private suspend fun saveEntrega(entregasParaSalvar: List<Entrega>): List<Entrega>{
        val entregasSalvas = mutableListOf<Entrega>()
        entregasParaSalvar.filter { it.id < 1 }.forEach { entregaParaSalvar ->
            val entregaSupabase = supabase
                .from(schema, entregaTable)
                .insert(toEntregaSupabase(entregaParaSalvar)) { select() }
                .decodeSingle<EntregaSupabase>()
            val entregaSalva = toEntrega(entregaSupabase)
            val produtosEntregues = saveProdutoEntregue(entregaParaSalvar.getProdutosEntregue(), entregaSalva.id)
            entregaSalva.addProdutoEntregue(produtosEntregues)
            entregasSalvas.add(entregaSalva)
        }
        return entregasSalvas
    }

    private suspend fun saveProdutoEntregue(produtosEntreguesParaSalvar: Set<ProdutoEntregue>, entregaId: Long): Set<ProdutoEntregue>{
        var produtosEntreguesSupabase = produtosEntreguesParaSalvar.map { toItemEntregaSupabase(it, entregaId) }

        produtosEntreguesSupabase = supabase
            .from(schema, itemEntregueTable)
            .insert(produtosEntreguesSupabase){ select() }
            .decodeList()

        val produtosEntreguesSalvos = produtosEntreguesSupabase.map{ Mapper.toItemEntrega(it) }
        return produtosEntreguesSalvos.toSet()
    }

//    override suspend fun deleteEntrega(id: Long): EntregaEntity {
//        try {
//            val entrega = supabase.from(schema, entregaTable).delete {
//                filter { EntregaEntity::id eq id }
//                select()
//            }.decodeSingleOrNull<EntregaEntity>()
//            entrega?.let { return it }
//        } catch (exception: Exception) {
//            //TODO melhorar o catch
//        }
//        throw BadRequestException("Não foi possível deletar entrega")
//    }
}

