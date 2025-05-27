package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.ui.components.cliente.ClienteListComponent
import com.gabriel_barros.usecase.usecase.ClienteSearch
import org.koin.compose.koinInject

@Composable
fun ClienteScreen(navController: NavController){
//    val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }
    val clienteDAO: ClienteSearch = koinInject()

    val clientes = remember { mutableMapOf<Long, String>() }

    LaunchedEffect(Unit) {
        clientes.clear()
        clientes.putAll(clienteDAO.getAllClientesNomes())
    }
    Column {
        ClienteListComponent(strings = clientes.map { it.value })

        Button(onClick = { navController.navigate("cadastrarClienteScreen") }) {
            Text(text = "Adicionar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClienteScreenPreview(){
    ClienteScreen(rememberNavController())
}