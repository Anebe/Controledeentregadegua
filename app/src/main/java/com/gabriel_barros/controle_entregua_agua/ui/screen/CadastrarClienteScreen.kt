package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarClienteComponent
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun CadastrarClienteScreen(navController: NavController) {
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
    val clienteDAO: ClienteManager = koinInject()
    val coroutineScope = rememberCoroutineScope()

    CadastrarClienteComponent { cliente ->
        coroutineScope.launch {
            val newCliente = clienteDAO.registerCliente(cliente)
            newCliente.let {
                navController.popBackStack()
            }
        }
    }
}