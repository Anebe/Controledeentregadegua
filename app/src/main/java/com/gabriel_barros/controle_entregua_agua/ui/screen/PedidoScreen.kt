package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarEntregaComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarPagamentoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.PedidoListComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MessageBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.Pedido
import com.gabriel_barros.domain.domain.entity.Produto
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun PedidoScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

//    val pedidoDAO = remember { PedidoService(PedidoDAO(SupabaseClientProvider.supabase)) }
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
//    val entregaDAO = remember { EntregaService(EntregaDao(SupabaseClientProvider.supabase)) }
//    val galaoDAO = remember { ProdutoService(ProdutoDAO(SupabaseClientProvider.supabase)) }

    val pedidoDAO: PedidoManager = koinInject()
    val clienteDAO: ClienteManager = koinInject()
    val produtoDAO: ProdutoManager = koinInject()
    val entregaDAO: EntregaManager = koinInject()

    val pedidoQuery: PedidoQueryBuilder = koinInject()
    val clienteQuery: ClienteFilterBuilder = koinInject()
    val produtoQuery: ProdutoQueryBuilder = koinInject()
    val entregaQuery: EntregaQueryBuilder = koinInject()

    val produtos = remember { mutableStateListOf<Produto>() }
    val pedidos = remember { mutableStateListOf<Pair<Pedido, Cliente>>() }

    var showPedidoDetalhado by remember { mutableStateOf(false) }
    var showAddEntrega by remember { mutableStateOf(false) }
    var showAddPagamento by remember { mutableStateOf(false) }


    var itemPedidoSupabase by remember { mutableStateOf(Pedido.emptyPedido()) }
    var itemClienteSupabase by remember { mutableStateOf(Cliente.emptyCliente()) }
    var itemEntrega = remember { mutableStateListOf<Entrega>() }

    LaunchedEffect(Unit) {
        val pedidoList = pedidoQuery.getAllPedidos().buildExecuteAsSList()
        pedidos.clear()
        pedidos.addAll(pedidoList.map {
            Pair(
                it,
                clienteQuery.getClienteById(it.cliente_id).buildExecuteAsSingle()
            )
        })

        produtos.clear()
        produtos.addAll(produtoQuery.buildExecuteAsSList())
    }

    Column(modifier = Modifier.padding(30.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            H3(text = "Pedidos")
            Button(onClick = { navController.navigate("cadastrarPedidoScreen") }) {
                Text(text = "Adicionar")
            }
        }
        HorizontalDivider()
//        LazyColumn {
//            items(pedidos.size ){ index ->
//                PedidoItemResumido(
//                     cliente = pedidos[index].second,
//                    pedido = pedidos[index].first,
//                )
//            }
//        }
        PedidoListComponent(pedidos) { pedido, cliente ->
            itemClienteSupabase = cliente
            itemPedidoSupabase = pedido
//            showPedidoDetalhado = true
            //TODO melhorar esse async
            coroutineScope.launch {
                itemEntrega.addAll(entregaQuery.getAllEntregasByPedido(itemPedidoSupabase.id).buildExecuteAsSList())

            }
            navController.navigate("pedidoDetalhe/${pedido.id}")
        }

    }

    if (showPedidoDetalhado) {
        MessageBoxComponent(onDismiss = { showPedidoDetalhado = false }) {
            Box(modifier = Modifier.padding(10.dp)) {
//            PedidoItemDetalhado(
//                pedido = itemPedidoSupabase,
//                cliente = itemClienteSupabase,
//                entregas = itemEntrega.toList(),
//                emptyList(),
//                emptyList(), emptyList(),
//                { valorPago, qtdEntregue, qtdSeco ->
//                },
//                {},
//                {
//                    showAddEntrega = true
//                })

            }
        }
    }

    if (showAddEntrega) {
        MessageBoxComponent(onDismiss = { showAddEntrega = false }) {
            CadastrarEntregaComponent(
                clientePedidoId = listOf(itemPedidoSupabase.id to itemClienteSupabase.nome),
                galoes = produtos.associate { it.id to it.nome })
            { entrega, galoes ->
                coroutineScope.launch {
                    entregaDAO.registerEntrega(entrega, galoes)

                }
                showAddEntrega = false
            }
        }
    }

    if (showAddPagamento) {
        MessageBoxComponent(onDismiss = { showAddPagamento = false }) {
            CadastrarPagamentoComponent(clientes = listOf(itemClienteSupabase.id to itemClienteSupabase.nome)) {

            }

        }
    }


}

@Preview(showBackground = true)
@Composable
private fun PedidoScreenPreview() {
    val navController = rememberNavController()

//    PedidoScreen(navController = navController)
    ControleDeEntregaDeAguaTheme {
        Column {
            H3(text = "Pedidos")
            HorizontalDivider()
            Text(text = "asdsa")
            Button(onClick = { /*TODO*/ }) {

            }
        }

    }
}