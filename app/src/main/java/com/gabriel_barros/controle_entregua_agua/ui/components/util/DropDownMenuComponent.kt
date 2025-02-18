package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun <Key, Value> DropDownMenuComponent(
    itens: Map<Key, Value>,
    label: String = "",
    onSelected: (Pair<Key, Value>) -> Unit){
    var tipoEntregadorShow by remember { mutableStateOf(false) }

    Column {
        Button(
            onClick = { tipoEntregadorShow = true }) {
            Text(label)
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Escolher Entregador")
        }
        DropdownMenu(expanded = tipoEntregadorShow,
            onDismissRequest = { tipoEntregadorShow = false }) {

            itens.entries.forEach { item ->
                DropdownMenuItem(text = { Text(text = item.value.toString()) },
                    onClick = {
                        tipoEntregadorShow = false
                        onSelected(item.toPair()) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropDownMenuComponentPreview(){
    DropDownMenuComponent(itens = mapOf(1 to "asd")) {
        
    }
}