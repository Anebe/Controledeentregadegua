package com.gabriel_barros.controle_entregua_agua.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.gabriel_barros.controle_entregua_agua.database.room.dao.ClienteDao
import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.ui.components.CadastrarClienteComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.ComboBox
import com.gabriel_barros.controle_entregua_agua.ui.components.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.MessageBoxComponent

@Composable
fun TesteScreen() {

    val clienteDAO = remember { ClienteDAO(SupabaseClientProvider.supabase) }
    //var clientes = remember { mutableStateMapOf<String,Long>() }
    var clientes by remember { mutableStateOf(emptyMap<String,Long>()) }
    var show by remember { mutableStateOf(false) }
    var cliente by remember { mutableStateOf(Pair("",-1L)) }

    LaunchedEffect(Unit) {
        clientes = clienteDAO.getAllClientesNomes()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if(show) {
            Dialog(onDismissRequest = { /*TODO*/ }) {
                Column {
                    Text(text = clientes.toString())
                    Card {
                        
                        Text(text = cliente.toString())
                    }
                    Button(onClick = { show = false }) {
                    }

                }
            }
        }
        ComboBoxComponent(itens = clientes) {
            cliente = it
            show = true
        }
    }
}