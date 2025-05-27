package com.gabriel_barros.controle_entregua_agua.ui.components.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.EntregaUI
import com.gabriel_barros.controle_entregua_agua.ui.ProdutoEntregueUI
import com.gabriel_barros.controle_entregua_agua.ui.ProdutoPedidoUI
import com.gabriel_barros.controle_entregua_agua.ui.TipoEntregadorUI
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DatePicker
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropDownMenuComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker
import java.time.format.DateTimeFormatter


val produtosPedidosMock = listOf<ProdutoPedidoUI>(
    ProdutoPedidoUI(
        produtoId = 10L, produtoPedidoId = 100L, produtoNome = "Água 20L", qtdPedido = 2
    ),
    ProdutoPedidoUI(
        produtoId = 11L, produtoPedidoId = 101L, produtoNome = "Refrigerante 2L", qtdPedido = 10
    )
)

@Composable
fun CadastrarEntregaComponent(
    clienteNomePedidoId: List<Pair<Long, String>>,
    produtos: Map<Long, String>,
    produtosPedidos: List<ProdutoPedidoUI> = emptyList(),
    onSave: (EntregaUI) -> Unit
) {
    val entregaUI by remember { mutableStateOf(EntregaUI()) }


//    var tipoEntregador by remember { mutableStateOf(TipoEntregadorUI.COMERCIO) }
//    var data by remember { mutableStateOf(LocalDate.now()) }

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ComboBoxComponent(label = "Cliente", itens = clienteNomePedidoId) {
            entregaUI.pedidoId = it.first
        }

//        Text(text = "Entregue(s)")
//        NumberPicker(value = qtdEntregue,
//            onValueChange = {qtdEntregue = it},
//            minValue = 0)
        DropDownMenuComponent(itens = produtos, label = "Escolher Galões") {
            entregaUI.produtoEntregues.add(
                ProdutoEntregueUI(
                    produtoPedidoId = it.first,
                    produtoId = TODO(),
                    qtdEntregue = 0,
                    qtdRetornado = 0
                )
            )
//            qtdEntregue = it.entries.sumOf { qtd-> qtd.value }
        }
//        DropdownWithQuantity(options = galoes, "Escolher Galões"){
//            galoesId.clear()
//            galoesId.addAll(it.map {
//                item-> ItensEntrega(0,itemPedido_id = item.key, qtdEntregue = item.value, qtdRetornado = 0)
//            })
//            qtdEntregue = it.entries.sumOf { qtd-> qtd.value }
//        }
        LazyColumn {
            val produtosEntregues = entregaUI.produtoEntregues
            produtosEntregues.forEachIndexed { index, itensEntrega ->
                item {
                    Row {
                        Text(text = "Entregue(s)")
                        NumberPicker(
                            value = itensEntrega.qtdEntregue,
                            onValueChange = { produtosEntregues.elementAt(index).qtdEntregue = it },
                            minValue = 0
                        )
                        Text(text = "Seco(s)")
                        NumberPicker(
                            value = itensEntrega.qtdRetornado,
                            onValueChange = {
                                produtosEntregues.elementAt(index).qtdRetornado = it
                            },
                            minValue = 0
                        )
                    }
                }
            }
        }

        DatePicker(label = entregaUI.data.format(DateTimeFormatter.ofPattern("dd-MM-uuuu"))) {
            entregaUI.data = it
        }

        DropDownMenuComponent(
            itens = TipoEntregadorUI.entries.associateBy { it.name },
            "Escolher Entregador"
        ) {
            entregaUI.tipoEntregador = it.second
        }

        Button(onClick = { onSave(entregaUI) }) {
            Text(text = "Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCadastrarEntregaComponent() {
    val clienteNomePedidoId = listOf(1L to "João da Silva", 2L to "Maria de Souza")
    val produtos = mapOf(10L to "Água 20L", 11L to "Refrigerante 2L")
    val produtosPedidos = produtosPedidosMock

    CadastrarEntregaComponent(
        clienteNomePedidoId = clienteNomePedidoId,
        produtos = produtos,
        produtosPedidos = produtosPedidos,
        onSave = { entregaUI ->
            println("Entrega salva: $entregaUI")
        }
    )
}


