package com.gabriel_barros.data_supabase

import com.gabriel_barros.data_supabase.dao.ClienteDAO
import com.gabriel_barros.data_supabase.dao.FreteDao
import com.gabriel_barros.data_supabase.dao.PagamentoDao
import com.gabriel_barros.data_supabase.dao.PedidoDAO
import com.gabriel_barros.data_supabase.dao.ProdutoDAO
import com.gabriel_barros.data_supabase.dao.query.ClienteQuery
import com.gabriel_barros.data_supabase.dao.query.EntregaQuery
import com.gabriel_barros.data_supabase.dao.query.FreteSearchImp
import com.gabriel_barros.data_supabase.dao.query.ItemEntregaQuery
import com.gabriel_barros.data_supabase.dao.query.ItemPedidoQuery
import com.gabriel_barros.data_supabase.dao.query.PagamentoQuery
import com.gabriel_barros.data_supabase.dao.query.PedidoQuery
import com.gabriel_barros.data_supabase.dao.query.ProdutoQuery
import com.gabriel_barros.domain_portout.ClientePortOut
import com.gabriel_barros.domain_portout.FretePortOut
import com.gabriel_barros.domain_portout.FreteSearch
import com.gabriel_barros.domain_portout.PagamentoPortOut
import com.gabriel_barros.domain_portout.PedidoPortOut
import com.gabriel_barros.domain_portout.ProdutoPortOut
import com.gabriel_barros.domain_portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain_portout.query.ClienteSelecBuilder
import com.gabriel_barros.domain_portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain_portout.query.ItemEntregaQueryBuilder
import com.gabriel_barros.domain_portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain_portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain_portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain_portout.query.ProdutoQueryBuilder
import org.koin.dsl.module


val dependenceInjectionSupabase = module {
//    single { SupabaseClientProvider.supabase }

    factory<ClientePortOut> { ClienteDAO() }
    factory<PedidoPortOut> { PedidoDAO() }
    factory<ProdutoPortOut> { ProdutoDAO() }
    factory<FretePortOut> { FreteDao() }
    factory<PagamentoPortOut> { PagamentoDao() }

    factory<ProdutoQueryBuilder> { ProdutoQuery() }
    factory<PedidoQueryBuilder> { PedidoQuery() }
    factory<ItemPedidoQueryBuilder> { ItemPedidoQuery() }
    factory<PagamentoQueryBuilder> { PagamentoQuery() }
    factory<EntregaQueryBuilder> { EntregaQuery() }
    factory<ItemEntregaQueryBuilder> { ItemEntregaQuery() }
    factory<ClienteFilterBuilder> { ClienteQuery() }

    factory<ClienteSelecBuilder> { ClienteQuery() }

    factory<FreteSearch> { FreteSearchImp() }

}