package com.gabriel_barros.data_supabase.dao.query

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.ItensPedidoSupabase
import com.gabriel_barros.data_supabase.entity.PedidoSupabase
import com.gabriel_barros.domain.entity.Pedido
import com.gabriel_barros.domain.entity.ProdutoPedido
import com.gabriel_barros.domain.entity.StatusPedido
import com.gabriel_barros.domain.error.NotFoundException
import com.gabriel_barros.domain_portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain_portout.query.PedidoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder
import io.github.jan.supabase.postgrest.result.PostgrestResult


internal class ItemPedidoQuery : ItemPedidoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "itens_pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getAllItensByPedidoId(pedidoId: Long): ItemPedidoQueryBuilder {
        query.add { (ItensPedidoSupabase::pedido_id eq pedidoId)}
        return this
    }

    override suspend fun buildExecuteAsSList(): List<ProdutoPedido> {
        val result = supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }.decodeList<ItensPedidoSupabase>()
        query.clear()
        return result.map { Mapper.toProdutoPedido(it) }
    }
}

internal class PedidoQuery : PedidoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPedidoById(vararg pedidoId: Long): PedidoQuery {
        query.add { isIn(PedidoSupabase::id.name, pedidoId.toList()) }
        return this
    }

    override fun getPedidoByClienteId(vararg clienteId: Long): PedidoQuery {
        query.add { isIn(PedidoSupabase::cliente_id.name, clienteId.toList()) }
        return this
    }

    override fun getPedidoByStatus(statusPedido: StatusPedido): PedidoQuery {
        query.add { eq(PedidoSupabase::status.name, statusPedido) }
        return this
    }

    override fun getAllPedidos(): PedidoQueryBuilder {
        return this
    }

    private suspend fun execute(): PostgrestResult {
        return supabase.from(schema, TABLE).select {
            if (query.isNotEmpty()) {
                filter {
                    query.forEach { it() }
                }
            }
        }
    }
    override suspend fun buildExecuteAsSingle(): Pedido {
        val result = execute().decodeSingleOrNull<PedidoSupabase>()
        if (result == null) throw  NotFoundException("Pedido não encontrado")
        query.clear()

        val pedido = getProdutoPedido(result)

        return pedido
    }

    private suspend fun getProdutoPedido(pedido: PedidoSupabase): Pedido{
        val pedido = Mapper.toPedido(pedido)
        val produtoPedido = (ItemPedidoQuery().getAllItensByPedidoId(pedido.id.id).buildExecuteAsSList())
        pedido.addProduto(produtoPedido)
        return pedido
    }

    override suspend fun buildExecuteAsSList(): List<Pedido> {
        var result = execute().decodeList<PedidoSupabase>()
        query.clear()

        val pedidos = result.map {
            getProdutoPedido(it)
        }

        return pedidos
    }
}

