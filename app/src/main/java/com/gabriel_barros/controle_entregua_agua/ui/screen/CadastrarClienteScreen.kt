package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarClienteComponent
import kotlinx.coroutines.launch

@Composable
fun CadastrarClienteScreen(navController: NavController) {
    val clienteDAO = remember { ClienteDAO(SupabaseClientProvider.supabase) }
    val coroutineScope = rememberCoroutineScope()

    CadastrarClienteComponent { cliente ->
        coroutineScope.launch {
            val newCliente = clienteDAO.insertCliente(cliente)
            newCliente?.let {
                navController.popBackStack()
            }
        }
    }
}