package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.entity.Id
import com.gabriel_barros.domain.entity.Pedido
import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.domain.entity.ProdutoPedido
import com.gabriel_barros.domain_portout.PedidoPortOut
import com.gabriel_barros.domain_portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain_portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain_portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain_portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain_portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain_portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain_portout.query.ProdutoQueryBuilder
import com.gabriel_barros.usecase.dto.ClienteOutput
import com.gabriel_barros.usecase.dto.PedidoInput
import com.gabriel_barros.usecase.dto.PedidoOutput
import com.gabriel_barros.usecase.dto.ProdutoPedidoOutPut
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.gabriel_barros.usecase.usecase.PedidoSearch
import com.gabriel_barros.usecase.usecase.ProdutoManager
import java.time.LocalDate

/*TODO Criar um redistribuir pedidos
quando o estoque tiver baixo(TODO escolher quantidade)
o usuario vai ser alertado e pode escolher redistribuir
se o fizer o pedidos vão ser dividos em partes menores para aquele produto com estoque baixo
então um pedido que tinha 5 qtd de um produto, agora vai ter dois (TODO escolher como dividir)
um com 2 e outro com 3, e o com 3 será agendado para depois
 */
internal class PedidoManagerImp(
    private val pedidoOut: PedidoPortOut,
    private val pedidoQuery: PedidoQueryBuilder,
    private val itemPedidoQuery: ItemPedidoQueryBuilder,
    private val produtoManager: ProdutoManager,
    private val produtoQuery: ProdutoQueryBuilder,
    private val pagamentoQuery: PagamentoQueryBuilder,
    private val entregaQuery: EntregaQueryBuilder,
    private val itemEntregaQuery: ItemEntregaQueryBuilder,
    private val clienteQuery: ClienteFilterBuilder
) : PedidoManager, PedidoSearch {

    override suspend fun checkAndFinalizePedido(pedidoId: Long) {
        //TODO criar um pagamento.isPagamentoFinalizado e entrega.isEntregaFinalizda
        //TODO()
//        var pedido = pedidoQuery.getPedidoById(pedidoId).buildExecuteAsSingle()
//
//        val pagamentos = pagamentoQuery.getPagamentosByPedido(pedidoId).buildExecuteAsSList()
//        val entregas = entregaQuery.getAllEntregasByPedido(pedidoId).buildExecuteAsSList()
//        val somaPagamentos = pagamentos.sumOf { it.valor }
//        val itensPedidos = itemPedidoQuery.getAllItensByPedidoId(pedidoId).buildExecuteAsSList()
//        val isPagamentoFeito = somaPagamentos == pedido.valor_total
//        var isAllProdutosEntregue = true
//        itensPedidos.forEachIndexed { index, itemPedido ->
//            var qtdItem = itemPedido.qtd
//            entregas.forEachIndexed { indexEntrega, entrega ->
//                itemEntregaQuery.getAllItensByEntregaId(entrega.id)
//                    .getAllItensByItemPedidoId(itemPedido.id).buildExecuteAsSList()
//                    .forEach { itemEntrega ->
//                        qtdItem -= itemEntrega.qtdEntregue
//                    }
//            }
//            isAllProdutosEntregue = isAllProdutosEntregue && qtdItem == 0
//        }
//        if (isAllProdutosEntregue && isPagamentoFeito) {
//            pedido = pedido.copy(status = StatusPedido.FINALIZADO)
//            pedidoOut.savePedido(pedido, itensPedidos.toSet())
//        }

    }

    override suspend fun delete(id: Long) {
        pedidoOut.deletePedido(id)
    }


    override suspend fun makePedido(pedidoInput: PedidoInput): PedidoOutput {

        val produtosIds = pedidoInput.produtos.map { it.produtoId }
        val produtos = produtoQuery.getProdutoById(produtosIds).buildExecuteAsSList().toSet()

        val novoPedido = Pedido(
            cliente_id = Id(pedidoInput.clienteId),
            dataPedido = LocalDate.now(),
        )

        produtos.forEach { produto ->
            val input = pedidoInput.produtos.find { produto.id.id == it.produtoId }
            input?.let{
                produto.reservarProduto(input.qtd)
                novoPedido.addProduto(produto, input.qtd)
            }
        }
        //TODO Criar frete
        //TODO Criar Divida
        //TODO alguns cliente podem receber desconto

        val cliente = clienteQuery.getClienteById(pedidoInput.clienteId).buildExecuteAsSingle()
        val pedidoResult = pedidoOut.savePedido(novoPedido)

        val produtosOut = mutableSetOf<ProdutoPedidoOutPut>()

        produtos.forEach { produto ->
            produtoManager.updateProduto(produto)
            produtosOut.add(ProdutoPedidoOutPut(
                produtoId = produto.id.id,
                qtd = pedidoResult.getProdutos().find { it.produtoId == produto.id }?.qtd ?: 0,
                pedidoId = pedidoResult.id.id,
                nome = produto.nome
            ))
        }


        return PedidoOutput(
            id = pedidoResult.id.id,
            data = pedidoResult.dataPedido,
            status = pedidoResult.status,
            cliente = ClienteOutput(cliente),
            produtos = produtosOut
        )
    }

    override suspend fun getByClienteId(clienteId: Long): PedidoOutput {
        val pedido = pedidoQuery.getPedidoByClienteId(clienteId).buildExecuteAsSingle()
        return getPedido(pedido)
    }

    private fun criarProdutoPedidoOutPut(
        pedidos: Set<ProdutoPedido>,
        produtos: List<Produto>
    ): Set<ProdutoPedidoOutPut> {
        val produtosMap = produtos.associateBy { it.id }

        return pedidos.mapNotNull { pedido ->
            val produto = produtosMap[pedido.produtoId]
            produto?.let {
                ProdutoPedidoOutPut(
                    pedidoId = pedido.pedidoId.id,
                    produtoId = pedido.produtoId.id,
                    nome = it.nome,
                    qtd = pedido.qtd
                )
            }
        }.toSet()
    }
    override suspend fun getPedidoById(pedidoId: Long): PedidoOutput {
        val pedido = pedidoQuery.getPedidoById(pedidoId).buildExecuteAsSingle()
        return getPedido(pedido)
    }

    private suspend fun getPedido(pedido: Pedido): PedidoOutput {
        val cliente = clienteQuery.getClienteById(pedido.cliente_id.id).buildExecuteAsSingle()
        val produtos = produtoQuery.getProdutoById(pedido.getProdutos().map { it.produtoId.id } ).buildExecuteAsSList()
        val pedidoOut = PedidoOutput(pedido, cliente, criarProdutoPedidoOutPut(pedido.getProdutos(), produtos))
        return pedidoOut
    }


}

