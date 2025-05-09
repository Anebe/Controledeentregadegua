package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.CadastrarPedidoComponent
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun CadastrarPedidoScreen (navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
//    val pedidoDAO = remember { PedidoService(PedidoDAO(SupabaseClientProvider.supabase)) }
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
//    val produtoDAO = remember { ProdutoService(ProdutoDAO(SupabaseClientProvider.supabase)) }

    val pedidoManager: PedidoManager = koinInject()
    val clienteManager: ClienteManager = koinInject()
    val produtoManager: ProdutoManager = koinInject()
    val clienteQuery: ClienteFilterBuilder = koinInject()
    val produtoQuery: ProdutoQueryBuilder = koinInject()

    var nomesClientes by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }
    var nomesProdutos by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }

    LaunchedEffect(Unit) {
        nomesClientes = clienteQuery.getAllClientes().buildExecuteAsSList().map { Pair(it.id,it.nome) }
        nomesProdutos = produtoQuery.buildExecuteAsSList().map { Pair(it.id,it.nome) }
    }
    Box(modifier = Modifier.padding(10.dp)){
        CadastrarPedidoComponent(nomesClientes, nomesProdutos) { pedido, produtos ->
            val prod = produtos.map { (id, quantidade) ->
                PedidoManager.ItemDoPedidoInput(produtoId = id, qtd =  quantidade) }.toSet()
            val pedidoInput = PedidoManager.PedidoInput(pedido.cliente_id, prod)
            coroutineScope.launch {
                val newCliente = pedidoManager.makePedido(pedidoInput)
                newCliente.let {
                    navController.popBackStack()
                }
            }
        }
    }
}