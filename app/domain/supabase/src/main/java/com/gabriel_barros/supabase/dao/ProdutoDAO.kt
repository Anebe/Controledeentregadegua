package com.gabriel_barros.supabase.dao

import com.gabriel_barros.domain.domain.entity.Produto
import com.gabriel_barros.domain.domain.error.BadRequestException
import com.gabriel_barros.domain.domain.portout.ProdutoPortOut
import com.gabriel_barros.supabase.SUPABASE_SCHEMA
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

internal class ProdutoDAO(_supabase: SupabaseClient): ProdutoPortOut {
    private val supabase = _supabase
    private val schema = SUPABASE_SCHEMA.TESTE.toString()
    private val TABLE: String = "produtos"


//    override suspend fun getProdutoById(id: Long): Produto? {
//        val produto = supabase.from(schema, TABLE)
//            .select {
//                filter { Produto::id eq id }
//            }
//            .decodeSingleOrNull<Produto>()
//        return produto
//    }

//    override suspend fun getAllProdutos(): List<Produto> {
//        val produto = supabase.from(schema, TABLE)
//            .select()
//            .decodeList<Produto>()
//        return produto
//    }

    override suspend fun saveProduto(pedido: Produto): Produto {
        try {
            val novoProduto = supabase.from(schema, TABLE)
                .upsert(pedido) { select() }
                .decodeSingleOrNull<Produto>()
            novoProduto?.let { return it }
        } catch (exception: Exception) {
        }
        throw BadRequestException("Não foi possível adicionar produto")
    }

    override suspend fun deleteProduto(id: Long): Produto {
        try {
            val produto = supabase.from(schema, TABLE)
                .delete {
                    filter { Produto::id eq id }
                    select()
                }
                .decodeSingleOrNull<Produto>()
            produto?.let { return it }
        } catch (exception: Exception) {
        }
        throw BadRequestException("Não foi possível deletar produto")
    }

}
