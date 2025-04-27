package com.gabriel_barros.controle_entregua_agua

import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.ClienteSelecBuilder
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.PagamentoManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import com.gabriel_barros.ioc.dependenceInjectionPrincipal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest() : KoinTest {
    init {
        stopKoin()
        startKoin{
            modules(dependenceInjectionPrincipal)
        }
    }
    val produtoPort: ProdutoManager by inject()
    val pedidoPort: PedidoManager by inject()
    val pagamentoPort: PagamentoManager by inject()
    val entregaPort: EntregaManager by inject()
    val clientePort: ClienteManager by inject()

    val produtoQuery: ProdutoQueryBuilder by inject()
    val pedidoQuery: PedidoQueryBuilder by inject()
    val itenspedidosQuery: ItemPedidoQueryBuilder by inject()
    val pagamentoQuery: PagamentoQueryBuilder by inject()
    val entregaQuery: EntregaQueryBuilder by inject()
    val clienteFilter: ClienteFilterBuilder by inject()
    val clienteQuery: ClienteSelecBuilder by inject()




    @Test
    fun bbbbbbb() = runTest{
        println(pagamentoQuery
            .getPagamentosByPedido(2)
            .buildExecuteAsSList())
    }
    @Test
    fun add() = runTest {
    // Criando produtos
        val produto1 = ProdutoEntity(id = 1, preco = 10.0, nome = "Coca-Cola", custo = 5.0, estoque = 50, descricao = "Refrigerante 2L", reservado = 0)
        val produto2 = ProdutoEntity(id = 2, preco = 5.0, nome = "Chocolate", custo = 2.5, estoque = 30, descricao = "Chocolate ao leite", reservado = 0)
        val produto3 = ProdutoEntity(id = 3, preco = 20.0, nome = "Pizza", custo = 12.0, estoque = 10, descricao = "Pizza grande de calabresa", reservado = 0)
        val produto4 = ProdutoEntity(id = 4, preco = 8.0, nome = "Suco de Laranja", custo = 4.0, estoque = 20, descricao = "Suco natural 500ml", reservado = 0)

        produtoPort.registerProduto(produto1)
        produtoPort.registerProduto(produto2)
        produtoPort.registerProduto(produto3)
        produtoPort.registerProduto(produto4)
//
//// Criando clientes
        val cliente1 = Cliente(id = 1, nome = "João Silva", credito = 00.0, emptyList(), "", )
        val cliente2 = Cliente(id = 2, nome = "Maria Souza", credito = 00.0, emptyList(), "", )
        val cliente3 = Cliente(id = 3, nome = "Carlos Oliveira", credito = 0.0, emptyList(), "", )
        val cliente4 = Cliente(id = 4, nome = "Ana Pereira", credito = 00.0, emptyList(), "", )

        clientePort.registerCliente(cliente1)
        clientePort.registerCliente(cliente2)
        clientePort.registerCliente(cliente3)
        clientePort.registerCliente(cliente4)

// Criando pedidos

//        val itensPedido1 = setOf(PedidoManager.ItemDoPedidoInput(produtoId = 1, qtd = 1))
//        val itensPedido2 = setOf(PedidoManager.ItemDoPedidoInput(produtoId = 2, qtd = 2))
//        val itensPedido3 = setOf(PedidoManager.ItemDoPedidoInput(produtoId = 3, qtd = 2))
//        val itensPedido4 = setOf(PedidoManager.ItemDoPedidoInput(produtoId = 4, qtd = 1))
//
//        val pedido1 = PedidoManager.PedidoInput(clienteId = 1, itensPedido1)
//        val pedido2 = PedidoManager.PedidoInput(clienteId = 2, itensPedido2)
//        val pedido3 = PedidoManager.PedidoInput(clienteId = 3, itensPedido3)
//        val pedido4 = PedidoManager.PedidoInput(clienteId = 4, itensPedido4)
//
////        pedidoPort.makePedido(pedido1)
////        pedidoPort.makePedido(pedido2)
//        pedidoPort.makePedido(pedido3)
//        pedidoPort.makePedido(pedido4)
//
//// Criando pagamentos
//        val pagamento1 = Pagamento(id = 1, pedido_id = 1, data = LocalDate.now(), valor = 100.0, pagamento = TipoPagamento.PIX)
//        val pagamento2 = Pagamento(id = 2, pedido_id = 2, data = LocalDate.now(), valor = 10.0, pagamento = TipoPagamento.DINHEIRO)
//        val pagamento3 = Pagamento(id = 3, pedido_id = 3, data = LocalDate.now(), valor = 40.0, pagamento = TipoPagamento.CARTAO)
//        val pagamento4 = Pagamento(id = 4, pedido_id = 4, data = LocalDate.now(), valor = 8.0, pagamento = TipoPagamento.PIX)
//
//        pagamentoPort.processPagamento(1, PagamentoManager.PagamentoDTO(pagamento1.valor, pagamento1.pagamento))
//        pagamentoPort.processPagamento(2, PagamentoManager.PagamentoDTO(pagamento2.valor, pagamento2.pagamento))
//        pagamentoPort.processPagamento(3, PagamentoManager.PagamentoDTO(pagamento3.valor, pagamento3.pagamento))
//        pagamentoPort.processPagamento(4, PagamentoManager.PagamentoDTO(pagamento4.valor, pagamento4.pagamento))
//
//// Criando entregas
//        val entrega1 = Entrega(id = 1, data = LocalDate.now(), pedido_id = 1)
//        val entrega2 = Entrega(id = 2, data = LocalDate.now(), pedido_id = 2)
//        val entrega3 = Entrega(id = 3, data = LocalDate.now(), pedido_id = 3)
//        val entrega4 = Entrega(id = 4, data = LocalDate.now(), pedido_id = 4)
//
//        val itensEntrega1 = listOf(
//            ItensEntrega(entrega_id = 1, itemPedido_id = 1, qtdEntregue = 1, qtdRetornado = 0),
//            ItensEntrega(entrega_id = 2, itemPedido_id = 1, qtdEntregue = 1, qtdRetornado = 0)
//        )
//
//        val itensEntrega2 = listOf(
//            ItensEntrega(entrega_id = 3, itemPedido_id = 2, qtdEntregue = 1, qtdRetornado = 1),
//            ItensEntrega(entrega_id = 4, itemPedido_id = 2, qtdEntregue = 1, qtdRetornado = 0)
//        )
//
//        val itensEntrega3 = listOf(
//            ItensEntrega(entrega_id = 5, itemPedido_id = 3, qtdEntregue = 2, qtdRetornado = 0)
//        )
//
//        val itensEntrega4 = listOf(
//            ItensEntrega(entrega_id = 6, itemPedido_id = 4, qtdEntregue = 1, qtdRetornado = 0)
//        )
//
//
//        entregaPort.registerEntrega(entrega1,itensEntrega1)
//        entregaPort.registerEntrega(entrega2,itensEntrega2)
//        entregaPort.registerEntrega(entrega3,itensEntrega3)
//        entregaPort.registerEntrega(entrega4,itensEntrega4)
//
    }

//    @Test
//    fun delete() = runTest {
//        produtoQuery.getAllProdutos().buildExecuteAsSList().forEach {
//            produtoPort.deleteProduto(it.id)
//        }
//        pedidoQuery.getAllPedidos().buildExecuteAsSList().forEach {
//            pedidoPort.deletePedido(it.id)
//        }
//        pagamentoQuery.getAllPagamentos().buildExecuteAsSList().forEach {
//            pagamentoPort.deletePagamento(it.id)
//        }
//        entregaQuery.getAllEntregas().buildExecuteAsSList().forEach {
//            entregaPort.deleteEntrega(it.id)
//        }
//        clienteQuery.getAllClientes().buildExecuteAsSList().forEach {
//            clientePort.deleteCliente(it.id)
//        }
//    }
}
