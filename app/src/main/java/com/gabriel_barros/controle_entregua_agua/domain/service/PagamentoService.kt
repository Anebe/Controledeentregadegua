package com.gabriel_barros.controle_entregua_agua.domain.service

import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import java.time.LocalDate

class PagamentoService(
    private val pagamentoOut: PagamentoPortOut,
    private val pedidoService: PedidoUseCase,
    private val clienteService: ClienteUseCase,
): PagamentoUseCase {
    override suspend fun getPagamentoById(id: Long): Pagamento? {
        return pagamentoOut.getPagamentoById(id)
    }

    override suspend fun getPagamentosByPedido(pedidoId: Long): List<Pagamento> {
        return pagamentoOut.getPagamentosByPedidoId(pedidoId)
    }

    override suspend fun getAllPagamentos(): List<Pagamento> {
        return pagamentoOut.getAllPagamentos()
    }
    //Quando salvar pagamento. Salva apenas até o limite do total do pedido. O excesso vira Credito em cliente
    override suspend fun savePagamento(clienteId: Long, valor: Double, tipoPagamento: TipoPagamento): List<Pagamento> {
        if(valor <= 0.0) return emptyList()

        var quantoSobrou = valor
        val pedidos = pedidoService.getPedidosPendentes().filter { it.cliente_id == clienteId }
        val novosPagamentos = mutableListOf<Pagamento>()
        val verificaPedidos = mutableListOf<Long>()

        pedidos.forEach {pedido ->
            val pagamentos = pagamentoOut.getPagamentosByPedidoId(pedido.id)
            val somaPagamentos = pagamentos.sumOf { it.valor }
            var quantoDeve = pedido.valor_total - somaPagamentos

            val aindaDeveETemDinheiroSobrando = quantoDeve != 0.0 && quantoSobrou > 0.0
            if(aindaDeveETemDinheiroSobrando){// Pedidos pendentes podem ser pelo pagamento ou entrega. Aqui verifica se é de fato pelo pagamento
                quantoSobrou -= quantoDeve

                var valorPagamento = 0.0
                if(quantoSobrou < 0){
                    valorPagamento = quantoDeve - quantoSobrou
                }
                if(quantoSobrou >= 0){
                    valorPagamento = quantoDeve
                }

                novosPagamentos.add(Pagamento(
                    id = 0,
                    pedido_id = pedido.id,
                    data = LocalDate.now(),
                    valor = valorPagamento,
                    pagamento = tipoPagamento,
                ))
                if(quantoSobrou >= 0.0){
                    verificaPedidos.add(novosPagamentos.lastIndex.toLong())
                }

            }
        }
        val resultPagamentos = mutableListOf<Pagamento>()
        novosPagamentos.forEachIndexed { index, pagamento ->
            pagamentoOut.savePagamento(pagamento)?.let { pagamentoSalvo ->
                resultPagamentos.add(pagamentoSalvo)
                if(verificaPedidos.contains(index.toLong())){
//                    val i = verificaPedidos.indexOf(index.toLong())
//                    verificaPedidos[i] = pagamentoSalvo.id
                    pedidoService.verificaPedidoEFinaliza(pagamentoSalvo.id)
                }
            }
        }
        if(quantoSobrou > 0){
            var cliente = clienteService.getClienteById(clienteId)
            if(cliente != null) {
                cliente = cliente.copy(credito = cliente.credito + quantoSobrou)
                clienteService.saveCliente(cliente)
            }
        }
        return resultPagamentos
    }

//    override suspend fun savePagamento(pagamento: Pagamento): Pagamento? {
//        val pedido = pedidoService.getPedidoById(pagamento.pedido_id) ?: return null
//        pedidoService.getPedidoByClienteId()
//        val resto = pagamento.valor - pagamento.valor_troco +  pedido.valor_total
//
//        if(resto <= 0){
//            //TODO fazer um verificar e fecha Pedido()
//
//            if(resto < 0){
//                var cliente = clienteService.getClienteById(pedido.cliente_id)
//                if(cliente != null) {
//                    cliente = cliente.copy(credito = cliente.credito + resto)
//                    clienteService.saveCliente(cliente)
//                }
//            }
//        }
//        return pagamentoOut.savePagamento(pagamento)
//    }


    override suspend fun deletePagamento(id: Long): Pagamento? {
        return pagamentoOut.deletePagamento(id)
    }
}