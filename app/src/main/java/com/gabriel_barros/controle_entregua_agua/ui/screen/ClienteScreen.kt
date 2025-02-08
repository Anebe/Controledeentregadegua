package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarClienteComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.ClienteListComponent
import kotlinx.coroutines.launch

@Composable
fun ClienteScreen(navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    val clienteDAO = remember { ClienteDAO(SupabaseClientProvider.supabase) }
    val clientes = remember { mutableStateListOf<String>() }

    LaunchedEffect(Unit) {
        val clienteList = clienteDAO.getAllClientes() // Método fictício para buscar clientes
        clientes.clear()
        clientes.addAll(clienteList.map { it.id.toString() }.toList())
    }
    Column {
        ClienteListComponent(strings = clientes)
//        CadastrarClienteComponent { cliente ->
//            coroutineScope.launch {
//                val newCliente = clienteDAO.insertCliente(cliente)
//                newCliente?.let {
//                    clientes.add(newCliente.id.toString())
//                }
//            }
//        }
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