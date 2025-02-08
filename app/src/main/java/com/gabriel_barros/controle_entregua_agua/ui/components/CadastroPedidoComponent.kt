package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.names
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

data class PedidoItemState(
    val cliente: MutableState<Pair<String, Long>> = mutableStateOf(Pair("",-1)),
    //var cliente: String = "",
    val qtdAgua: MutableIntState = mutableIntStateOf(0),
    val troco: MutableIntState = mutableIntStateOf(0),
    val entrega: MutableState<LocalDate> = mutableStateOf(LocalDate.now().minusDays(3))
){
    fun to(): Pedido {
        return Pedido(
            cliente_id = this.cliente.value.second,
            qtd = qtdAgua.intValue,
            data = entrega.value,
            troco = troco.intValue)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroPedidoComponent(nomes: Map<String, Long>, onSave: (pedido: Pedido) -> Unit) {
    val data by remember { mutableStateOf(PedidoItemState()) }
    var quandoPuder by remember { mutableStateOf(true) }
    val entregaShow = rememberUseCaseState(visible = false)


    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {



        ComboBoxComponent(itens = nomes) {
            data.cliente.value = it
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                TextField(
                    value = data.troco.value.toString(),
                    onValueChange = { data.troco.value = it.toIntOrNull() ?: 0 },
                    label = {Text("Troco para")},
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(135.dp)
                )

            }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Quantidade")
                NumberPicker(
                    value = data.qtdAgua.intValue,
                    onValueChange = { data.qtdAgua.intValue = it },
                    minValue = 0)
            }

        }


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        quandoPuder = !quandoPuder
                        if(quandoPuder){
                            data.entrega.value = LocalDate.MIN
                        }
                    }
                ) {
                    Checkbox(
                        checked = quandoPuder,
                        onCheckedChange = {},
                    )
                    Text("Quando puder")
                }


                Button(
                    onClick = { entregaShow.show() },
                    enabled = !quandoPuder) {
                    Text("Escolher data")
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Escolher Data")
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
        Button(onClick = { onSave(data.to()) }) {
            Text("Salvar")
        }
    }
}

@Composable
private fun MenuItem(onSelect: (value: Int) ->Unit){
    var expandedTroco = false
    Box {
        Button(
            onClick = { expandedTroco = !expandedTroco },
        ) {
            Text(text = "Troco para")
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Mais"
            )
        }
        DropdownMenu(
            expanded = expandedTroco,
            onDismissRequest = { expandedTroco = false },
            modifier = Modifier.height(200.dp)
        ) {
            for (i in 10..100 step 5) {
                DropdownMenuItem(
                    text = { Text(i.toString()) },
                    onClick = {
                        onSelect(i)
                        expandedTroco = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormPedidoPreview() {
    ControleDeEntregaDeÁguaTheme {
        CadastroPedidoComponent(names){ }

    }
}