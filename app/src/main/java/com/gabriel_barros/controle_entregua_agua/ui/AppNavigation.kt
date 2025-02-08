package com.gabriel_barros.controle_entregua_agua.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.screen.CadastrarClienteScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.CadastrarPedidoScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.ClienteScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.HomeScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.ListaPedidosScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.PedidoScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen1
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen2
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen3
import com.gabriel_barros.controle_entregua_agua.ui.screen.TesteScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("clienteScreen") { ClienteScreen(navController) }
        composable("cadastrarClienteScreen") { CadastrarClienteScreen(navController) }
        composable("pedidoScreen") { PedidoScreen(navController) }
        composable("cadastrarPedidoScreen") { CadastrarPedidoScreen(navController) }
        composable("testeScreen") { TesteScreen() }

    }
}
