package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarEntregaResumidoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MyBox
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import org.koin.compose.koinInject


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
    onSaveEntrega: (Entrega, List<ItensEntrega>) -> Unit,
    onSavePagamento: (Long, Double, TipoPagamento) -> Unit
) {

    var valorPago by remember { mutableStateOf("") }
    var entrega by remember { mutableIntStateOf(0) }
    var galaoSeco by remember { mutableIntStateOf(0) }
    var showCadastroEntrega by remember { mutableStateOf(false) }
    var showCadastroPagamento by remember { mutableStateOf(false) }



    Column(
        modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "Cliente: ${cliente.nome}")
//        Text(text = "Galão(ões): ${pedidoSupabase.qtd} ")
        MyBox(modifier = Modifier.fillMaxWidth()) {

            if (showCadastroEntrega) {
                CadastrarEntregaResumidoComponent(
                    produtosEntregues = produtos,
                    entregasAnteriores = produtosEntregues,
                    itensPedido = produtosPedidos
                ) { entrega, itensEntrega ->
                    showCadastroEntrega = false
                    onSaveEntrega(entrega, itensEntrega)
                }
            } else {
                EntregaRelatorioComponent(produtos, produtosPedidos, produtosEntregues)
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = { showCadastroEntrega = true }) {
                    Text(text = "Adicionar")
                }
            }
        }
        MyBox(modifier = Modifier.fillMaxWidth()) {
            if (showCadastroPagamento) {
                CadastrarPagamentoResumidoComponent(
                    onSave = {
                        showCadastroPagamento = false
                        onSavePagamento(cliente.id, it, TipoPagamento.DINHEIRO)
                    }
                )
            } else {
                PagamentoRelatorioComponent(pedido, pagamentos)
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { showCadastroPagamento = true }) {
                    Text(text = "Adicionar")
                }
            }
        }

        Text(text = "Fazer Entrega Em: ${pedido.data.dayOfMonth}/${pedido.data.monthValue}/${pedido.data.year}")


    }

}

@Composable
fun PagamentoRelatorioComponent(pedido: Pedido, pagamentos: List<Pagamento>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Text("Total: R$${pedido.valor_total}")
            val total = pagamentos.sumOf { it.valor }
            Text("Pago: R$${total}")
            Text("Falta: R$${pedido.valor_total - total} ")
        }
    }
}

@Composable
fun CadastrarPagamentoResumidoComponent(onSave: (Double) -> Unit) {
    //TODO Adicionar Tipo do pagamento na UI
    var totalStr by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedTextField(
            value = totalStr,
            onValueChange = { newText ->
                totalStr = ""
                totalStr = newText.filter { it.isDigit() }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(75.dp)
        )

        Button(onClick = { onSave(totalStr.toDoubleOrNull() ?: 0.0) }) {
            Text("Salvar")
        }
    }

}

@Composable
fun EntregaRelatorioComponent(
    produtos: List<Produto>,
    itensPedidos: List<ItensPedido>,
    produtosEntregues: List<ItensEntrega>
) {
    val produtoWeight = 0.3f
    val entregaWeight = 0.3f
    val retornadoWeight = 0.3f
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Produto", Modifier.weight(produtoWeight))
            Text("Entregue", Modifier.weight(entregaWeight))
            Text("Retornado", Modifier.weight(retornadoWeight))
        }
        LazyColumn {
            itensPedidos.forEachIndexed { index, itensPedido ->
                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        val entregue = produtosEntregues
                            .filter { it.itemPedido_id == itensPedido.id }
                            .sumOf { it.qtdEntregue }
                        val retornado = produtosEntregues
                            .filter { it.itemPedido_id == itensPedido.id }
                            .sumOf { it.qtdRetornado }
                        val prod = produtos.find { itensPedido.produto_id == it.id }?.nome

                        Text("${prod}", Modifier.weight(produtoWeight))
                        Text("${entregue}/${itensPedido.qtd}", Modifier.weight(entregaWeight))
                        Text("${retornado}", Modifier.weight(retornadoWeight))
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

    onClick: () -> Unit,
    onEntregaCheckChange: (Boolean, Long) -> Unit,
    onPagamentoCheckChange: (Boolean, Long) -> Unit,
) {
    val pagamentoDAO: PagamentoQueryBuilder = koinInject()
    val itemEntregaDAO: ItemEntregaQueryBuilder = koinInject()
    val itemPedidoDAO: ItemPedidoQueryBuilder = koinInject()

    var totalPedido by remember { mutableIntStateOf(0) }
    var totalEntregue by remember { mutableIntStateOf(0) }
    var totalPago by remember { mutableDoubleStateOf(0.0) }

    var isEntregue by remember { mutableStateOf(false) }
    var isPago by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        totalPago = pagamentoDAO
            .getPagamentosByPedido(pedido.id)
            .buildExecuteAsSList()
            .sumOf { it.valor }

        val itensPedidos = itemPedidoDAO.getAllItensByPedidoId(pedido.id).buildExecuteAsSList()

        totalPedido = itensPedidos.sumOf { it.qtd }

        totalEntregue =
            itemEntregaDAO.getAllItensByItemPedidoId(*itensPedidos.map { it.id }.toLongArray())
                .buildExecuteAsSList().sumOf { it.qtdEntregue }
    }


    MyBox(
        modifier = Modifier
            .clickable { onClick() }
            .padding(5.dp))
    {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(text = cliente.nome)
            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text("${totalEntregue}/$totalPedido")
                Text("R$ $totalPago/${pedido.valor_total}")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.clickable { isEntregue = !isEntregue }) {
                    //TODO bloquear se a entrega está completa
                    Text("Entregue")
                    Checkbox(
                        checked = isEntregue,
                        onCheckedChange = {
                            isEntregue = it
                            onEntregaCheckChange(it, pedido.id)
                        },
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { isPago = !isPago }) {
                    //TODO bloquear se o pagamento está completo

                    Text("Pago")
                    Checkbox(
                        checked = isPago,
                        onCheckedChange = {
                            isPago = it
                            onPagamentoCheckChange(it, pedido.id)
                        },
                    )
                }
            }
        }

    }


}

@Preview(showBackground = true)
@Composable
private fun preview() {
    ControleDeEntregaDeAguaTheme {
        PedidoItemResumido(pedido = Pedido.emptyPedido(), cliente = Cliente.emptyCliente(),
            {}, {} as (Boolean, Long) -> Unit, {} as (Boolean, Long) -> Unit)
    }
}

@Preview(
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
                emptyList(), onSave = { a, b, c ->/*TODO*/ }, {}, {},
                onSaveEntrega = { } as (Entrega, List<ItensEntrega>) -> Unit,
                onSavePagamento = TODO()
            )
        }

    }
}