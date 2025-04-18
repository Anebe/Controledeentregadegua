package com.gabriel_barros.controle_entregua_agua.database.supabase.dao

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import com.gabriel_barros.controle_entregua_agua.domain.usecase.query.ItemPedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.usecase.query.PedidoQueryBuilder
import io.github.jan.supabase.auth.PostgrestFilterDSL
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.filter.PostgrestFilterBuilder


class ItemPedidoQueryImp: ItemPedidoQueryBuilder{
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "itens_pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getAllItensByPedidoId(pedidoId: Long): ItemPedidoQueryBuilder {
        query.add { ItensPedido::pedido_id eq pedidoId }
        return this
    }

    override suspend fun buildExecuteAsSList(): List<ItensPedido> {
        return supabase.from(schema, TABLE).select {
            filter {
                query.forEach { it() }
            }
        }.decodeList<ItensPedido>()
    }
}

class PedidoQueryImp: PedidoQueryBuilder {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"
    private val query = mutableListOf<@PostgrestFilterDSL PostgrestFilterBuilder.() -> Unit>()

    override fun getPedidoById(id: Long): PedidoQueryImp {
        query.add { eq(Pedido::id.name, id) }
        return this
    }
    override fun getPedidoByClienteId(clienteId: Long): PedidoQueryImp{
        query.add { eq(Pedido::cliente_id.name, clienteId) }
        return this
    }

    override fun getPedidoByStatus(statusPedido: StatusPedido): PedidoQueryImp{
        query.add { eq(Pedido::status.name, statusPedido) }
        return this
    }

    override fun getAllPedidos(): PedidoQueryBuilder {
        return this
    }

    override suspend fun buildExecuteAsSingle(): Pedido{
        return supabase.from(schema, TABLE).select {
            filter {
                query.forEach { it() }
            }
        }.decodeSingle<Pedido>()
    }
    override suspend fun buildExecuteAsSList(): List<Pedido>{
        return supabase.from(schema, TABLE).select {
            filter {
                query.forEach { it() }
            }
        }.decodeList<Pedido>()
    }
}