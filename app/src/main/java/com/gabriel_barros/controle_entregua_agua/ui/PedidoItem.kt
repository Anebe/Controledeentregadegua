package com.gabriel_barros.controle_entregua_agua.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabriel_barros.controle_entregua_agua.model.PedidoViewModel
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme



@Composable
fun PedidoList(){
    val cadastroPedidoViewModel: PedidoViewModel = viewModel()

    //val items = remember { mutableStateListOf(PedidoItemState()) }

    Column {


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Nome")
            Text(text = "Quantidade")
            Text(text = "Troco")
            Text(text = "Data")
            Text(text = "Editar")
            Text(text = "Concluir")
        }
        Column {

        }
    }
}
@Composable
fun PedidoItem(data: Pedido){

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = data.clienteId.toString())
        Text(text = data.qtd_agua.toString())
        val troco = data.troco - data.qtd_agua * 7
        Text(text = troco.toString())
        Text(text = data.entrega.toString())
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
        }
        Checkbox(checked = false, onCheckedChange = {})
    }

}

@Preview(showBackground = true)
@Composable
fun previewPedidoList(){
    ControleDeEntregaDeÁguaTheme {
        PedidoList()

    }
}