package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme


@Composable
fun PedidoListComponent(pedidos: List<com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido>){

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
            LazyColumn {
                items(pedidos.size){
                    PedidoItem(data = pedidos[it])
                }
            }
        }
    }
}
@Composable
fun PedidoItem(data: com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido){

    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = data.cliente_id.toString())
        Text(text = data.qtd.toString())
        val troco = data.troco - data.qtd * 7
        Text(text = troco.toString())
        Text(text = data.data.toString())
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
        PedidoListComponent(emptyList())

    }
}