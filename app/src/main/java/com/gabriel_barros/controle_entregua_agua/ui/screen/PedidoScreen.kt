package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.usecase.dto.ProdutoPedidoOutPut
import com.gabriel_barros.usecase.usecase.ClienteManager
import com.gabriel_barros.usecase.usecase.ClienteSearch
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.FreteSearch
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.gabriel_barros.usecase.usecase.PedidoSearch
import com.gabriel_barros.usecase.usecase.ProdutoManager
import com.gabriel_barros.usecase.usecase.ProdutoSearch
import org.koin.compose.koinInject

@Composable @Deprecated("")
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

    val pedidoQuery: PedidoSearch = koinInject()
    val clienteQuery: ClienteSearch = koinInject()
    val produtoQuery: ProdutoSearch = koinInject()
    val freteQuery: FreteSearch = koinInject()

    val produtos = remember { mutableStateListOf<ProdutoPedidoOutPut>() }

    var showPedidoDetalhado by remember { mutableStateOf(false) }
    var showAddEntrega by remember { mutableStateOf(false) }
    var showAddPagamento by remember { mutableStateOf(false) }


//    var itemEntrega = remember { mutableStateListOf<EntregaEntity>() }

//    LaunchedEffect(Unit) {
//        val pedidoList = pedidoQuery.getAllPedidos().buildExecuteAsSList()
//        pedidos.clear()
//        pedidos.addAll(pedidoList.map {
//            val clientId = it.cliente_id ?: 0
//            Pair(
//                it,
//                clienteQuery.getClienteById(clientId).buildExecuteAsSingle()
//            )
//        })
//
//        produtos.clear()
//        produtos.addAll(produtoQuery.buildExecuteAsSList())
//    }

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
//        PedidoListComponent(pedidos) { pedido, cliente ->
//            itemClienteSupabase = cliente
//            itemPedidoSupabase = pedido
////            showPedidoDetalhado = true
//
//            navController.navigate("pedidoDetalhe/${pedido.id}")
//        }

    }

//    if (showPedidoDetalhado) {
//        MessageBoxComponent(onDismiss = { showPedidoDetalhado = false }) {
//            Box(modifier = Modifier.padding(10.dp)) {
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

//            }
//        }
//    }

//    if (showAddEntrega) {
//        MessageBoxComponent(onDismiss = { showAddEntrega = false }) {
//            CadastrarEntregaComponent(
//                clienteNomePedidoId = listOf(itemPedidoSupabase.id to itemClienteSupabase.nome),
//                produtos = produtos.associate { it.id to it.nome })
//            { entrega, galoes ->
//                coroutineScope.launch {
//                    entregaDAO.registerEntrega(entrega, galoes)
//
//                }
//                showAddEntrega = false
//            }
//        }
//    }

//    if (showAddPagamento) {
//        MessageBoxComponent(onDismiss = { showAddPagamento = false }) {
//            CadastrarPagamentoComponent(clientes = listOf(itemClienteSupabase.id to itemClienteSupabase.nome)) {
//
//            }
//
//        }
//    }


}