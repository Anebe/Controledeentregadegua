package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarEntregaResumidoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MyBox
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme


@Composable
fun PedidoItemDetalhado(
    produtos: List<Produto>,
    pedido: Pedido,
    cliente: Cliente,
    entregas: List<Entrega>,
    produtosPedidos: List<ItensPedido>,
    produtosEntregues: List<ItensEntrega>,
    pagamentos: List<Pagamento>,
    onSave: (valorPago: Double, entrega: Int, galaoSeco: Int) -> Unit,
    onDelete: () -> Unit,
    onAddEntrega: () -> Unit,
) {

    var valorPago by remember { mutableStateOf("") }
    var entrega by remember { mutableIntStateOf(0) }
    var galaoSeco by remember { mutableIntStateOf(0) }
    var showCadastroEntrega by remember { mutableStateOf(false) }



    Column(modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Text(text = "Cliente: ${cliente.nome}")
//        Text(text = "Galão(ões): ${pedidoSupabase.qtd} ")
        MyBox(modifier = Modifier.fillMaxWidth()) {

            if (showCadastroEntrega) {
                CadastrarEntregaResumidoComponent(
                    produtosEntregues = produtos,
                    entregasAnteriores = produtosEntregues,
                    itensPedido = produtosPedidos
                ) {
                    showCadastroEntrega = false
                }
            } else {
                EntregaRelatorioComponent(produtos, produtosPedidos, produtosEntregues)
                Button(
                    modifier = Modifier.width(100.dp).align(Alignment.CenterHorizontally),
                    onClick = { showCadastroEntrega = true }) {
                    Text(text = "+")
                }
            }
        }
        MyBox(modifier = Modifier.fillMaxWidth()) {
            PagamentoRelatorioComponent(pedido, pagamentos)
        }

        Text(text = "Fazer Entrega Em: ${pedido.data.dayOfMonth}/${pedido.data.monthValue}/${pedido.data.year}")


    }

}

@Composable
fun PagamentoRelatorioComponent(pedido: Pedido, pagamentos: List<Pagamento>) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Text("Total: R$${pedido.valor_total}")
            val total = pagamentos.sumOf { it.valor }
            var totalStr = total.toString()
            Row(modifier = Modifier.wrapContentWidth()) {
                Text("Pago: R$")
                OutlinedTextField(
                    value = totalStr,
                    onValueChange = { newText ->
                        val numeric = newText.filter { it.isDigit() }
                        val value = numeric.toDoubleOrNull() ?: 0
                        totalStr = value.toString() // atualiza o texto mostrado
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(75.dp)

                )
            }
            Text("Falta: R$${pedido.valor_total - total} ")
//                    Button(onClick = { /*TODO*/ },
//                        modifier = Modifier.wrapContentSize()) {
//                        Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Entrega", modifier = Modifier.size(10.dp))
//                    }
        }
    }
}

@Composable
fun EntregaRelatorioComponent(produtos: List<Produto>, itensPedidos: List<ItensPedido>, produtosEntregues: List<ItensEntrega>) {
    Column {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly) {
            Text("Produto")
            Text("Entregue")
            Text("Retornado")
        }
        LazyColumn {
            itensPedidos.forEachIndexed { index, itensPedido ->
                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        val entregue =
                            produtosEntregues.filter { it.itemPedido_id == itensPedido.id }
                                .sumOf { it.qtdEntregue }
                        val retornado =
                            produtosEntregues.filter { it.itemPedido_id == itensPedido.id }
                                .sumOf { it.qtdRetornado }
                        val prod = produtos.find { itensPedido.produto_id == it.id}?.nome
                        Text("${prod}")
                        Text("${entregue}/${itensPedido.qtd}")
                        Text("${retornado}")
                    }
                }
            }
        }
    }

}

@Composable
fun PedidoItemResumido(
    pedido: Pedido,
    cliente: Cliente,
    onClick: () -> Unit
) {

    Card(modifier = Modifier
        .clickable { onClick() }
        .padding(5.dp))
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround
        )
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


@Preview(
    name = "Samsung J2 Core (Simulado)",
    device = "spec:shape=Normal,width=540,height=960,unit=px,dpi=220",
    showBackground = true
)
@Composable
fun PedidoItemDetalhadoPreview() {

    val itensPedido = listOf(
        ItensPedido(id = 1, pedido_id = 101, produto_id = 1001, qtd = 5),
        ItensPedido(id = 2, pedido_id = 102, produto_id = 1002, qtd = 3)
    )

    val itensEntrega = listOf(
        ItensEntrega(entrega_id = 201, itemPedido_id = 1, qtdEntregue = 3, qtdRetornado = 1),
        ItensEntrega(entrega_id = 202, itemPedido_id = 2, qtdEntregue = 2, qtdRetornado = 0)
    )
    ControleDeEntregaDeAguaTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            PedidoItemDetalhado(
                produtos = emptyList(),
                pedido = Pedido.emptyPedido(),
                cliente = Cliente.emptyCliente(),
                entregas = emptyList(),
                produtosPedidos = itensPedido,
                produtosEntregues = itensEntrega,
                emptyList(), onSave = { a, b, c ->/*TODO*/ }, {}, {}
            )
        }

    }
}