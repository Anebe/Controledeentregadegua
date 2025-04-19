package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import com.gabriel_barros.controle_entregua_agua.domain.error.BadRequestException
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoManager
import java.time.LocalDate

class PedidoManagerImp(
    private val pedidoOut: PedidoPortOut,
    private val pedidoQuery: PedidoQueryBuilder,
    private val itemPedidoQuery: ItemPedidoQueryBuilder,
    private val produtoService: ProdutoManager,
    private val produtoQuery: ProdutoQueryBuilder,
    private val pagamentoQuery: PagamentoQueryBuilder,
    private val entregaQuery: EntregaQueryBuilder,
    private val itemEntregaQuery: ItemEntregaQueryBuilder,
) : PedidoManager{


    override suspend fun checkAndFinalizePedido(pedidoId: Long) {
        var pedido = pedidoQuery.getPedidoById(pedidoId).buildExecuteAsSingle()

        val pagamentos = pagamentoQuery.getPagamentosByPedido(pedidoId).buildExecuteAsSList()
        val entregas = entregaQuery.getAllEntregasByPedido(pedidoId).buildExecuteAsSList()
        val somaPagamentos = pagamentos.sumOf { it.valor }
        val itensPedidos = itemPedidoQuery.getAllItensByPedidoId(pedidoId).buildExecuteAsSList()
        val isPagamentoFeito = somaPagamentos == pedido.valor_total
        var isAllProdutosEntregue = true
        itensPedidos.forEachIndexed { index, itemPedido ->
            var qtdItem = itemPedido.qtd
            entregas.forEachIndexed { indexEntrega, entrega ->
                itemEntregaQuery
                    .getAllItensByEntregaId(entrega.id)
                    .getAllItensByItemPedidoId(itemPedido.id)
                    .buildExecuteAsSList()
                    .forEach { itemEntrega ->
                        qtdItem -= itemEntrega.qtdEntregue
                }
            }
            isAllProdutosEntregue = isAllProdutosEntregue && qtdItem == 0
        }
        if(isAllProdutosEntregue && isPagamentoFeito){
            pedido = pedido.copy(status = StatusPedido.FINALIZADO)
            pedidoOut.savePedido(pedido, itensPedidos.toSet())
        }

    }
    override suspend fun makePedido(pedido: PedidoManager.PedidoDTO): Pedido {
        if (pedido.itensDoPedido.isEmpty()){
            throw BadRequestException("Não foi possível adicionar pedido")
        }
        val produtos = pedido.itensDoPedido.map {
            produtoQuery.getProdutoById(it.produtoId).buildExecuteAsSingle()
        }.associateBy { it.id }.toMutableMap()
        val novoPedido = Pedido(
            data = LocalDate.now(),
            cliente_id = pedido.clienteId,
            valor_total = produtos.values.sumOf { it.preco },
            status = StatusPedido.PENDENTE,
            data_entrega = LocalDate.now(),
            id = 0,
            troco = 0.0
        )

        val itensPedido = mutableListOf<ItensPedido>()
        pedido.itensDoPedido.forEach{item ->
            itensPedido.add(ItensPedido(0,0, item.produtoId, item.qtd))

            produtos[item.produtoId]?.let{produto ->
                val prod = produto.copy(reservado = produto.reservado + item.qtd)
                produtoService.registerProduto(prod)
            }
        }
        val pedidoResult = pedidoOut.savePedido(novoPedido, itensPedido.toSet())
        return pedidoResult
    }


    override suspend fun validateStockForPedido(itens: List<ItensPedido>): Boolean {
        TODO("Not yet implemented")
    }
}