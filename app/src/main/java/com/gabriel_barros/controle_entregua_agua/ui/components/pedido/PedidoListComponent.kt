package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.gabriel_barros.usecase.dto.PedidoCompletoOutput
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.PagamentoManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable fun PedidoListComponent(onItemClick: (PedidoCompletoOutput) -> Unit){

}
@Composable
fun PedidoListComponent(
    pedidos: List<PedidoCompletoOutput>,
    onItemClick: (PedidoCompletoOutput) -> Unit
) {
    val scope = rememberCoroutineScope()

    val pagamentoManager: PagamentoManager = koinInject()
    val entregaDAO: EntregaManager = koinInject()

    val pagamentos = remember { mutableListOf<Long>() }
    val entregas = remember { mutableListOf<Long>() }

    Column {
        Button(onClick = {
            scope.launch {
                pagamentos.forEach { pedidoId ->
                    pagamentoManager.payPedidoRemainder(pedidoId)
                }
                Log.i("Info", "Ids ${entregas.toString()}")
                entregas.forEach { pedidoId ->
                    entregaDAO.registerEntrega(pedidoId)
                }

            }
        }) {
            Text("Confirmar entregas/pagamentos")
        }
        LazyColumn {
            items(pedidos.size) { index ->
//                PedidoItemResumido(
//                    pedido = pedidos[index].first,
//                    cliente = pedidos[index].second,
//                    onClick = {
//                        onItemClick(pedidos[index].first, pedidos[index].second)
//                    },
//                    onEntregaCheckChange = { isForAdd, pedidoId ->
//                        if (isForAdd) entregas.add(pedidoId)
//                        else entregas.remove(pedidoId)
//                    },
//                    onPagamentoCheckChange = { isForAdd, pedidoId ->
//                        if (isForAdd) pagamentos.add(pedidoId)
//                        else pagamentos.remove(pedidoId)
//                    },
//                    valorTotal = TODO()
//                )
                val pedicoCompleto = pedidos.get(index)
                val pedido = pedicoCompleto.pedido
                val frete = pedicoCompleto.frete
                val divida = pedicoCompleto.divida

                PedidoItemResumido(
                    cliente = pedido.cliente.nome,
                    qtdProdutoPedido = pedido.produtos.sumOf { it.qtd },
                    qtdProdutoEntregue = frete.produtos.sumOf { it.qtdEntregue },
                    valorPago = divida.pagamentos.sumOf { it.valor }.toDouble(),
                    valorTotal = divida.valor.toDouble(),
                    onClick = { onItemClick(pedicoCompleto) }
                )
            }
        }

    }

}


