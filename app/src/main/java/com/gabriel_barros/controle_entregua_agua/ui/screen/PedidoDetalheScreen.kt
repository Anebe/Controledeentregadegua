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
import com.gabriel_barros.controle_entregua_agua.ui.components.pedido.PedidoItemDetalhado
import com.gabriel_barros.controle_entregua_agua.ui.components.util.H3
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega
import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PagamentoEntity
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.PagamentoManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import kotlinx.coroutines.launch
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

    val clienteQuery: ClienteFilterBuilder = koinInject()
    val produtoQuery: ProdutoQueryBuilder = koinInject()
    val pedidoQuery: PedidoQueryBuilder = koinInject()
    val itemPedidoQuery: ItemPedidoQueryBuilder = koinInject()
    val entregaQuery: EntregaQueryBuilder = koinInject()
    val itemEntregaQuery: ItemEntregaQueryBuilder = koinInject()
    val pagamentoQuery: PagamentoQueryBuilder = koinInject()

    val itensPedido = remember { mutableStateListOf<ItensPedido>() }
    var itensEntrega = remember { mutableStateListOf<ItensEntrega>() }
    var pagamento = remember { mutableStateListOf<PagamentoEntity>() }
    var pedido by remember { mutableStateOf(PedidoEntity.emptyPedido()) }
    var cliente by remember { mutableStateOf(Cliente.emptyCliente()) }
    var entregas = remember { mutableStateListOf<Entrega>() }
    var produtos = remember { mutableStateListOf<ProdutoEntity>() }

    LaunchedEffect(Unit) {
        pedido = pedidoQuery.getPedidoById(idPedido).buildExecuteAsSingle()
        itensPedido.addAll(itemPedidoQuery.getAllItensByPedidoId(pedido.id).buildExecuteAsSList())
        entregas.addAll(entregaQuery.getAllEntregasByPedido(pedido.id).buildExecuteAsSList())
        entregas.forEach {
            itensEntrega.addAll(
                itemEntregaQuery.getAllItensByEntregaId(it.id).buildExecuteAsSList()
            )
        }
        cliente = clienteQuery.getClienteById(pedido.id).buildExecuteAsSingle()
        pagamento.addAll(pagamentoQuery.getPagamentosByPedido(pedido.id).buildExecuteAsSList())
        //TODO
        val idProdutos = itensPedido.map { it.produto_id }
        val produtosNull = idProdutos.map { produtoQuery.getProdutoById(it).buildExecuteAsSingle() }
        produtos.addAll(produtosNull)
    }
    Column {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHighest)) {
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
                    val newEntrega = entregaDAO.registerEntrega(entregaForSave, itensEntregaForSave)
                    entregas.add(newEntrega)
                    itensEntrega.addAll(
                        itemEntregaQuery.getAllItensByEntregaId(newEntrega.id).buildExecuteAsSList()
                    )

                }
            },
            onSavePagamento = { clienteId, valor, tipoPagamento ->
                corroutines.launch {
                    val newPagamento = pagamentoDAO.processPagamento(
                        clienteId,
                        PagamentoManager.PagamentoDTO(valor, tipoPagamento)
                    )
                    pagamento.addAll(newPagamento)


                }
            }
        )
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