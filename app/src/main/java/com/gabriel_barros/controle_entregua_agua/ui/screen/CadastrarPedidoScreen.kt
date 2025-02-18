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
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarPedidoComponent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


@Composable
fun CadastrarPedidoScreen (navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
//    val pedidoDAO = remember { PedidoService(PedidoDAO(SupabaseClientProvider.supabase)) }
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
//    val produtoDAO = remember { ProdutoService(ProdutoDAO(SupabaseClientProvider.supabase)) }

    val pedidoDAO: PedidoUseCase = koinInject()
    val clienteDAO: ClienteUseCase = koinInject()
    val produtoDAO: ProdutoUseCase = koinInject()

    var nomesClientes by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }
    var nomesProdutos by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }

    LaunchedEffect(Unit) {
        nomesClientes = clienteDAO.getAllClientesNomes()
        nomesProdutos = produtoDAO.getAllProdutosNomes()
    }
    Box(modifier = Modifier.padding(10.dp)){
        CadastrarPedidoComponent(nomesClientes, nomesProdutos) { pedido, produtos ->
            val prod = produtos.map { (id, quantidade) -> ItensPedido(produto_id =  id, qtd =  quantidade) }.toSet()
            val newCliente = pedidoDAO.savePedido(pedido, prod)
            newCliente?.let {
                navController.popBackStack()
            }
        }
    }
}