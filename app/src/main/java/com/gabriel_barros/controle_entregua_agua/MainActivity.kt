package com.gabriel_barros.controle_entregua_agua

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.ui.AppNavigation
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
//            SupabaseClientProvider.supabase.auth.signInAnonymously()
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
