package com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ProdutoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder

class ProdutoQuery: ProdutoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "produtos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getProdutoById(id: Long): ProdutoQueryBuilder {
        query.add { Produto::id eq id }
        return this
    }

    override fun getAllProdutos(): ProdutoQueryBuilder {
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