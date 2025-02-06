package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.util.NumberPicker
import com.gabriel_barros.controle_entregua_agua.ui.names
import com.gabriel_barros.controle_entregua_agua.ui.principal.PedidoItemState
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPedido(data: PedidoItemState) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    var quandoPuder by remember { mutableStateOf(true) }
    var expandedTroco by remember { mutableStateOf(false) }
    var expandedClientes by remember { mutableStateOf(false) }
    val entregaShow = rememberUseCaseState(visible = false)
    val filteredItems = remember(data.cliente.value.first)  {
        names.filter { it.key.contains(data.cliente.value.first, ignoreCase = true) }
            .entries
            .take(3)
            .toList()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column (
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
        ){
            TextField(
                value = data.cliente.value.first,
                onValueChange = { newValue:String ->
                    if(!expandedClientes) expandedClientes = true
                    data.cliente.value = Pair(newValue, -1)
                    val id = names[newValue]
                    id?.let {
                        data.cliente.value = Pair(newValue, id)
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                }),
                label = { Text("Cliente") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (!it.hasFocus) {
                            expandedClientes = false
                        }
                    }
                    .clickable { expandedClientes = true },
            )
            if(expandedClientes){
                Row {
                    filteredItems.forEach { item ->
                        Card(
                            onClick = {
                                data.cliente.value = item.toPair()
                                expandedClientes = false
                            },modifier = Modifier.padding(horizontal = 3.dp)
                        ){
                            Text(item.key, modifier = Modifier.padding(6.dp))
                        }
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {

            Box {
                Button(
                    onClick = { expandedTroco = !expandedTroco },
                ) {
                    Text(text = "Troco para ${data.troco.intValue}")
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "More"
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

    }
}

@Preview(showBackground = true)
@Composable
fun FormPedidoPreview() {
    ControleDeEntregaDeÁguaTheme {
        FormPedido(PedidoItemState())

    }
}