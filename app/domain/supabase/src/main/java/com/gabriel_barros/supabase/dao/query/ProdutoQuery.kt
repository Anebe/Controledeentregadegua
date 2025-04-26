package com.gabriel_barros.supabase.dao.query

import com.gabriel_barros.domain.domain.entity.Produto
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

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


    override suspend fun buildExecuteAsSingle(): Produto {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Produto>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Produto> {
        val result = supabase.from(schema, TABLE).select {
            if(query.isNotEmpty()){
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Produto>()
        query.clear()
        return result
    }
}