package com.gabriel_barros.supabase.dao.query

import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.Pedido
import com.gabriel_barros.domain.domain.entity.StatusPedido
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder


internal class ItemPedidoQuery : ItemPedidoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "itens_pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getAllItensByPedidoId(pedidoId: Long): ItemPedidoQueryBuilder {
        query.add { (ItensPedido::pedido_id eq pedidoId)}
        return this
    }

    override suspend fun buildExecuteAsSList(): List<ItensPedido> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<ItensPedido>()
        query.clear()
        return result
    }
}

internal class PedidoQuery : PedidoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPedidoById(vararg pedidoId: Long): PedidoQuery {
        query.add { isIn(Pedido::id.name, pedidoId.toList()) }
        return this
    }

    override fun getPedidoByClienteId(vararg clienteId: Long): PedidoQuery {
        query.add { isIn(Pedido::cliente_id.name, clienteId.toList()) }
        return this
    }

    override fun getPedidoByStatus(statusPedido: StatusPedido): PedidoQuery {
        query.add { eq(Pedido::status.name, statusPedido) }
        return this
    }

    override fun getAllPedidos(): PedidoQueryBuilder {
        return this
    }

    override suspend fun buildExecuteAsSingle(): Pedido {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeSingle<Pedido>()
        query.clear()
        return result
    }

    override suspend fun buildExecuteAsSList(): List<Pedido> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<Pedido>()
        query.clear()
        return result
    }
}