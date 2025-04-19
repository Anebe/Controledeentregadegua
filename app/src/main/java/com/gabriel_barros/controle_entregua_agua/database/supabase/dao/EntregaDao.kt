package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import android.util.Log
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.error.BadRequestException
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import io.github.jan.supabase.postgrest.from


class EntregaDao() : EntregaPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"

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


    override suspend fun saveEntrega(entrega: Entrega, itens: List<ItensEntrega>): Entrega {
        try {
            val novaEntrega = supabase.from(schema, TABLE).upsert(entrega) { select() }
                .decodeSingleOrNull<Entrega>()
            novaEntrega?.let { ent ->
                supabase.from(schema, "itens_entregas")
                    .insert(itens.map { it.copy(entrega_id = ent.id) })
            }
            novaEntrega?.let { return it }
        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
        }
        throw BadRequestException("Não foi possível adicionar entrega")
    }


    override suspend fun deleteEntrega(id: Long): Entrega {
        try {
            val entrega = supabase.from(schema, TABLE).delete {
                filter { Entrega::id eq id }
                select()
            }.decodeSingleOrNull<Entrega>()
            entrega?.let { return it }
        } catch (exception: Exception) {
            Log.e("ERROR", exception.toString())
        }
        throw BadRequestException("Não foi possível deletar entrega")
    }
}

