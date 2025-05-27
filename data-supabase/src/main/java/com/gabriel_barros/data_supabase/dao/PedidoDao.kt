package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.Mapper
import com.gabriel_barros.data_supabase.SupabaseClientProvider
import com.gabriel_barros.data_supabase.entity.ItensPedidoSupabase
import com.gabriel_barros.data_supabase.entity.PedidoSupabase
import com.gabriel_barros.domain.entity.ItensPedido
import com.gabriel_barros.domain.entity.Pedido
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain.entity.ProdutoPedido
import com.gabriel_barros.domain.entity.Quantidade
import com.gabriel_barros.domain.error.BadRequestException
import com.gabriel_barros.domain_portout.PedidoPortOut
import io.github.jan.supabase.postgrest.from

internal class PedidoDAO : PedidoPortOut {
    private val schema = SupabaseClientProvider.schema
    private val supabase = SupabaseClientProvider.supabase
    private val TABLE: String = "pedidos"


//    override suspend fun getPedidoById(id: Long): PedidoEntity? {
//        val pedido = supabase.from(schema, TABLE).select() {
//                filter { PedidoEntity::id eq id }
//            }.decodeSingleOrNull<PedidoEntity>()
//        return pedido
//
//    }

//    override suspend fun getPedidoByIdWithCliente(id: Long): PedidoEntity? {
//
//            val pedido = supabase.from(schema, TABLE)
//                .select() {
//                    filter { PedidoEntity::id eq id }
//                }.decodeSingleOrNull<PedidoEntity>()
//
//
//            val cliente = supabase.from(schema, "clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id eq pedido?.cliente_id
//                    }
//                }.decodeSingle<ClienteSupabase>().let{ ClienteSupabase.to(it) }
//
//            return pedido?.to()
//
//
//    }

//    override suspend fun getPedidoByClienteId(clienteId: Long): List<PedidoEntity> {
//        val pedido = supabase.from(schema, TABLE).select() {
//                filter { PedidoEntity::cliente_id eq clienteId }
//            }.decodeList<PedidoEntity>()
//        return pedido
//
//    }


//    override suspend fun getPedidosPendentes(): List<PedidoEntity> {
//        val pedidos = supabase.from(schema, TABLE).select {
//                filter {
//                    PedidoEntity::status eq StatusPedido.PENDENTE
//                }
//            }.decodeList<PedidoEntity>()
//
//        return pedidos
//
//    }

//    override suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido> {
//        val itensPedidos = supabase.from(schema, "itens_pedidos").select {
//                filter {
//                    ItensPedido::pedido_id eq pedidoId
//                }
//            }.decodeList<ItensPedido>()
//        return itensPedidos
//
//    }

//    override suspend fun getAllPedidos(): List<PedidoEntity> {
//        val pedidos = supabase.from(schema, TABLE).select().decodeList<PedidoEntity>()
//        return pedidos
//
//    }

//    override suspend fun getAllPedidosWithClientes(): List<PedidoEntity> {
//
//            val pedidos = supabase.from(schema, TABLE)
//                .select()
//                .decodeList<PedidoEntity>()
//            val id_clientes = pedidos.map { it.cliente_id }
//            val clientes = supabase.from(schema, "clientes")
//                .select(){
//                    filter {
//                        ClienteSupabase::id isIn listOf(id_clientes)
//                    }
//                }.decodeList<ClienteSupabase>().map { ClienteSupabase.to(it) }
//            return pedidos.map {
//                pdido -> pedido.to()
//            }
//
//        }
//    }

    override suspend fun savePedido(pedido: Pedido, itens: Set<ItensPedido>): Pedido {
        try {
            val novoPedido = supabase
                .from(schema, TABLE)
                .upsert(pedido) { select() }
                .decodeSingle<Pedido>()

            val item = itens.map { it.copy(pedido_id = novoPedido.id.id) }
            supabase.from(schema, "itens_pedidos").upsert(item)
            return novoPedido
        } catch (exception: Exception) {
            println(exception.message)
        }
        throw BadRequestException("Não foi possível adicionar pedido")
    }

    private fun convert(pedidoId: Long, produtos: Map<Produto, Quantidade>): List<ItensPedido> {
        val result = mutableListOf<ItensPedido>()
        produtos.forEach { prod, qtd ->
            result.add(
                ItensPedido(
                    qtd = qtd,
                    produto_id = prod.id.id,
                    pedido_id = pedidoId
                )
            )

        }
        return result
    }

    private fun convert(pedidoId: Long, produtos: Set<ProdutoPedido>): List<ItensPedidoSupabase> {
        return produtos.map { ItensPedidoSupabase(
                pedido_id = pedidoId,
                produto_id = it.produtoId.id,
                qtd = it.qtd
            )}
    }

    override suspend fun savePedido(pedido: Pedido): Pedido {
        try {
            val pedidoCadastrado = supabase
                .from(schema, TABLE)
                .upsert(Mapper.toPedidoSupabase(pedido)) { select() }
                .decodeSingle<PedidoSupabase>()

            val itensPedido = convert(pedidoCadastrado.id, pedido.getProdutos())

            supabase.from(schema, "itens_pedidos").upsert(itensPedido)
            return Mapper.toPedido(pedidoCadastrado)
        } catch (exception: Exception) {
            println(exception.message)
        }
        throw BadRequestException("Não foi possível adicionar pedido")
    }

    override suspend fun deletePedido(id: Long): Pedido {
        try {
            val pedido = supabase.from(schema, TABLE).delete {
                filter { Pedido::id eq id }
                select()
            }.decodeSingleOrNull<Pedido>()
            pedido?.let { return it }
        } catch (exception: Exception) {
            println(exception.toString())
        }
        throw BadRequestException("Não foi possível deletar pedido")
    }
}
