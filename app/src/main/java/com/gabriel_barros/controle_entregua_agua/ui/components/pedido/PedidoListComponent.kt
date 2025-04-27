package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.PagamentoManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun PedidoListComponent(
    pedidos: List<Pair<PedidoEntity, Cliente>>,
    onItemClick: (PedidoEntity, Cliente) -> Unit
) {
    val scope = rememberCoroutineScope()

    val pagamentoDAO: PagamentoManager = koinInject()
    val entregaDAO: EntregaManager = koinInject()

    val pagamentos = remember { mutableListOf<Long>() }
    val entregas = remember { mutableListOf<Long>() }

    Column {
        Button(onClick = {
            scope.launch {
                pagamentos.forEach { pedidoId ->
                    pagamentoDAO.payPedidoRemainder(pedidoId)
                }
                Log.i("Info", "Ids ${ entregas.toString() }")
                entregas.forEach { pedidoId ->
                    entregaDAO.registerCompleteEntrega(pedidoId)
                }

            }
        }) {
            Text("Confirmar entregas/pagamentos")
        }
        LazyColumn {
            items(pedidos.size) { index ->
                PedidoItemResumido(
                    pedido = pedidos[index].first,
                    cliente = pedidos[index].second,
                    onClick = {
                        onItemClick(pedidos[index].first, pedidos[index].second)
                    },
                    onEntregaCheckChange = { isForAdd, pedidoId ->
                        if (isForAdd) entregas.add(pedidoId)
                        else entregas.remove(pedidoId)
                    },
                    onPagamentoCheckChange = { isForAdd, pedidoId ->
                        if (isForAdd) pagamentos.add(pedidoId)
                        else pagamentos.remove(pedidoId)
                    }
                )
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun previewPedidoList() {
    ControleDeEntregaDeAguaTheme {
        PedidoListComponent(emptyList()) { pedido, cliente ->

        }

    }
}