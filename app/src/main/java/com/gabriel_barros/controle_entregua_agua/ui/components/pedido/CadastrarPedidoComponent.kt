package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropDownMenuComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropdownWithQuantity
import com.gabriel_barros.usecase.dto.PedidoInput
import com.gabriel_barros.usecase.dto.ProdutoPedidoInput
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.LocalDate


@Composable
fun CadastrarPedidoComponent() {


    val idWithNomesClientes = mapOf<Long, String>()
    val idWithNomesProdutos = mapOf<Long, String>()

    val pedidoManager: PedidoManager = koinInject()
    val scope = rememberCoroutineScope()

    CadastrarPedidoComponent(
        idWithNomesClientes = idWithNomesClientes,
        idWithNomesProdutos = idWithNomesProdutos
    ) { clienteId, produtos ->
        val produtosDesejadosSet = mutableSetOf<ProdutoPedidoInput>()
        produtos.forEach { produto ->
            produtosDesejadosSet.add(
                ProdutoPedidoInput(
                    qtd = produto.value,
                    produtoId = produto.key
                )
            )

        }
        scope.launch {
            pedidoManager.makePedido(
                PedidoInput(
                    clienteId = clienteId,
                    produtos = produtosDesejadosSet,
                )
            )

        }

    }
}

@Composable
fun CadastrarPedidoComponent(
    idWithNomesClientes: Map<Long, String>,
    idWithNomesProdutos: Map<Long, String>,
    onSave:(clienteId: Long, produtosDesejados: Map<Long,Int>) -> Unit,
){
    var clienteId: Long = 0
    var produtosDesejados = mutableMapOf<Long, Int>()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        ComboBoxComponent(itens = idWithNomesClientes.toList(), label = "Cliente") {
            //data.cliente.value = it
            clienteId = it.first
        }

        DropdownWithQuantity(options = idWithNomesProdutos, label = "Produtos") {
            produtosDesejados = it.toMutableMap()
        }

        Button(modifier = Modifier.align(Alignment.End), onClick = {
            onSave(clienteId, produtosDesejados)
        }) {
            Text("Salvar")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetDataAgendadaComponente(){
    var quandoPuder by remember { mutableStateOf(true) }
    var dataParaEntrega by remember { mutableStateOf(LocalDate.MIN) }
    val entregaShow = rememberUseCaseState(visible = false)


    Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    quandoPuder = !quandoPuder
                }
            ) {
                Checkbox(
                    checked = quandoPuder,
                    onCheckedChange = { quandoPuder = !quandoPuder },
                )
                Text("Quando puder")
            }


            Button(
                onClick = { entregaShow.show() },
                enabled = !quandoPuder
            ) {
                Text("Escolher data")
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Escolher Data")
            }
            CalendarDialog(
                state = entregaShow,
                selection = CalendarSelection.Date(
                    selectedDate = dataParaEntrega
                ) { newDate ->
                    dataParaEntrega = newDate
                }
            )
        }
        DropDownMenuComponent(label = "Entrega Para",itens = mapOf(
            LocalDate.now() to "Hoje",
            LocalDate.now().plusDays(1) to "Amanhã",
            LocalDate.now().plusDays(2) to "Depois de Amanhã",
        )) {
            dataParaEntrega = it.first
        }
}
@Composable
fun GetTrocoComponent(){
    var levarTroco by remember { mutableStateOf(false) }
    var troco by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.clickable { levarTroco = !levarTroco },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = levarTroco,
                onCheckedChange = { },
            )
            Text("Levar Troco")
        }
        if (levarTroco) {
            OutlinedTextField(
                value = troco,
                onValueChange = { troco = it },
                label = { Text("Troco para") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.width(135.dp),
                enabled = levarTroco
            )
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
