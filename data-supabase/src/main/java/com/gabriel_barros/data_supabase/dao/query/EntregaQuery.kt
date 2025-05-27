package com.gabriel_barros.data_supabase.dao.query


import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.EntregaSupabase
import com.gabriel_barros.domain.entity.Entrega
import com.gabriel_barros.domain_portout.query.EntregaQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult

@Deprecated("")
internal class EntregaQuery : EntregaQueryBuilder {

    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "entregas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getEntregaById(vararg entregaId: Long): EntregaQueryBuilder {
        query.add { isIn(EntregaSupabase::id.name, entregaId.toList()) }
        return this
    }

    override fun getEntregaById(entregaIds: List<Long>): EntregaQueryBuilder {
        query.add { isIn(EntregaSupabase::id.name, entregaIds) }
        return this
    }

    override fun getAllEntregas(): EntregaQueryBuilder {
        return this
    }

    override fun getAllEntregasByFrete(freteId: Long): EntregaQueryBuilder {
        query.add { eq(EntregaSupabase::frete_id.name, freteId) }
        return this
    }

    private suspend fun build(): PostgrestResult {
        return supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }
    }
    override suspend fun buildExecuteAsSingle(): Entrega {
        val result = build().decodeSingle<EntregaSupabase>()

        query.clear()
        return Mapper.toEntrega(result)
    }

    override suspend fun buildExecuteAsSList(): List<Entrega> {
        val entregasSupabase = build().decodeList<EntregaSupabase>()
        query.clear()
        val result = entregasSupabase.map { Mapper.toEntrega(it) }
        return result
    }
}

