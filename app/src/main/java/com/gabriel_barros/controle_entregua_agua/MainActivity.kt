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
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.principal.ListaPedidosScreen
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable


val supabase = createSupabaseClient(
    supabaseUrl = "https://vfqisgektavejjzqlayg.supabase.co",
    supabaseKey = BuildConfig.SUPABASE_KEY
){
    install(Auth)
    install(Postgrest)
    //install other modules
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            supabase.auth.signInAnonymously()
        }
        enableEdgeToEdge()
        setContent {
            principal()
        }
    }
}

@Composable
fun principal(){
    ControleDeEntregaDeÁguaTheme {
        Scaffold() {
            Box(Modifier.padding(it)){
                //PedidoList()
                //formCadastro()
                //CadastroPedido()
//                val navController = rememberNavController()
//                AppNavigation(navController = navController)
//                list()
                ListaPedidosScreen(pedidos = listOf(Pedido()))
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Preview() {
    principal()
}

@Serializable
data class Teste(val id: Int, val nome: String)

@Composable
fun list(){
    val notes = remember { mutableStateListOf<Teste>()}
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO){
            val results = supabase.from("teste").select().decodeList<Teste>()
            notes.addAll(results)
        }
    }
    LazyColumn {
        items(notes){
            note -> ListItem(headlineContent = { Text(note.nome) })
        }
    }
}