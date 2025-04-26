package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.usecase.ClienteManager
import org.koin.compose.koinInject

@Composable
fun TesteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val clienteDAO: ClienteManager = koinInject()
        val clienteQuery: ClienteFilterBuilder = koinInject()

        var nomesClientes by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }


        LaunchedEffect(Unit) {
            nomesClientes = clienteQuery.getAllClientes().buildExecuteAsSList().map { Pair(it.id, it.nome)}

        }
        ComboBoxComponent(itens =nomesClientes ) {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun TestScreenPreview() {
    com.gabriel_barros.controle_entregua_agua.ui.screen.TesteScreen()
}