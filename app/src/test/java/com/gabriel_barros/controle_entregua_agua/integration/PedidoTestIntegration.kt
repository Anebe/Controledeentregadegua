package com.gabriel_barros.controle_entregua_agua.integration

import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager.ItemDoPedidoInput
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import com.gabriel_barros.ioc.dependenceInjectionPrincipal
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.time.LocalDate

class PedidoTestIntegration: KoinTest {
    init {
        stopKoin()
        startKoin{
            modules(dependenceInjectionPrincipal)
        }
    }
    val pedidoManager: PedidoManager by inject()
    val produtoManager: ProdutoManager by inject()

    @Test
    fun deveSalvarNoBancoCorretamente() = runTest{
        val produto = ProdutoEntity(
            id = 1L,
            preco = 10.99,
            nome = "Produto Exemplo",
            custo = 5.50,
            estoque = 100,
            reservado = 20,
            descricao = "Produto de exemplo para teste"
        )

        produtoManager.registerProduto(produto)

        val pedido = PedidoManager.PedidoInput(
            clienteId = 123L,
            itensDoPedido = setOf(
                ItemDoPedidoInput(
                    qtd = 3,
                    produtoId = produto.id
                )
            ),
            dataAgendadaParaEntrega = LocalDate.of(2025, 5, 15) // ou use LocalDate.now()
        )

        pedidoManager.makePedido(pedido)
    }
}