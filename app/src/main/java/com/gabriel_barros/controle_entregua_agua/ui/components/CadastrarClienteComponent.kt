package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.Cliente

@Composable
fun CadastrarClienteComponent(onSave: (Cliente) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var anoDePreferencia by remember { mutableStateOf("") }
    var qtdGalao by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = anoDePreferencia,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    anoDePreferencia = it
                }
            },
            label = { Text("Ano de Preferência") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = qtdGalao,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) {
                    qtdGalao = it
                }
            },
            label = { Text("Quantidade de Galões") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nome.isNotBlank() && anoDePreferencia.isNotEmpty() && qtdGalao.isNotEmpty()) {
                    onSave(
                        Cliente(
                            nome = nome,
                            ano_validade_galao = anoDePreferencia.toInt(),
                            qtd_galao = qtdGalao.toInt(),
                            descricao = descricao
                        )
                    )
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CadastroClienteScreenPreview(){
    CadastrarClienteComponent {

    }
}