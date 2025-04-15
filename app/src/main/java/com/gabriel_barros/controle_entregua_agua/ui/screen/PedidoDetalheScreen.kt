package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.PedidoItemDetalhado
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun PedidoDetalheScreen(navController: NavController, idPedido: Long) {
    val corroutines = rememberCoroutineScope()
    val pedidoDAO: PedidoUseCase = koinInject()
    val clienteDAO: ClienteUseCase = koinInject()
    val galaoDAO: ProdutoUseCase = koinInject()
    val entregaDAO: EntregaUseCase = koinInject()
    val pagamentoDAO: PagamentoUseCase = koinInject()
    val produtoDAO: ProdutoUseCase = koinInject()

    val itensPedido = remember { mutableStateListOf<ItensPedido>() }
    var itensEntrega = remember { mutableStateListOf<ItensEntrega>() }
    var pagamento = remember { mutableStateListOf<Pagamento>() }
    var pedido by remember { mutableStateOf(Pedido.emptyPedido()) }
    var cliente by remember { mutableStateOf(Cliente.emptyCliente()) }
    var entregas = remember { mutableStateListOf<Entrega>() }
    var produtos = remember { mutableStateListOf<Produto>() }

    LaunchedEffect(Unit) {
        pedido = pedidoDAO.getPedidoById(idPedido)?: Pedido.emptyPedido()
        itensPedido.addAll(pedidoDAO.getAllItensByPedidoId(pedido.id))
        entregas.addAll(entregaDAO.getAllEntregasByPedido(pedido.id))
        entregas.forEach {
            itensEntrega.addAll(entregaDAO.getAllItensByEntregaId(it.id))
        }
        cliente = clienteDAO.getClienteById(pedido.id) ?: Cliente.emptyCliente()
        pagamento.addAll(pagamentoDAO.getPagamentosByPedido(pedido.id))
        //TODO
        val idProdutos = itensPedido.map { it.produto_id }
        val produtosNull = idProdutos.mapNotNull { produtoDAO.getProdutoById(it) }
        produtos.addAll(produtosNull)
    }
    Column {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest)){
            H3(text = "Detalhes do Pedido")

        }
        HorizontalDivider()
        PedidoItemDetalhado(
            produtos = produtos,
            pedido = pedido,
            cliente = cliente,
            entregas = entregas,
            produtosPedidos = itensPedido,
            produtosEntregues = itensEntrega,
            pagamentos = pagamento,
            onSave = { valorPago, entrega, galaoSeco -> },
            onDelete = {},
            onAddEntrega = {},
            onSaveEntrega = { entregaForSave, itensEntregaForSave ->
                corroutines.launch {
                    val newEntrega = entregaDAO.saveEntrega(entregaForSave, itensEntregaForSave)
                    if (newEntrega != null){
                        entregas.add(newEntrega)
                        itensEntrega.addAll(entregaDAO.getAllItensByEntregaId(newEntrega.id))
                    }
                }
            },
            onSavePagamento = {clienteId, valor, tipoPagamento ->
                corroutines.launch {
                    val newPagamento = pagamentoDAO.savePagamento(clienteId, valor, tipoPagamento)
                    if(newPagamento != null){
                        pagamento.addAll(newPagamento)

                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun preview(){
    val navController = rememberNavController()
    ControleDeEntregaDeAguaTheme {
        PedidoDetalheScreen(navController = navController, idPedido = 0)
    }
}