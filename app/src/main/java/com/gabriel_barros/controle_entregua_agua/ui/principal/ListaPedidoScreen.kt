package com.gabriel_barros.controle_entregua_agua.ui.principal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme
import kotlin.random.Random

@Composable
fun ListaPedidosScreen(pedidos: List<Pedido>) {
    var pedidos by remember { mutableStateOf(listOf<Pedido>()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = {
            pedidos = pedidos + Pedido(id = pedidos.size + 1, clienteId = Random.nextInt(1, 100))
        }) {
            Text("Adicionar Pedido")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pedidos, key = { it.id }) { pedido ->
                PedidoItem(pedido)
            }
        }
    }
}

@Composable
fun PedidoItem(pedido: Pedido) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Pedido #${pedido.id}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Cliente ID: ${pedido.clienteId}")
            Text(text = "Quantidade de água: ${pedido.qtd_agua}")
            Text(text = "Entrega: ${pedido.entrega}")
            Text(text = "Troco: ${pedido.troco}")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListaPedidosScreenPreview(){
    ControleDeEntregaDeÁguaTheme {
        ListaPedidosScreen(listOf(Pedido()))

    }
}