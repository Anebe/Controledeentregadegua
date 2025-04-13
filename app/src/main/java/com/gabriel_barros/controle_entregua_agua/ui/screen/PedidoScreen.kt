package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarEntregaComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarPagamentoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.PedidoListComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MessageBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MySection
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun PedidoScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

//    val pedidoDAO = remember { PedidoService(PedidoDAO(SupabaseClientProvider.supabase)) }
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
//    val entregaDAO = remember { EntregaService(EntregaDao(SupabaseClientProvider.supabase)) }
//    val galaoDAO = remember { ProdutoService(ProdutoDAO(SupabaseClientProvider.supabase)) }

    val pedidoDAO: PedidoUseCase = koinInject()
    val clienteDAO: ClienteUseCase = koinInject()
    val galaoDAO: ProdutoUseCase = koinInject()
    val entregaDAO: EntregaUseCase = koinInject()

    val galoes = remember { mutableStateListOf<Produto>()}
    val pedidos = remember { mutableStateListOf<Pair<Pedido, Cliente>>() }

    var showPedidoDetalhado by remember { mutableStateOf(false)}
    var showAddEntrega by remember { mutableStateOf(false)}
    var showAddPagamento by remember { mutableStateOf(false)}


    var itemPedidoSupabase by remember { mutableStateOf(Pedido.emptyPedido()) }
    var itemClienteSupabase by remember { mutableStateOf(Cliente.emptyCliente()) }
    var itemEntrega = remember { mutableStateListOf<Entrega>() }

    LaunchedEffect(Unit) {
        val pedidoList = pedidoDAO.getAllPedidos()
        pedidos.clear()
        pedidos.addAll(pedidoList.map { Pair(it, clienteDAO.getClienteById(it.cliente_id)?: Cliente.emptyCliente()) })

        galoes.clear()
        galoes.addAll(galaoDAO.getAllProdutos())
    }

    Column (modifier = Modifier.padding(3.dp)){
        MySection(){
            H3(text = "Pedidos")
            Button(onClick = { navController.navigate("cadastrarPedidoScreen") }) {
                Text(text = "Adicionar")
            }
        }
        PedidoListComponent(pedidos){ pedido, cliente ->
            itemClienteSupabase = cliente
            itemPedidoSupabase = pedido
//            showPedidoDetalhado = true
            //TODO melhorar esse async
            coroutineScope.launch {
                itemEntrega.addAll(entregaDAO.getEntregaByPedidoId(itemPedidoSupabase.id))

            }
            navController.navigate("pedidoDetalhe/${pedido.id}")
        }

    }

    if (showPedidoDetalhado){
        MessageBoxComponent(onDismiss = { showPedidoDetalhado = false }) {
            Box(modifier = Modifier.padding(10.dp)){
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
    
    if(showAddEntrega){
        MessageBoxComponent(onDismiss = { showAddEntrega = false }) {
            CadastrarEntregaComponent(
                clientePedidoId = listOf(itemPedidoSupabase.id to itemClienteSupabase.nome),
                galoes = galoes.associate { it.id to it.nome })
            { entrega, galoes ->
                coroutineScope.launch{
                    entregaDAO.saveEntrega(entrega, galoes)

                }
                showAddEntrega = false
            }
        }
    }
    
    if(showAddPagamento){
        MessageBoxComponent(onDismiss = { showAddPagamento = false }) {
            CadastrarPagamentoComponent(clientes = listOf(itemClienteSupabase.id to itemClienteSupabase.nome)) {

            }
            
        }
    }
    

}

@Preview(showBackground = true)
@Composable
private fun PedidoScreenPreview(){
    val navController = rememberNavController()

//    PedidoScreen(navController = navController)
    Column {
        H3(text = "Pedidos")
        HorizontalDivider()
        Text(text = "asdsa")
        Button(onClick = { /*TODO*/ }) {
            
        }
    }
}