package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Endereco
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme

@Composable
fun CadastrarClienteComponent(onSave: (Cliente) -> Unit) {
    var cliente by remember { mutableStateOf(Cliente(nome = "")) }
    val enderecos = remember { mutableStateListOf<Endereco>() }
    val apelidos = remember { mutableStateListOf<String>() }

    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .width(50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = cliente.nome,
            onValueChange = { cliente = cliente.copy(nome = it) },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cliente.descricao ?: "",
            onValueChange = { cliente = cliente.copy(descricao = it) },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Endereços")
            IconButton(onClick = { enderecos.add(Endereco()) }) {
                Icon(imageVector = Icons.Default.AddCircle,
                    contentDescription = "Adicionar Endereço",
                    tint = MaterialTheme.colorScheme.primary)
            }

        }
        LazyColumn {
            enderecos.forEachIndexed { index, endereco ->
                item {

                    OutlinedTextField(
                        value = endereco.cep,
                        onValueChange = { enderecos[index] = endereco.copy(cep = it) },
                        label = { Text("CEP") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )

                    OutlinedTextField(
                        value = endereco.rua,
                        onValueChange = { enderecos[index] = endereco.copy(rua = it) },
                        label = { Text("Rua") }
                    )

                    OutlinedTextField(
                        value = endereco.bairro,
                        onValueChange = { enderecos[index] = endereco.copy(bairro = it) },
                        label = { Text("Bairro") }
                    )

                    OutlinedTextField(
                        value = endereco.complemento,
                        onValueChange = { enderecos[index] = endereco.copy(complemento = it) },
                        label = { Text("Complemento") }
                    )

                    OutlinedTextField(
                        value = endereco.numero,
                        onValueChange = { enderecos[index] = endereco.copy(numero = it) },
                        label = { Text("Número") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Apelidos")
            IconButton(onClick = { apelidos.add("") }) {
                Icon(imageVector = Icons.Default.AddCircle,
                    contentDescription = "Adicionar",
                    tint = MaterialTheme.colorScheme.primary,)
            }

        }

        LazyColumn {
            apelidos.forEachIndexed { index, apelido ->
                item {
                    OutlinedTextField(
                        value = apelido, onValueChange = { apelidos[index] = it })

                }
            }
        }

        Button(
            onClick = {
                if (cliente.nome.isNotBlank()) {
                    cliente = cliente.copy(
//                        enderecos = enderecos,
                        apelidos = apelidos)
                    onSave(cliente)
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
    ControleDeEntregaDeAguaTheme {
        CadastrarClienteComponent {

        }

    }
}