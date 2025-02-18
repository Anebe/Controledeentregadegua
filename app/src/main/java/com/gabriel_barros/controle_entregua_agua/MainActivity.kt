package com.gabriel_barros.controle_entregua_agua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.EntregaDao
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PagamentoDao
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PedidoDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ProdutoDAO
import com.gabriel_barros.controle_entregua_agua.domain.portout.ClientePortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.EntregaPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PagamentoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.PedidoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.portout.ProdutoPortOut
import com.gabriel_barros.controle_entregua_agua.domain.service.ClienteService
import com.gabriel_barros.controle_entregua_agua.domain.service.EntregaService
import com.gabriel_barros.controle_entregua_agua.domain.service.PagametoService
import com.gabriel_barros.controle_entregua_agua.domain.service.PedidoService
import com.gabriel_barros.controle_entregua_agua.domain.service.ProdutoService
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.EntregaUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PagamentoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.PedidoUseCase
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ProdutoUseCase
import com.gabriel_barros.controle_entregua_agua.ui.AppNavigation
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { SupabaseClientProvider.supabase }
    single<ClientePortOut> { ClienteDAO(get()) }
    single<ClienteUseCase> { ClienteService(get()) }

    single<PedidoPortOut> { PedidoDAO(get()) }
    single<PedidoUseCase> { PedidoService(get()) }

    single<ProdutoPortOut> { ProdutoDAO(get()) }
    single<ProdutoUseCase> { ProdutoService(get()) }

    single<EntregaPortOut> { EntregaDao(get()) }
    single<EntregaUseCase> { EntregaService(get()) }

    single<PagamentoPortOut> { PagamentoDao(get()) }
    single<PagamentoUseCase> { PagametoService(get()) }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            SupabaseClientProvider.supabase.auth.signInAnonymously()
        }

        startKoin{
            androidContext(this@MainActivity.applicationContext)
            modules(appModule)
        }

        enableEdgeToEdge()
        setContent {
            principal()
        }
    }
}

@Composable
fun principal(){
    ControleDeEntregaDeAguaTheme {
        Scaffold() {
            Box(Modifier.padding(it)){
                //PedidoList()
                //formCadastro()
                //CadastroPedido()
                val navController = rememberNavController()
                AppNavigation(navController = navController)
//                list()
//                ListaPedidosScreen(pedidos = listOf(Pedido()))
                //ComboBoxComponent(names) {}

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    principal()
}
