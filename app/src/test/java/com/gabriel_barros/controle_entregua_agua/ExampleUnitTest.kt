package com.gabriel_barros.controle_entregua_agua

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest() : KoinTest {
    init {
        startKoin{
            modules(appModule)
        }
    }
    val produtoPort: ProdutoUseCase by inject()
    val pedidoPort: PedidoUseCase by inject()
    val pagamentoPort: PagamentoUseCase by inject()
    val entregaPort: EntregaUseCase by inject()
    val clientePort: ClienteUseCase by inject()

    @Test
    fun add(){
// Criando produtos
        val produto1 = Produto.emptyProduto().copy(id = 1, preco = 10.0, nome = "Coca-Cola", custo = 5.0, estoque = 50, descricao = "Refrigerante 2L")
        val produto2 = Produto.emptyProduto().copy(id = 2, preco = 5.0, nome = "Chocolate", custo = 2.5, estoque = 30, descricao = "Chocolate ao leite")
        val produto3 = Produto.emptyProduto().copy(id = 3, preco = 20.0, nome = "Pizza", custo = 12.0, estoque = 10, descricao = "Pizza grande de calabresa")
        val produto4 = Produto.emptyProduto().copy(id = 4, preco = 8.0, nome = "Suco de Laranja", custo = 4.0, estoque = 20, descricao = "Suco natural 500ml")

        produtoPort.saveProduto(produto1)
        produtoPort.saveProduto(produto2)
        produtoPort.saveProduto(produto3)
        produtoPort.saveProduto(produto4)

// Criando clientes
        val cliente1 = Cliente(id = 1, nome = "João Silva", credito = 00.0, emptyList(), "", emptyList())
        val cliente2 = Cliente(id = 2, nome = "Maria Souza", credito = 00.0, emptyList(), "", emptyList())
        val cliente3 = Cliente(id = 3, nome = "Carlos Oliveira", credito = 0.0, emptyList(), "", emptyList())
        val cliente4 = Cliente(id = 4, nome = "Ana Pereira", credito = 00.0, emptyList(), "", emptyList())

        clientePort.saveCliente(cliente1)
        clientePort.saveCliente(cliente2)
        clientePort.saveCliente(cliente3)
        clientePort.saveCliente(cliente4)

// Criando pedidos
        val pedido1 = Pedido.emptyPedido().copy(cliente_id = 1)
        val pedido2 = Pedido.emptyPedido().copy(cliente_id = 2)
        val pedido3 = Pedido.emptyPedido().copy(cliente_id = 3)
        val pedido4 = Pedido.emptyPedido().copy(cliente_id = 4)

        val itensPedido1 = setOf(ItensPedido(id = 1, pedido_id = 1, produto_id = 1, qtd = 1))
        val itensPedido2 = setOf(ItensPedido(id = 2, pedido_id = 2, produto_id = 2, qtd = 2))
        val itensPedido3 = setOf(ItensPedido(id = 3, pedido_id = 3, produto_id = 3, qtd = 2))
        val itensPedido4 = setOf(ItensPedido(id = 4, pedido_id = 4, produto_id = 4, qtd = 1))

        pedidoPort.savePedido(pedido1, itensPedido1)
        pedidoPort.savePedido(pedido2, itensPedido2)
        pedidoPort.savePedido(pedido3, itensPedido3)
        pedidoPort.savePedido(pedido4, itensPedido4)

// Criando pagamentos
        val pagamento1 = Pagamento(id = 1, pedido_id = 1, data = LocalDate.now(), valor = 100.0, pagamento = TipoPagamento.PIX)
        val pagamento2 = Pagamento(id = 2, pedido_id = 2, data = LocalDate.now(), valor = 10.0, pagamento = TipoPagamento.DINHEIRO)
        val pagamento3 = Pagamento(id = 3, pedido_id = 3, data = LocalDate.now(), valor = 40.0, pagamento = TipoPagamento.CARTAO)
        val pagamento4 = Pagamento(id = 4, pedido_id = 4, data = LocalDate.now(), valor = 8.0, pagamento = TipoPagamento.PIX)

        pagamentoPort.savePagamento(1, pagamento1.valor, pagamento1.pagamento)
        pagamentoPort.savePagamento(2, pagamento2.valor, pagamento2.pagamento)
        pagamentoPort.savePagamento(3, pagamento3.valor, pagamento3.pagamento)
        pagamentoPort.savePagamento(4, pagamento4.valor, pagamento4.pagamento)

// Criando entregas
        val entrega1 = Entrega(id = 1, data = LocalDate.now(), pedido_id = 1)
        val entrega2 = Entrega(id = 2, data = LocalDate.now(), pedido_id = 2)
        val entrega3 = Entrega(id = 3, data = LocalDate.now(), pedido_id = 3)
        val entrega4 = Entrega(id = 4, data = LocalDate.now(), pedido_id = 4)

        val itensEntrega1 = listOf(
            ItensEntrega(entrega_id = 1, itemPedido_id = 1, qtdEntregue = 1, qtdRetornado = 0),
            ItensEntrega(entrega_id = 2, itemPedido_id = 1, qtdEntregue = 1, qtdRetornado = 0)
        )

        val itensEntrega2 = listOf(
            ItensEntrega(entrega_id = 3, itemPedido_id = 2, qtdEntregue = 1, qtdRetornado = 1),
            ItensEntrega(entrega_id = 4, itemPedido_id = 2, qtdEntregue = 1, qtdRetornado = 0)
        )

        val itensEntrega3 = listOf(
            ItensEntrega(entrega_id = 5, itemPedido_id = 3, qtdEntregue = 2, qtdRetornado = 0)
        )

        val itensEntrega4 = listOf(
            ItensEntrega(entrega_id = 6, itemPedido_id = 4, qtdEntregue = 1, qtdRetornado = 0)
        )


        entregaPort.saveEntrega(entrega1,itensEntrega1)
        entregaPort.saveEntrega(entrega2,itensEntrega2)
        entregaPort.saveEntrega(entrega3,itensEntrega3)
        entregaPort.saveEntrega(entrega4,itensEntrega4)

    }

    @Test
    fun delete(){
        produtoPort.getAllProdutos().forEach {
            produtoPort.deleteProduto(it.id)
        }
        pedidoPort.getAllPedidos().forEach {
            pedidoPort.deletePedido(it.id)
        }
        pagamentoPort.getAllPagamentos().forEach {
            pagamentoPort.deletePagamento(it.id)
        }
        entregaPort.getAllEntregas().forEach {
            entregaPort.deleteEntrega(it.id)
        }
        clientePort.getAllClientes().forEach {
            clientePort.deleteCliente(it.id)
        }
    }
}
