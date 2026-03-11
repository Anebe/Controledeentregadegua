package com.gabriel_barros.usecase

import com.gabriel_barros.usecase.service.ClienteManagerImp
import com.gabriel_barros.usecase.service.EntregaManagerImp
import com.gabriel_barros.usecase.service.PagamentoManagerImp
import com.gabriel_barros.usecase.service.PedidoManagerImp
import com.gabriel_barros.usecase.service.ProdutoManagerImp
import com.gabriel_barros.usecase.usecase.ClienteManager
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.PagamentoManager
import com.gabriel_barros.usecase.usecase.PedidoManager
import com.gabriel_barros.usecase.usecase.PedidoSearch
import com.gabriel_barros.usecase.usecase.ProdutoManager
import org.koin.dsl.module


val dependenceInjectionUsecase = module {

    single<ProdutoManager> { ProdutoManagerImp(get()) }
    single<PedidoManager> {
        PedidoManagerImp(
            pedidoOut = get(),
            pedidoQuery = get(),
            itemPedidoQuery = get(),
            produtoManager = get(),
            produtoQuery = get(),
            pagamentoQuery = get(),
            entregaQuery = get(),
            itemEntregaQuery = get(),
            clienteQuery = get()
        )
    }
    single<PedidoSearch> {
        PedidoManagerImp(
            pedidoOut = get(),
            pedidoQuery = get(),
            itemPedidoQuery = get(),
            produtoManager = get(),
            produtoQuery = get(),
            pagamentoQuery = get(),
            entregaQuery = get(),
            itemEntregaQuery = get(),
            clienteQuery = get()
        )
    }
    single<PagamentoManager> { PagamentoManagerImp(get(), get(), get(), get(), get(), get(),) }
    single<EntregaManager> {
        EntregaManagerImp(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    single<ClienteManager> { ClienteManagerImp(get(),) }


}