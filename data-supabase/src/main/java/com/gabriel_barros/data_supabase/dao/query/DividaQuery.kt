package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.BigDecimalSerializer
import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.domain.entity.Divida
import com.gabriel_barros.domain_portout.query.DividaQueryBuilder
import com.gabriel_barros.domain_portout.query.PagamentoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class DividaSupabase(
    val id: Long,
    val pedido_id: Long,
    @Serializable(with = BigDecimalSerializer::class)
    val valor_total: BigDecimal,
)

class DividaQuery(private val pagamentoQuery: PagamentoQueryBuilder): DividaQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "dividas"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun byId(id: Long): DividaQueryBuilder {
        query.add { eq(DividaSupabase::id.name, id) }
        return this
    }

    override fun byPedidoId(pedidoId: Long): DividaQueryBuilder {
        query.add { eq(DividaSupabase::pedido_id.name, pedidoId) }
        return this
    }

    override suspend fun getSigle(): Divida {
        val result = get().decodeSingle<DividaSupabase>()
        return Mapper.toDivida(result)
    }

    private suspend fun get(): PostgrestResult {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }
        query.clear()
        return result
    }
}