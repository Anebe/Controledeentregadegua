package com.gabriel_barros.depence_injection

import com.gabriel_barros.data_supabase.dependenceInjectionSupabase
import com.gabriel_barros.domain.error.NotFoundException
import com.gabriel_barros.usecase.dependenceInjectionUsecase
import com.gabriel_barros.usecase.dto.PedidoInput
import com.gabriel_barros.usecase.dto.ProdutoPedidoInput
import com.gabriel_barros.usecase.usecase.ClienteManager
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.gabriel_barros.usecase.usecase.PedidoSearch
import com.gabriel_barros.usecase.usecase.ProdutoManager
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PedidoManagerImpTest : KoinTest {

    val pedidoManager by inject<PedidoManager>()
    val clienteManager by inject<ClienteManager>()
    val produtoManager by inject<ProdutoManager>()
    val pedidoSearch by inject<PedidoSearch>()


    @BeforeEach
    fun setUp() {
        startKoin {
            modules(
                dependenceInjectionSupabase,
                dependenceInjectionUsecase
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun must_save_read_delete() {
        runBlocking {
            val cliente = clienteManager.registerCliente(clienteMock)
            val produto = produtoManager.registerProduto(produtoMock)

            val savePedido = pedidoManager.makePedido(
                PedidoInput(
                    clienteId = cliente.id,
                    produtos = setOf(
                        ProdutoPedidoInput(
                            qtd = 10,
                            produtoId = produto.id
                        )
                    )
                )
            )
            assertTrue { savePedido.id > 0 }

            val readPedido = pedidoSearch.getPedidoById(savePedido.id)
            assertEquals(savePedido,readPedido)

            val deletePedido = pedidoManager.delete(savePedido.id)
            assertThrows<NotFoundException> {
                pedidoSearch.getPedidoById(readPedido.id)
            }
            produtoManager.delete(produto.id)
            clienteManager.deleteCliente(cliente.id)

        }
    }
}