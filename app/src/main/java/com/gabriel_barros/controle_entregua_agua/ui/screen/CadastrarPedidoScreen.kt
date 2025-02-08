package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PedidoDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastroPedidoComponent
import kotlinx.coroutines.launch
import java.time.LocalDate



@Composable
fun CadastrarPedidoScreen (navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val pedidoDAO = remember { PedidoDAO(SupabaseClientProvider.supabase) }
    val clienteDAO = remember { ClienteDAO(SupabaseClientProvider.supabase) }

    var nomesClientes by remember { mutableStateOf(emptyMap<String,Long>()) }
    
    LaunchedEffect(Unit) {
        nomesClientes = clienteDAO.getAllClientesNomes()
    }
    CadastroPedidoComponent(nomesClientes) {pedido ->
        coroutineScope.launch {
            val newCliente = pedidoDAO.insertPedido(pedido)
            newCliente?.let {
                navController.popBackStack()
            }
        }
    }
}