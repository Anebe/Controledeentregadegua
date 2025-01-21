
package com.gabriel_barros.controle_entregua_agua.ui.pedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.model.PedidoViewModel
import com.gabriel_barros.controle_entregua_agua.ui.names
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme
import com.gabriel_barros.controle_entregua_agua.ui.util.NumberPicker
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate


data class PedidoItemState(
    val id: Int = -1,
    val cliente: MutableState<Pair<String, Int>> = mutableStateOf(Pair("",-1)),
    //var cliente: String = "",
    val qtdAgua: MutableIntState = mutableIntStateOf(0),
    val troco: MutableIntState = mutableIntStateOf(0),
    val entrega: MutableState<LocalDate> = mutableStateOf(LocalDate.now().minusDays(3))
){
    fun to(): Pedido {
        return Pedido(
            this.id,
            this.cliente.value.second,
            qtdAgua.intValue,
            entrega.value,
            troco.intValue)
    }
}

@Composable
fun CadastroPedido(){
    val context = LocalContext.current
    val cadastroPedidoViewModel: PedidoViewModel = viewModel()
    val pedidoItems = remember { mutableStateListOf(PedidoItemState()) }


    LazyColumn (modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()
    ) {
        items(pedidoItems) {item ->
            FormPedido(item)
        }

        item {
            Row (horizontalArrangement = Arrangement.SpaceBetween){
                Button(
                    modifier = Modifier
                        .width(150.dp),
                    onClick = { pedidoItems.add(PedidoItemState()) }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar")
                }
                Button(onClick = {
                    cadastroPedidoViewModel.add(pedidoItems.toList())
                }) {
                    BasicText(text = "Salvar")
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroPedidoItem(data: PedidoItemState) {
    var quandoPuder by remember { mutableStateOf(true) }
    var expandedTroco by remember { mutableStateOf(false) }
    val entregaShow = rememberUseCaseState(visible = false)

    val filteredItems = remember(data.cliente) {
        names.filter { it.key.contains(data.cliente.value.first, ignoreCase = true) }
            .entries
            .take(3)
            .toList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Column {
            TextField(
                value = data.cliente.value.first,
                onValueChange = { newValue:String ->
                    val id = names[newValue]
                    id?.let {
                        data.cliente.value = Pair(newValue, id)
                    }
                },
                label = { Text("Cliente") },
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn {
                items(filteredItems) { item ->
                    Text(
                        text = item.key,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                data.cliente.value = item.toPair()
                            }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box {
                Button(
                    onClick = { expandedTroco = !expandedTroco }) {
                    Text(text = "Troco para ${data.troco.intValue}")
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(
                    expanded = expandedTroco,
                    onDismissRequest = { expandedTroco = false},
                    modifier = Modifier.height(125.dp)
                ) {
                    for (i in 10..100 step 5) {
                        DropdownMenuItem(
                            text = { Text(i.toString()) },
                            onClick = {
                                data.troco.intValue = i
                                expandedTroco = false
                            },
                        )
                    }
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Quantidade")
                NumberPicker(
                    value = data.qtdAgua.intValue,
                    onValueChange = { data.qtdAgua.intValue = it },
                    minValue = 1)
            }

        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = quandoPuder,
                        onCheckedChange = {
                            quandoPuder = it
                            if(it){
                                data.entrega.value = LocalDate.MIN
                            }
                        }
                    )
                    Text("Quando puder")
                }

                Button(onClick = { entregaShow.show() },
                    enabled = !quandoPuder) {
                    Text("Escolher data")
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Escolher Data")
                }
            }
            CalendarDialog(
                state = entregaShow,
                selection = CalendarSelection.Date(
                    selectedDate = data.entrega.value
                ) { newDate ->
                    data.entrega.value = newDate
                }
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun CadastroPedidoItemPreview() {
    ControleDeEntregaDeÁguaTheme {
        CadastroPedido()

    }
}

