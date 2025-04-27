package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.domain.domain.entity.StatusPedido
import com.gabriel_barros.domain.domain.error.BadRequestException
import com.gabriel_barros.domain.domain.error.NotFoundException
import com.gabriel_barros.domain.domain.portout.PedidoPortOut
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import java.time.LocalDate

/*TODO Criar um redistribuir pedidos
quando o estoque tiver baixo(TODO escolher quantidade)
o usuario vai ser alertado e pode escolher redistribuir
se o fizer o pedidos vão ser dividos em partes menores para aquele produto com estoque baixo
então um pedido que tinha 5 qtd de um produto, agora vai ter dois (TODO escolher como dividir)
um com 2 e outro com 3, e o com 3 será agendado para depois
 */
class PedidoManagerImp(
    private val pedidoOut: PedidoPortOut,
    private val pedidoQuery: PedidoQueryBuilder,
    private val itemPedidoQuery: ItemPedidoQueryBuilder,
    private val produtoService: ProdutoManager,
    private val produtoQuery: ProdutoQueryBuilder,
    private val pagamentoQuery: PagamentoQueryBuilder,
    private val entregaQuery: EntregaQueryBuilder,
    private val itemEntregaQuery: ItemEntregaQueryBuilder,
) : PedidoManager {

    override suspend fun checkAndFinalizePedido(pedidoId: Long) {
        //TODO criar um pagamento.isPagamentoFinalizado e entrega.isEntregaFinalizda
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
        if (isAllProdutosEntregue && isPagamentoFeito) {
            pedido = pedido.copy(status = StatusPedido.FINALIZADO)
            pedidoOut.savePedido(pedido, itensPedidos.toSet())
        }

    }

    private fun calculatePrecoTotalOfPedido(
        produtos: Set<ProdutoEntity>,
        produtoExigidos: Set<PedidoManager.ItemDoPedidoInput>
    ): Double {
        var valorTotal = 0.0
        produtos.forEach { produto ->
            val produtoExigido = produtoExigidos.find { produto.id == it.produtoId }
            produtoExigido?.let {
                valorTotal += produtoExigido.qtd * produto.preco
            }
        }
        return valorTotal
    }

    private fun validaQtdExigidaNoPedidoOrThrow(
        produtos: Set<ProdutoEntity>,
        produtoExigidos: Set<PedidoManager.ItemDoPedidoInput>
    ) {
        produtos.forEach { produto ->
            val produtoExigido = produtoExigidos.find { produto.id == it.produtoId }
            produtoExigido?.let {
                if (produtoExigido.qtd > produto.estoque - produto.reservado) {
                    throw BadRequestException("A quantidade produtos exigidos excede o estoque.")
                }
            }
        }
    }

    private suspend fun reservarProdutosParaPedido(
        produtos: Set<ProdutoEntity>,
        produtoExigidos: Set<PedidoManager.ItemDoPedidoInput>
    ) {
        produtos.forEach { produto ->
            val produtoExigido = produtoExigidos.find { produto.id == it.produtoId }
            produtoExigido?.let {
                val prod = produto.copy(reservado = produto.reservado + produtoExigido.qtd)
                produtoService.registerProduto(prod)
            }
        }
    }

    private fun validarProdutosExistem(produtosIds: List<Long>, produtos: Set<ProdutoEntity>) {
        if (produtosIds.size != produtos.size) {
            throw NotFoundException("Algum(ns) do(s) produto(s) não foi(ram) localizado(s)")
        }
    }

    private fun criarItensPedido(input: PedidoManager.PedidoInput): Set<ItensPedido> =
        input.itensDoPedido.map {
            ItensPedido(produto_id = it.produtoId, qtd = it.qtd)
        }.toSet()

    private fun criarPedido(input: PedidoManager.PedidoInput, valorTotal: Double) = PedidoEntity(
        cliente_id = input.clienteId,
        data = LocalDate.now(),
        data_agendada_para_entrega = input.dataAgendadaParaEntrega,
        status = StatusPedido.PENDENTE,
        valor_total = valorTotal,
    )

    override suspend fun makePedido(pedidoInput: PedidoManager.PedidoInput): PedidoEntity {
        ValidatePedidoInput.validateOrThrow(pedidoInput)

        val produtosIds = pedidoInput.itensDoPedido.map { it.produtoId }
        val produtos = produtoQuery.getProdutoById(produtosIds).buildExecuteAsSList().toSet()

        validarProdutosExistem(produtosIds, produtos)

        //TODO alguns cliente podem receber desconto
        val valorTotal = calculatePrecoTotalOfPedido(produtos, pedidoInput.itensDoPedido)
        //validaQtdExigidaNoPedidoOrThrow(produtos, pedidoInput.itensDoPedido)

        val novoPedido = criarPedido(pedidoInput, valorTotal)
        val itensPedido = criarItensPedido(pedidoInput)

        val pedidoResult = pedidoOut.savePedido(novoPedido, itensPedido)

        //TODO criar um pedidoService.UpdateReservaParaPedido(
        reservarProdutosParaPedido(produtos, pedidoInput.itensDoPedido)
        return pedidoResult
    }

    override suspend fun validateStockForPedido(itens: List<ItensPedido>): Boolean {
        TODO("Not yet implemented")
    }
}