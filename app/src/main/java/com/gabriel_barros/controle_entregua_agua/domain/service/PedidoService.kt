package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import org.koin.compose.koinInject
import java.util.concurrent.Flow

class PedidoService(
    private val pedidoOut: PedidoPortOut,
    private val produtoService: ProdutoUseCase,
    private val pagamentoService: Lazy<PagamentoUseCase>,
    private val entregaService: EntregaUseCase
) :PedidoUseCase{

    override suspend fun getPedidoById(id: Long): Pedido? {
        return pedidoOut.getPedidoById(id)
    }


    override suspend fun getPedidoByClienteId(clienteId: Long): List<Pedido> {
        return pedidoOut.getPedidoByClienteId(clienteId)
    }

    override suspend fun getPedidosPendentes(): List<Pedido> {
        return pedidoOut.getPedidosPendentes()
    }

    override suspend fun verificaPedidoEFinaliza(id: Long) {
        var pedido = this.getPedidoById(id) ?: return

        val pagamentos = pagamentoService.value.getPagamentosByPedido(id)
        val entregas = entregaService.getAllEntregasByPedido(id)
        //TODO
        val somaPagamentos = pagamentos.sumOf { it.valor }
        val itensPedidos = this.getAllItensByPedidoId(id)
        val isPagamentoFeito = somaPagamentos == pedido.valor_total
        var isAllProdutosEntregue = true
        itensPedidos.forEachIndexed { index, itensPedido ->
            var qtdItem = itensPedido.qtd
            entregas.forEachIndexed { indexEntrega, entrega ->
                entregaService.getAllItensByEntregaId(entrega.id).forEach { itemEntrega ->
                    if (itensPedido.id == itemEntrega.itemPedido_id){
                        qtdItem -= itemEntrega.qtdEntregue
                    }
                }
            }
            isAllProdutosEntregue = isAllProdutosEntregue && qtdItem == 0
        }
        if(isAllProdutosEntregue && isPagamentoFeito){
            pedido = pedido.copy(status = StatusPedido.FINALIZADO)
            this.savePedido(pedido, itensPedidos.toSet())
        }

    }

    override suspend fun getAllPedidos(): List<Pedido> {
        return pedidoOut.getAllPedidos()
    }

    override suspend fun getAllItensByPedidoId(pedidoId: Long): List<ItensPedido> {
        return pedidoOut.getAllItensByPedidoId(pedidoId)
    }

    override suspend fun savePedido(pedido: Pedido, itensPedido: Set<ItensPedido>): Pedido? {
        if (itensPedido.isEmpty() || pedido.troco < 0){
            return null
        }
        val produtos = itensPedido.mapNotNull { produtoService.getProdutoById(it.produto_id)}.associateBy { it.id }
            .toMutableMap()
        val novoPedido = pedido.copy(valor_total = produtos.values.sumOf { it.preco })

        itensPedido.forEach{item ->
            produtos[item.id]?.let{produto ->
                val prod = produto.copy(reservado = produto.reservado + item.qtd)
                produtoService.saveProduto(prod)
            }
        }
        val pedidoResult = pedidoOut.savePedido(novoPedido, itensPedido)
        return pedidoResult
    }


    override suspend fun deletePedido(id: Long): Pedido? {
        return pedidoOut.deletePedido(id)
    }
}