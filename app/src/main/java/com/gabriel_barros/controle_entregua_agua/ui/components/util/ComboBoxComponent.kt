package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
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
fun ComboBoxComponent( itens: List<Pair<Long, String>>, label: String = "", onChange: (Pair<Long, String>) -> Unit){
    val focusManager = LocalFocusManager.current
    var expandedClientes by remember { mutableStateOf(false) }
    val choosenItem = remember { mutableStateOf(Pair(0L, "")) }
    val filteredItems = remember(choosenItem.value.second)  {
        itens.filter { it.second.contains(choosenItem.value.second, ignoreCase = true) }
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
        OutlinedTextField(
            value = choosenItem.value.second,
            onValueChange = { newValue:String ->
                if(!expandedClientes) expandedClientes = true
                choosenItem.value = Pair(-1, newValue)
                val id = names[newValue]
                id?.let {
                    choosenItem.value = Pair(id, newValue)
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
            }),
            label = { Text(label) },
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
                            choosenItem.value = item
                            expandedClientes = false
                            onChange(choosenItem.value)
                        },modifier = Modifier.padding(horizontal = 3.dp)
                    ){
                        Text(item.second, modifier = Modifier.padding(6.dp))
                    }
                }
            }
        }
    }

}
