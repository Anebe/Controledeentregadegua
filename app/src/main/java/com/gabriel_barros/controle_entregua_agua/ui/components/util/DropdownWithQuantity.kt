package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DropdownWithQuantity(
    options: Map<Long , String>,
    label: String = "",
    onChange: (Map<Long, Int>) -> Unit
) {
    var itens = remember { mutableStateListOf<Pair<Long, Int>>() }
//    var selectedItems = remember { mutableStateMapOf<String, Long>() }
//    var qtdItems = remember { mutableStateMapOf<Long, Int>() }
    Column() {
        DropDownMenuComponent(itens = options, label) {
            if (itens.none { id -> id.first == it.first }) {
                itens.add(Pair(it.first, 0))
                onChange(itens.toMap())
            }

//            selectedItems[it.first] = it.second
//            qtdItems[it.second] = 0
        }

        // Lista de itens selecionados com controle de quantidade
//        selectedItems.forEach { (item, quantity) ->
//        itens.forEach { item/*(item, id, quantity)*/ ->
        itens.forEachIndexed { index, item ->

          Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                qtdItems[selectedItems[item]]?.let{qtd ->
//                    Text(item, modifier = Modifier.weight(1f))
//                    NumberPicker(value = qtd, onValueChange = { qtdItems[quantity] = it})
//                }
                Text(text = options[item.first]?: "", modifier = Modifier.weight(1f))
                NumberPicker(
                    value = item.second,
                    onValueChange = {
                        itens[index] = Pair(item.first, it)
                        //itens.add(Triple(item.first, item.second, it))
                        onChange(itens.toMap())

                    })

            }
        }
    }
}
