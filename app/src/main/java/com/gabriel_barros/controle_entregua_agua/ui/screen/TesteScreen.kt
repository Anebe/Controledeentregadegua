package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.domain.service.ClienteService
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarEntregaComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarPagamentoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBox
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent

@Composable
fun TesteScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val clienteDAO = remember { ClienteService(ClienteDAO(SupabaseClientProvider.supabase)) }

        var nomesClientes by remember { mutableStateOf(emptyList<Pair<Long, String>>()) }


        LaunchedEffect(Unit) {
            nomesClientes = clienteDAO.getAllClientesNomes()

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