package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme

@Composable
fun ClienteListComponent(strings: List<String>) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(strings.size) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                colors = CardDefaults.cardColors()
            ) {
                Row(modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                    , verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = strings[item],

                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = "detalhes",
                        style = MaterialTheme.typography.labelSmall)
                    
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ClienteListComponentPreview(){
    ControleDeEntregaDeAguaTheme {
        ClienteListComponent(strings = listOf("a","b"))
    }
}