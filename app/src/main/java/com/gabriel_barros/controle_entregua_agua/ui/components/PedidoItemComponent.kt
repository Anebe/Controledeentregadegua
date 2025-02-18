package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker


@Preview(showBackground = true)
@Composable
fun PedidoItemDetalhadoPreview(){
    Column (modifier = Modifier.fillMaxSize()){
        PedidoItemDetalhado(Pedido(),Cliente(nome = ""),
            emptyList(), onSave = { a,b,c ->/*TODO*/ },{}) {}
        PedidoItemResumido(pedido = Pedido(), Cliente(nome = "")) {}
        PedidoItemResumido(pedido = Pedido(), Cliente(nome = "")) {}
    }
}

@Composable
fun PedidoItemDetalhado(pedido: Pedido,
                        cliente: Cliente,
                        entregas: List<Entrega>,
                        onSave: (valorPago: Double,
                                 entrega: Int,
                                 galaoSeco: Int) -> Unit,
                        onDelete: () -> Unit,
                        onAddEntrega: () -> Unit) {

    var valorPago by remember { mutableStateOf("") }
    var entrega by remember { mutableIntStateOf(0) }
    var galaoSeco by remember { mutableIntStateOf(0) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Cliente: ${cliente.nome}")
//        Text(text = "Galão(ões): ${pedidoSupabase.qtd} ")
        Button(onClick = { onAddEntrega() }) {
            Text("Entrega:")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Entrega")
        }
        Row {
            Text("Galão Seco")
            NumberPicker(value = galaoSeco, onValueChange = {galaoSeco = it})
        }
        Text(text = "Fazer Entrega Em: ${pedido.data}")
        Text(text = "Levar Troco: ${pedido.troco}")
        Row {
            Text(text = "Foi Pago:")
            TextField(value = valorPago.toString(),
                onValueChange = {
                        valorPago = it
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Button(onClick = { onSave(valorPago.toDouble(), entrega, galaoSeco)}) {
                Text(text = "Salvar")
            }
            Button(onClick = { onDelete()}) {
                Text(text = "Deletar")
            }
        }
    }

}

@Composable
fun PedidoItemResumido(pedido: Pedido,
                       cliente: Cliente,
                       onClick: () -> Unit){

    Card(modifier = Modifier
        .clickable { onClick() }
        .padding(5.dp))
    {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround)
        {
            Text(text = cliente.nome.toString())
//            Text(text = pedidoSupabase.qtd.toString())
//            val troco = pedidoSupabase.troco - pedidoSupabase.qtd * 7
//            Text(text = "R$ ${troco.toString()}")
            //Text(text = data.data.toString())
            //IconButton(onClick = { /*TODO*/ }) {
            //    Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
            //}
            //Checkbox(checked = false, onCheckedChange = {})
        }

    }
}

