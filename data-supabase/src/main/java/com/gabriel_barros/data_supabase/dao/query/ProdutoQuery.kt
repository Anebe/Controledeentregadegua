package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.ProdutoSupabase
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain_portout.query.ProdutoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult

internal class ProdutoQuery: ProdutoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "produtos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getProdutoById(vararg id: Long): ProdutoQueryBuilder {
        getProdutoById(id.toList())
        return this
    }

    override fun getProdutoById(id: List<Long>): ProdutoQueryBuilder {
        query.add { Produto::id isIn id }
        return this
    }

    private suspend fun execute(): PostgrestResult{
        return supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }
    }

    override suspend fun buildExecuteAsSingle(): Produto {
        val result = execute().decodeSingle<ProdutoSupabase>()
        query.clear()
        return Mapper.toProduto(result)
    }

    override suspend fun buildExecuteAsSList(): List<Produto> {
        val result = execute().decodeList<ProdutoSupabase>()
        query.clear()
        return result.map { Mapper.toProduto(it) }
    }
}