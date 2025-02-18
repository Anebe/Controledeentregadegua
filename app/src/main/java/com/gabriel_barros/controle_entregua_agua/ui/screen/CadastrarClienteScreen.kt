package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.domain.service.ClienteService
import com.gabriel_barros.controle_entregua_agua.domain.usecase.ClienteUseCase
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarClienteComponent
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun CadastrarClienteScreen(navController: NavController) {
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
    val clienteDAO:ClienteUseCase = koinInject()
    val coroutineScope = rememberCoroutineScope()

    CadastrarClienteComponent { cliente ->
        coroutineScope.launch {
            val newCliente = clienteDAO.saveCliente(cliente)
            newCliente?.let {
                navController.popBackStack()
            }
        }
    }
}