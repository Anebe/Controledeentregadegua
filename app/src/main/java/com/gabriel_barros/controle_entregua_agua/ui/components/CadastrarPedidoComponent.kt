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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropDownMenuComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropdownWithQuantity
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import java.time.LocalDate

data class PedidoItemState(
    val cliente: MutableState<Pair<Long, String>> = mutableStateOf(Pair(-1, "")),
    //var cliente: String = "",
    val qtdAgua: MutableIntState = mutableIntStateOf(0),
    val troco: MutableDoubleState = mutableDoubleStateOf(0.0),
    val entrega: MutableState<LocalDate> = mutableStateOf(LocalDate.now().minusDays(3))
) {
    fun to(): Pedido {
        return Pedido(
            data = entrega.value,
            troco = troco.doubleValue
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CadastrarPedidoComponent(
    nomes: List<Pair<Long, String>>,
    produtos: List<Pair<Long, String>>,
    onSave: (Pedido, Map<Long, Int>) -> Unit
) {
//    val data by remember { mutableStateOf(PedidoItemState()) }
    var quandoPuder by remember { mutableStateOf(true) }
    var levarTroco by remember { mutableStateOf(false) }
    var troco by remember { mutableStateOf("") }
    val entregaShow = rememberUseCaseState(visible = false)
    var pedido by remember { mutableStateOf(Pedido()) }
    var produtosEscolhidos by remember { mutableStateOf(emptyMap<Long, Int>()) }
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        ComboBoxComponent(itens = nomes, label = "Cliente") {
            //data.cliente.value = it
            pedido = Pedido(cliente_id = it.first)
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.clickable { levarTroco = !levarTroco },
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = levarTroco,
                    onCheckedChange = { },
                )
                Text("Levar Troco")
            }
            if(levarTroco){
                OutlinedTextField(
                    value = troco,
                    onValueChange = {
                        troco = it
                        pedido = pedido.copy(troco = it.toDoubleOrNull() ?: 0.0)
                    },
                    label = { Text("Troco para") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(135.dp),
                    enabled = levarTroco
                )
            }

//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Text(text = "Quantidade")
//                NumberPicker(
//                    value = data.qtdAgua.intValue,
//                    onValueChange = { data.qtdAgua.intValue = it },
//                    minValue = 0)
//            }

        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.clickable {
//                    quandoPuder = !quandoPuder
//                }
//            ) {
//                Checkbox(
//                    checked = quandoPuder,
//                    onCheckedChange = { quandoPuder = !quandoPuder },
//                )
//                Text("Quando puder")
//            }
//
//
//            Button(
//                onClick = { entregaShow.show() },
//                enabled = !quandoPuder
//            ) {
//                Text("Escolher data")
//                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Escolher Data")
//            }
//            CalendarDialog(
//                state = entregaShow,
//                selection = CalendarSelection.Date(
//                    selectedDate = pedido.data
//                ) { newDate ->
//                    pedido = pedido.copy(data = newDate)
//                }
//            )
//        }
        DropDownMenuComponent(label = "Entrega Para",itens = mapOf(
            LocalDate.now() to "Hoje",
            LocalDate.now().plusDays(1) to "Amanhã",
            LocalDate.now().plusDays(2) to "Depois de Amanhã",
        )) {
            pedido = pedido.copy(data_entrega = it.first)
        }
        DropdownWithQuantity(options = produtos.toMap(), label = "Produtos") {
            produtosEscolhidos = it
        }

        Button(modifier = Modifier.align(Alignment.End), onClick = {
            onSave(pedido, produtosEscolhidos)
        }) {
            Text("Salvar")
        }
    }
}

@Composable
private fun MenuItem(onSelect: (value: Int) -> Unit) {
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
    ControleDeEntregaDeAguaTheme {
        CadastrarPedidoComponent(
            nomes = listOf(1L to "asd"),
            produtos = listOf(1L to "asd")
        ) { pedido, map ->

        }
    }
}