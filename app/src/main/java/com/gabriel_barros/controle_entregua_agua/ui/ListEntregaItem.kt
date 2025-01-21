package com.gabriel_barros.controle_entregua_agua.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme

@Composable
fun ListEntregaItem(nome: String, qtdAgua: Int, troco: Double, isPago: Boolean){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Text(
            text = nome,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = qtdAgua.toString(),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )

        Text(
            text = troco.toString(),
            modifier = Modifier.padding(start = 8.dp, end = 8.dp)
        )
        Checkbox(
            checked = isPago,
            onCheckedChange = null
        )
    }
}

