package com.gabriel_barros.controle_entregua_agua.ui.principal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.ViewModelProvider
import androidx.activity.viewModels

@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Tela Inicial", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("screen1") }) {
            Text(text = "Ir para Tela 1")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("screen2") }) {
            Text(text = "Ir para Tela 2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("screen3") }) {
            Text(text = "Ir para Tela 3")
        }
    }
}

@Composable
fun Screen1() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tela 1", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun Screen2() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tela 2", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun Screen3() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Tela 3", style = MaterialTheme.typography.headlineMedium)
    }
}
