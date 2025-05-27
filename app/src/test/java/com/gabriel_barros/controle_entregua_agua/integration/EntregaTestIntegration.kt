package com.gabriel_barros.controle_entregua_agua.integration

import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.ioc.dependenceInjectionPrincipal
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class EntregaTestIntegration: KoinTest {
    init {
        stopKoin()
        startKoin{
            modules(dependenceInjectionPrincipal)
        }
    }
    val entregaManager: EntregaManager by inject()


    @Test
    fun deveSalvarNoBancoCorrentamente(){
        val entrega = EntregaManager.EntregaRegister(
            entregaId = 1L,
            produtosEntregues = setOf(
                EntregaManager.ProdutoEntregue(
                    produtoPedidoId = 101L,
                    qtdEntregue = 5,
                    qtdRetornavel = 2
                ),
                EntregaManager.ProdutoEntregue(
                    produtoPedidoId = 102L,
                    qtdEntregue = 3,
                    qtdRetornavel = 1
                )
            )
        )

    }
}