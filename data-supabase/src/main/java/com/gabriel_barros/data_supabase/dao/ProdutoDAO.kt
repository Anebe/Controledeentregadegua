package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.ProdutoSupabase
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain.error.BadRequestException
import com.gabriel_barros.domain_portout.ProdutoPortOut
import io.github.jan.supabase.postgrest.from

internal class ProdutoDAO: ProdutoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
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

    override suspend fun saveProduto(produto: Produto): Produto {
        try {
            val novoProduto = supabase.from(schema, TABLE)
                .upsert(Mapper.toProdutoSupabase(produto)) { select() }
                .decodeSingleOrNull<ProdutoSupabase>()
            novoProduto?.let { return Mapper.toProduto(it) }
        } catch (exception: Exception) {
            throw  exception
            //TODO
        }
        throw BadRequestException("Não foi possível adicionar produto")
    }

    override suspend fun deleteProduto(id: Long): Produto {
        try {
            val produto = supabase.from(schema, TABLE)
                .delete {
                    filter { ProdutoSupabase::id eq id }
                    select()
                }
                .decodeSingleOrNull<ProdutoSupabase>()
            produto?.let { return Mapper.toProduto(it) }
        } catch (exception: Exception) {
        }
        throw BadRequestException("Não foi possível deletar produto")
    }

}
