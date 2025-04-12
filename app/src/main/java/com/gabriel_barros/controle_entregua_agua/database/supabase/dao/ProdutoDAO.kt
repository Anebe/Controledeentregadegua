package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ProdutoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import io.github.jan.supabase.postgrest.from

class ProdutoDAO : ProdutoPortOut {
    private val supabase = SupabaseClientProvider.supabase
    private val schema = SupabaseClientProvider.schema
    private val TABLE: String = "produtos"


    override suspend fun getProdutoById(id: Long): Produto? {
        val produto = supabase.from(schema, TABLE)
            .select {
                filter { ProdutoSupabase::id eq id }
            }
            .decodeSingleOrNull<ProdutoSupabase>()
        return produto?.let { Mapper.toProduto(it) }
    }

    override suspend fun getAllProdutos(): List<Produto> {
        val produto = supabase.from(schema, TABLE)
            .select()
            .decodeList<ProdutoSupabase>()
        return produto.map { Mapper.toProduto(it) }
    }

    override suspend fun saveProduto(pedido: Produto): Produto? {
        val produto = supabase.from(schema, TABLE)
            .upsert(Mapper.toProdutoSupabase(pedido)) { select() }
            .decodeSingleOrNull<ProdutoSupabase>()
        return produto?.let { Mapper.toProduto(it) }
    }

    override suspend fun deleteProduto(id: Long): Produto? {
        val produto = supabase.from(schema, TABLE)
            .delete {
                filter { ProdutoSupabase::id eq id }
                select()
            }
            .decodeSingleOrNull<ProdutoSupabase>()
        return produto?.let { Mapper.toProduto(it) }
    }

}
