package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.Mapper
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.ProdutoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.runBlocking

class ProdutoDAO(
private val supabase: SupabaseClient,
private val TABLE: String = "produtos"): ProdutoPortOut
{
    override fun getProdutoById(id: Long): Produto? {
        return runBlocking{
            val produto = supabase.from(TABLE)
                .select(columns = Columns.list("*")) {
                    filter { ProdutoSupabase::id eq id }
                }
                .decodeSingleOrNull<ProdutoSupabase>()
            return@runBlocking produto?.let{ Mapper.toProduto(it) }
        }
    }

    override fun getAllProdutos(): List<Produto> {
        return runBlocking{
            val produto = supabase.from(TABLE)
                .select(columns = Columns.list("*"))
                .decodeList<ProdutoSupabase>()
            return@runBlocking produto.map { Mapper.toProduto(it) }
        }
    }

    override fun saveProduto(pedido: Produto): Produto? {
        return runBlocking{
            val produto = supabase.from(TABLE)
                .upsert(Mapper.toProdutoSupabase(pedido)) { select() }
                .decodeSingleOrNull<ProdutoSupabase>()
            return@runBlocking produto?.let{ Mapper.toProduto(it) }
        }
    }

    override fun deleteProduto(id: Long): Produto? {
        return runBlocking{
            val produto = supabase.from(TABLE)
                .delete {
                    filter { ProdutoSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<ProdutoSupabase>()
            return@runBlocking produto?.let{ Mapper.toProduto(it) }
        }
    }

}
