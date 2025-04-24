package com.gabriel_barros.controle_entregua_agua

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.EntregaDao
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PagamentoDao
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PedidoDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ProdutoDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.ClienteQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.EntregaQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.ItemEntregaQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.ItemPedidoQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.PagamentoQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.PedidoQuery
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.query.ProdutoQuery
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ClienteSelecBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.controle_entregua_agua.domain.service.ClienteManagerImp
import com.gabriel_barros.controle_entregua_agua.domain.service.PagamentoManagerImp
import com.gabriel_barros.controle_entregua_agua.domain.service.PedidoManagerImp
import com.gabriel_barros.controle_entregua_agua.domain.service.deprecated.EntregaManagerImp
import com.gabriel_barros.controle_entregua_agua.domain.service.deprecated.ProdutoManagerImp
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoManager
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoManager
import org.koin.dsl.module

val dependenceInjection = module {
    single { SupabaseClientProvider.supabase }

    single<ClientePortOut> { ClienteDAO() }
//    single<ClienteUseCase> { ClienteManagerImp(get()) }

    single<PedidoPortOut> { PedidoDAO() }
//    single<PedidoUseCase> { PedidoManagerImp(get(), get(), lazy { get() }, lazy { get() }) }

    single<ProdutoPortOut> { ProdutoDAO() }
//    single<ProdutoUseCase> { ProdutoManagerImp(get()) }

    single<EntregaPortOut> { EntregaDao() }
//    single<EntregaUseCase> { EntregaManagerImp(get(),get(), get()) }

    single<PagamentoPortOut> { PagamentoDao() }
//    single<PagamentoUseCase> { PagamentoManagerImp(get(), get(), get()) }

    single<ProdutoManager> { ProdutoManagerImp(get()) }
    single<PedidoManager> { PedidoManagerImp(get(),get(),get(),get(),get(),get(),get(),get(),) }
    single<PagamentoManager> { PagamentoManagerImp(get(),get(),get(),get(),get(),get(),) }
    single<EntregaManager> { EntregaManagerImp(get(),get(),get(),get(),get(),get(),get(),) }
    single<ClienteManager> { ClienteManagerImp(get(),) }

    factory<ProdutoQueryBuilder> { ProdutoQuery() }
    factory<PedidoQueryBuilder> { PedidoQuery() }
    factory<ItemPedidoQueryBuilder> { ItemPedidoQuery() }
    factory<PagamentoQueryBuilder> { PagamentoQuery() }
    factory<EntregaQueryBuilder> { EntregaQuery() }
    factory<ItemEntregaQueryBuilder> { ItemEntregaQuery() }
    factory<ClienteFilterBuilder> { ClienteQuery() }

    single<ClienteSelecBuilder> { ClienteQuery() }

}