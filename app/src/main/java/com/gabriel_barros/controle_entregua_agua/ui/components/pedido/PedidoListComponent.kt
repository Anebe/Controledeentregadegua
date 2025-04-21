package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme


@Composable
fun PedidoListComponent(
    pedidos: List<Pair<Pedido, Cliente>>,
    onItemClick: (Pedido, Cliente) -> Unit
) {


    Column {
        LazyColumn {
            items(pedidos.size) {
                PedidoItemResumido(
                    pedido = pedidos[it].first,
                    cliente = pedidos[it].second,
                )
                {
                    onItemClick(pedidos[it].first, pedidos[it].second)
                }
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