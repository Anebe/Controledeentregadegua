package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.PedidoItemDetalhado
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.gabriel_barros.usecase.usecase.ClienteManager
import com.gabriel_barros.usecase.usecase.ClienteSearch
import com.gabriel_barros.usecase.usecase.DividaSearch
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.FreteSearch
import com.gabriel_barros.usecase.usecase.PagamentoManager
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.gabriel_barros.usecase.usecase.PedidoSearch
import com.gabriel_barros.usecase.usecase.ProdutoManager
import com.gabriel_barros.usecase.usecase.ProdutoSearch
import org.koin.compose.koinInject

@Composable
fun PedidoDetalheScreen(navController: NavController, idPedido: Long) {
    val corroutines = rememberCoroutineScope()
    val pedidoDAO: PedidoManager = koinInject()
    val clienteDAO: ClienteManager = koinInject()
    val galaoDAO: ProdutoManager = koinInject()
    val entregaDAO: EntregaManager = koinInject()
    val pagamentoDAO: PagamentoManager = koinInject()
    val produtoDAO: ProdutoManager = koinInject()

    val clienteSearch: ClienteSearch = koinInject()
    val produtoSearch: ProdutoSearch = koinInject()
    val pedidoSearch: PedidoSearch = koinInject()
    val freteSearch: FreteSearch = koinInject()
    val dividaSearch: DividaSearch = koinInject()

//    val itensPedido = remember { mutableStateListOf<ItensPedido>() }
//    var itensEntrega = remember { mutableStateListOf<ItensEntrega>() }
//    var pagamento = remember { mutableStateListOf<PagamentoEntity>() }
//    var pedido by remember { mutableStateOf<PedidoOutput>() }
//    var cliente by remember { mutableStateOf(Cliente.emptyCliente()) }
//    var entregas = remember { mutableStateListOf<EntregaEntity>() }
//    var produtos = remember { mutableStateListOf<ProdutoEntity>() }

    LaunchedEffect(Unit) {
//        pedido = pedidoSearch.getPedidoById(idPedido)
//        itensPedido.addAll(itemPedidoSearch.getAllItensByPedidoId(pedido.id).buildExecuteAsSList())
//        entregas.addAll(freteSearch.getAllEntregasByPedido(pedido.id).buildExecuteAsSList())
//        entregas.forEach {
//            itensEntrega.addAll(
//                itemEntregaSearch.getAllItensByEntregaId(it.id).buildExecuteAsSList()
//            )
//        }
//        cliente = clienteSearch.getClienteById(pedido.id).buildExecuteAsSingle()
//        pagamento.addAll(dividaSearch.getPagamentosByPedido(pedido.id).buildExecuteAsSList())
//        //TODO
//        val idProdutos = itensPedido.map { it.produto_id }
//        val produtosNull = idProdutos.map { produtoSearch.getProdutoById(it).buildExecuteAsSingle() }
//        produtos.addAll(produtosNull)
    }
    Column {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest)) {
            H3(text = "Detalhes do Pedido")

        }
        HorizontalDivider()
        PedidoItemDetalhado(pedidoId = idPedido)
//        PedidoItemDetalhado(
//            produtos = produtos,
//            pedido = pedido,
//            cliente = cliente,
//            entregas = entregas,
//            produtosPedidos = itensPedido,
//            produtosEntregues = itensEntrega,
//            pagamentos = pagamento,
//            onSave = { valorPago, entrega, galaoSeco -> },
//            onDelete = {},
//            onAddEntrega = {},
//            onSaveEntrega = { entregaForSave, itensEntregaForSave ->
//                corroutines.launch {
//                    val newEntrega = entregaDAO.registerEntrega(entregaForSave, itensEntregaForSave)
//                    entregas.add(newEntrega)
//                    itensEntrega.addAll(
//                        itemEntregaSearch.getAllItensByEntregaId(newEntrega.id).buildExecuteAsSList()
//                    )
//
//                }
//            },
//            onSavePagamento = { clienteId, valor, tipoPagamento ->
//                corroutines.launch {
//                    val newPagamento = pagamentoDAO.processPagamento(
//                        clienteId,
//                        PagamentoManager.PagamentoDTO(valor, tipoPagamento)
//                    )
//                    pagamento.addAll(newPagamento)
//
//
//                }
//            }
//        )

    }
}

@Preview(showBackground = true)
@Composable
private fun preview() {
    val navController = rememberNavController()
    ControleDeEntregaDeAguaTheme {
        PedidoDetalheScreen(navController = navController, idPedido = 0)
    }
}