package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
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
import org.koin.compose.koinInject

@Composable
fun PedidoDetalheScreen(navController: NavController, idPedido: Long) {

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
    PedidoItemDetalhado(
                produtos = produtos,
                pedido = pedido,
                cliente = cliente,
                entregas = entregas,
                produtosPedidos = itensPedido,
                produtosEntregues = itensEntrega,
                pagamentos = pagamento,
                onSave = {valorPago, entrega, galaoSeco ->  },
                onDelete = {},
                onAddEntrega = {},
    )
}