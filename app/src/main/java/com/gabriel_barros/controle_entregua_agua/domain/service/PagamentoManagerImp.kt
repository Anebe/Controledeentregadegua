package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoManager
import java.time.LocalDate

class PagamentoManagerImp(
    private val pagamentoOut: PagamentoPortOut,
    private val pagamentoQuery: PagamentoQueryBuilder,
    private val pedidoService: PedidoManager,
    private val pedidoQuery: PedidoQueryBuilder,
    private val clienteService: ClienteManager,
    private val clienteQuery: ClienteFilterBuilder,
) : PagamentoManager {

    /*Tenta fazer o seguinte:
    *   Pagar dívidas
    *   Incrementar crédito do cliente*/
    override suspend fun processPagamento(
        clienteId: Long,
        pagamento: PagamentoManager.PagamentoDTO
    ): List<Pagamento> {

        if (pagamento.valor <= 0.0) return emptyList()

        var quantoSobrou = pagamento.valor
        val pedidos = pedidoQuery
            .getPedidoByStatus(StatusPedido.PENDENTE)
            .getPedidoByClienteId(clienteId)
            .buildExecuteAsSList()
        val novosPagamentos = mutableListOf<Pagamento>()
        val verificaPedidos = mutableListOf<Long>()

        pedidos.forEach { pedido ->
            val pagamentos = pagamentoQuery.getPagamentosByPedido(pedido.id).buildExecuteAsSList()
            val somaPagamentos = pagamentos.sumOf { it.valor }
            var quantoDeve = pedido.valor_total - somaPagamentos

            val aindaDeveETemDinheiroSobrando = quantoDeve != 0.0 && quantoSobrou > 0.0
            if (aindaDeveETemDinheiroSobrando) {// Pedidos pendentes podem ser pelo pagamento ou entrega. Aqui verifica se é de fato pelo pagamento
                quantoSobrou -= quantoDeve

                var valorPagamento = 0.0
                if (quantoSobrou < 0) {
                    valorPagamento = quantoDeve - quantoSobrou
                }
                if (quantoSobrou >= 0) {
                    valorPagamento = quantoDeve
                }

                novosPagamentos.add(
                    Pagamento(
                        id = 0,
                        pedido_id = pedido.id,
                        data = LocalDate.now(),
                        valor = valorPagamento,
                        pagamento = pagamento.tipoPagamento,
                    )
                )
                if (quantoSobrou >= 0.0) {
                    verificaPedidos.add(novosPagamentos.lastIndex.toLong())
                }

            }
        }
        val resultPagamentos = mutableListOf<Pagamento>()
        novosPagamentos.forEachIndexed { index, pagamento ->
            pagamentoOut.savePagamento(pagamento).let { pagamentoSalvo ->
                resultPagamentos.add(pagamentoSalvo)
                if (verificaPedidos.contains(index.toLong())) {
                    //                    val i = verificaPedidos.indexOf(index.toLong())
                    //                    verificaPedidos[i] = pagamentoSalvo.id
                    pedidoService.checkAndFinalizePedido(pagamentoSalvo.id)
                }
            }
        }
        if (quantoSobrou > 0) {
            var cliente = clienteQuery.getClienteById(clienteId).buildExecuteAsSingle()
            cliente = cliente.copy(credito = cliente.credito + quantoSobrou)
            clienteService.registerCliente(cliente)
        }
        return resultPagamentos
    }

    override suspend fun payPedidoRemainder(pedidoId: Long) {
        val pagamentosFeitos = pagamentoQuery.getPagamentosByPedido(pedidoId).buildExecuteAsSList()
        val pedido = pedidoQuery.getPedidoById(pedidoId).buildExecuteAsSingle()

        var valorParaPagar = 0.0
        val totalPagamentosFeitos = pagamentosFeitos.sumOf { it.valor }
        valorParaPagar = pedido.valor_total - totalPagamentosFeitos

        pagamentoOut.savePagamento(
            Pagamento(
                id = 0,
                pedido_id = pedidoId,
                data = LocalDate.now(),
                pagamento = TipoPagamento.DINHEIRO,
                valor = valorParaPagar
            )
        )    }

    override suspend fun increaseCredit(
        clienteId: Long,
        pagamento: PagamentoManager.PagamentoDTO
    ) {
        TODO("")
    }


    override suspend fun payPedido(
        pedidoId: Long,
        pagamento: PagamentoManager.PagamentoDTO
    ) {

    }

    override suspend fun payDebts(
        clienteId: Long,
        pagamento: PagamentoManager.PagamentoDTO
    ) {
        TODO("Not yet implemented")
    }
}