package com.gabriel_barros.usecase.service

import com.gabriel_barros.domain.domain.usecase.ClienteManager
import com.gabriel_barros.domain.domain.usecase.EntregaManager
import com.gabriel_barros.domain.domain.usecase.PagamentoManager
import com.gabriel_barros.domain.domain.usecase.PedidoManager
import com.gabriel_barros.domain.domain.usecase.ProdutoManager
import org.koin.dsl.module


val dependenceInjectionUsecase = module {

    single<ProdutoManager> { ProdutoManagerImp(get()) }
    single<PedidoManager> { PedidoManagerImp(get(),get(),get(),get(),get(),get(),get(),get(),) }
    single<PagamentoManager> { PagamentoManagerImp(get(),get(),get(),get(),get(),get(),) }
    single<EntregaManager> { EntregaManagerImp(get(),get(),get(),get(),get(),get(),get(),) }
    single<ClienteManager> { ClienteManagerImp(get(),) }


}