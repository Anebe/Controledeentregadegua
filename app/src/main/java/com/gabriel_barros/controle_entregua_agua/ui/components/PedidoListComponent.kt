package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme


@Composable
fun PedidoListComponent(pedidos: List<Pair<Pedido, Cliente>>,
                        onItemClick: (Pedido, Cliente) -> Unit){

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Nome")
            Text(text = "Quantidade")
            Text(text = "Troco")
            //Text(text = "Data")
            //Text(text = "Editar")
            //Text(text = "Concluir")
        }
        Column {
            LazyColumn {
                items(pedidos.size){
                    PedidoItemResumido(
                        pedido = pedidos[it].first,
                        cliente = pedidos[it].second)
                    {
                        onItemClick(pedidos[it].first, pedidos[it].second)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun previewPedidoList(){
    ControleDeEntregaDeAguaTheme {
        PedidoListComponent(emptyList()){ pedido, cliente ->

        }

    }
}