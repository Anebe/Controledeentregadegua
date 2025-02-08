package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.PedidoDAO
import com.gabriel_barros.controle_entregua_agua.ui.components.PedidoListComponent

@Composable
fun PedidoScreen(navController: NavController) {

    val pedidoDAO = remember { PedidoDAO(SupabaseClientProvider.supabase) }
    val pedidos = remember { mutableStateListOf<com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Pedido>() }

    LaunchedEffect(Unit) {
        val pedidoList = pedidoDAO.getAllPedidos()
        pedidos.clear()
        pedidos.addAll(pedidoList)
    }

    Column {
        PedidoListComponent(pedidos)
        Button(onClick = { navController.navigate("cadastrarPedidoScreen") }) {
            Text(text = "Adicionar")
        }
    }
}