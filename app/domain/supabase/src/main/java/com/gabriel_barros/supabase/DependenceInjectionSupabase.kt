package com.gabriel_barros.supabase

import com.gabriel_barros.domain.domain.portout.ClientePortOut
import com.gabriel_barros.domain.domain.portout.EntregaPortOut
import com.gabriel_barros.domain.domain.portout.PagamentoPortOut
import com.gabriel_barros.domain.domain.portout.PedidoPortOut
import com.gabriel_barros.domain.domain.portout.ProdutoPortOut
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.ClienteSelecBuilder
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.supabase.dao.ClienteDAO
import com.gabriel_barros.supabase.dao.EntregaDao
import com.gabriel_barros.supabase.dao.PagamentoDao
import com.gabriel_barros.supabase.dao.PedidoDAO
import com.gabriel_barros.supabase.dao.ProdutoDAO
import com.gabriel_barros.supabase.dao.query.ClienteQuery
import com.gabriel_barros.supabase.dao.query.EntregaQuery
import com.gabriel_barros.supabase.dao.query.ItemEntregaQuery
import com.gabriel_barros.supabase.dao.query.ItemPedidoQuery
import com.gabriel_barros.supabase.dao.query.PagamentoQuery
import com.gabriel_barros.supabase.dao.query.PedidoQuery
import com.gabriel_barros.supabase.dao.query.ProdutoQuery
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.dsl.module


val dependenceInjectionSupabase = module {
//    single { SupabaseClientProvider.supabase }
    single {
        createSupabaseClient(
            supabaseUrl = Supabase.URL,
            supabaseKey = Supabase.KEY
        ) {
            install(Auth)
            install(Postgrest)
        }
    }
    factory<ClientePortOut> { ClienteDAO(get()) }
    factory<PedidoPortOut> { PedidoDAO(get()) }
    factory<ProdutoPortOut> { ProdutoDAO(get()) }
    factory<EntregaPortOut> { EntregaDao() }
    factory<PagamentoPortOut> { PagamentoDao() }

    factory<ProdutoQueryBuilder> { ProdutoQuery() }
    factory<PedidoQueryBuilder> { PedidoQuery() }
    factory<ItemPedidoQueryBuilder> { ItemPedidoQuery() }
    factory<PagamentoQueryBuilder> { PagamentoQuery() }
    factory<EntregaQueryBuilder> { EntregaQuery() }
    factory<ItemEntregaQueryBuilder> { ItemEntregaQuery() }
    factory<ClienteFilterBuilder> { ClienteQuery(get()) }

    factory<ClienteSelecBuilder> { ClienteQuery(get()) }

}