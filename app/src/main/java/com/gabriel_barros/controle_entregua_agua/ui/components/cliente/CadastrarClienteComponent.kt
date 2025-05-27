package com.gabriel_barros.controle_entregua_agua.ui.components.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import com.gabriel_barros.usecase.dto.ClienteInput
import com.gabriel_barros.usecase.dto.EnderecoInput

data class ClienteUI(
    var nome: String = "",
    val apelidos: MutableList<String> = mutableListOf(),
    var descricao: String = "",
    val enderecos: MutableList<EnderecoUI> = mutableListOf(),
){
    fun to() = ClienteInput(
        nome = nome,
        apelidos = apelidos,
        descricao = descricao,
        enderecos = enderecos.map { it.to() }
    )
}

data class EnderecoUI(
    var cep: String = "",
    var rua: String = "",
    var bairro: String = "",
    var complemento: String = "",
    var numero: String = "",
){
    fun to() = EnderecoInput(
        cep = cep,
        rua = rua,
        bairro = bairro,
        complemento = complemento,
        numero = numero
    )
}


@Composable
fun CadastrarClienteComponent(onSave: (ClienteUI) -> Unit) {
    val clienteUI by remember { mutableStateOf(ClienteUI()) }

    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .width(50.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = clienteUI.nome,
            onValueChange = { clienteUI.nome = it },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = clienteUI.descricao,
            onValueChange = { clienteUI.descricao = it },
            label = { Text("Descrição") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Endereços")
            IconButton(onClick = { clienteUI.enderecos.add(EnderecoUI()) }) {
                Icon(imageVector = Icons.Default.AddCircle,
                    contentDescription = "Adicionar Endereço",
                    tint = MaterialTheme.colorScheme.primary)
            }

        }
        LazyColumn {
            clienteUI.enderecos.forEachIndexed { index, endereco ->
                item {
                    CadastrarEndereco(clienteUI.enderecos[index])

                }
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Apelidos")
            IconButton(onClick = { clienteUI.apelidos.add("") }) {
                Icon(imageVector = Icons.Default.AddCircle,
                    contentDescription = "Adicionar",
                    tint = MaterialTheme.colorScheme.primary,)
            }
        }

        LazyColumn {
            clienteUI.apelidos.forEachIndexed { index, apelido ->
                item {
                    OutlinedTextField(
                        value = apelido, onValueChange = { clienteUI.apelidos[index] = it })

                }
            }
        }

        Button(
            onClick = {
                if (clienteUI.nome.isNotBlank()) {
                    onSave(clienteUI)
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Salvar")
        }
    }
}

@Composable
fun CadastrarEndereco(endereco: EnderecoUI){
    OutlinedTextField(
        value = endereco.cep,
        onValueChange = { endereco.cep = it },
        label = { Text("CEP") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )

    OutlinedTextField(
        value = endereco.rua,
        onValueChange = { endereco.rua = it },
        label = { Text("Rua") }
    )

    OutlinedTextField(
        value = endereco.bairro,
        onValueChange = { endereco.bairro = it },
        label = { Text("Bairro") }
    )

    OutlinedTextField(
        value = endereco.complemento,
        onValueChange = { endereco.complemento = it },
        label = { Text("Complemento") }
    )

    OutlinedTextField(
        value = endereco.numero,
        onValueChange = { endereco.numero = it },
        label = { Text("Número") },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )

}
@Preview(showBackground = true)
@Composable
fun CadastroClienteScreenPreview(){
    ControleDeEntregaDeAguaTheme {
        CadastrarClienteComponent {

        }

    }
}