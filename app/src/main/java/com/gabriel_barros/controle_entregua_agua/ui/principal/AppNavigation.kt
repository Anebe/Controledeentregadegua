package com.gabriel_barros.controle_entregua_agua.ui.principal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import com.gabriel_barros.controle_entregua_agua.ui.screen.HomeScreen
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen1
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen2
import com.gabriel_barros.controle_entregua_agua.ui.screen.Screen3

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("screen1") { Screen1() }
        composable("screen2") { Screen2() }
        composable("screen3") { Screen3() }
        composable("listaPedidoScreen") { ListaPedidosScreen(listOf(Pedido())) }
    }
}
