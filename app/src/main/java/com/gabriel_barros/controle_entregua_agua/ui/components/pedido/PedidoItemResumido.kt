package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MyBox


@Composable
fun PedidoItemResumido(
    cliente: String,
    qtdProdutoPedido: Int,
    qtdProdutoEntregue: Int,
    valorPago: Double,
    valorTotal: Double,
    onClick: () -> Unit,
) {
//    val pagamentoDAO: PagamentoQueryBuilder = koinInject()
//    val itemEntregaDAO: ItemEntregaQueryBuilder = koinInject()
//    val itemPedidoDAO: ItemPedidoQueryBuilder = koinInject()

//    var totalPedido by remember { mutableIntStateOf(0) }
//    var totalEntregue by remember { mutableIntStateOf(0) }
//    var totalPago by remember { mutableDoubleStateOf(0.0) }

    var isEntregue by remember { mutableStateOf(false) }
    var isPago by remember { mutableStateOf(false) }

    /*
    LaunchedEffect(Unit) {
        totalPago = pagamentoDAO
            .getPagamentosByPedido(pedido.id)
            .buildExecuteAsSList()
            .sumOf { it.valor }

        val itensPedidos = itemPedidoDAO.getAllItensByPedidoId(pedido.id).buildExecuteAsSList()

        totalPedido = itensPedidos.sumOf { it.qtd }

        totalEntregue =
            itemEntregaDAO.getAllItensByItemPedidoId(*itensPedidos.map { it.id }.toLongArray())
                .buildExecuteAsSList().sumOf { it.qtdEntregue }
    }
    */


    MyBox(
        modifier = Modifier
            .clickable { onClick() }
            .padding(5.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = cliente)
            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("${qtdProdutoEntregue}/$qtdProdutoPedido")
                Text("R$ $valorPago/${valorTotal}")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.clickable { isEntregue = !isEntregue }) {
                    //TODO bloquear se a entrega está completa
                    Text("Entregue")
                    Checkbox(
                        checked = isEntregue,
                        onCheckedChange = {
                            isEntregue = it
                            //TODO completar entrega
                        },
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isPago = !isPago }) {
                    //TODO bloquear se o pagamento está completo

                    Text("Pago")
                    Checkbox(
                        checked = isPago,
                        onCheckedChange = {
                            isPago = it
                            //TODO completar pagamento
                        },
                    )
                }
            }
        }

    }
}
