package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.names


@Composable
fun ComboBoxComponent( itens: Map<String,Long>, onChange: (Pair<String, Long>) -> Unit){
    val focusManager = LocalFocusManager.current
    var expandedClientes by remember { mutableStateOf(false) }
    val choosenItem = remember { mutableStateOf(Pair("", 0L)) }
    val filteredItems = remember(choosenItem.value.first)  {
        itens.filter { it.key.contains(choosenItem.value.first, ignoreCase = true) }
            .entries
            .take(3)
            .toList()
    }

    Column (
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ){
        TextField(
            value = choosenItem.value.first,
            onValueChange = { newValue:String ->
                if(!expandedClientes) expandedClientes = true
                choosenItem.value = Pair(newValue, -1)
                val id = names[newValue]
                id?.let {
                    choosenItem.value = Pair(newValue, id)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
            }),
            label = { Text("Cliente") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    if (!it.hasFocus) {
                        expandedClientes = false
                    }
                }
                .clickable { expandedClientes = true },
        )
        if(expandedClientes){
            Row {
                filteredItems.forEach { item ->
                    Card(
                        onClick = {
                            choosenItem.value = item.toPair()
                            expandedClientes = false
                            onChange(choosenItem.value)
                        },modifier = Modifier.padding(horizontal = 3.dp)
                    ){
                        Text(item.key, modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }

}
