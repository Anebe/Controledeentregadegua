package com.gabriel_barros.controle_entregua_agua.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme


@Composable
fun ComboBox(itens: Map<Int, String>, onItemSelected: (Int) -> Unit){

    var text by remember { mutableStateOf("") };
    var showMenu by remember { mutableStateOf(true) }

    val filteredItems = remember(text) {
        itens.filter { it.value.contains(text, ignoreCase = true) }
            .entries
            .take(3)
            .toList()
    }
    Column {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Cliente") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
                items(filteredItems) { item ->
                Text(
                    text = item.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onItemSelected(item.key)
                            text = item.value
                        }
                )
            }
        }

    }
}



@Preview(showBackground = true)
@Composable
fun preview(){
    ControleDeEntregaDeÁguaTheme {
        //ComboBox(names, {})

    }
}