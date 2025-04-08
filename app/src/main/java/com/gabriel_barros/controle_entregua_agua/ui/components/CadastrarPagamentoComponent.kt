package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.PagamentoSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DatePicker
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropDownMenuComponent
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastrarPagamentoComponent(
        clientes: List<Pair<Long, String>>,
        onSave: (PagamentoSupabase) -> Unit) {

    var data by remember { mutableStateOf(LocalDate.now())}
    var valor by remember { mutableStateOf("")}
    var pedidoId by remember { mutableLongStateOf(0L) }
    var tipoPagamento by remember { mutableStateOf(TipoPagamento.DINHEIRO) }

    Column {
        ComboBoxComponent(label = "Cliente",itens = clientes) {
            pedidoId = it.first
        }
        OutlinedTextField(
            value = valor, onValueChange = { valor = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Valor") },
        )

        DatePicker (label = "Data"){
            data = it
        }

        DropDownMenuComponent(itens = TipoPagamento.entries.associateBy { it.name }, "Modo de Pagamento") {
            tipoPagamento = it.second
        }

        Button(modifier = Modifier.align(Alignment.End),
            onClick = {
                onSave(PagamentoSupabase(
                pedido_id = pedidoId,
                valor = valor.toDouble(),
                data = data,
                pagamento = tipoPagamento))
        }) {
            Text(text = "Salvar")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun CadastrarPagamentoComponentPreview(){
    CadastrarPagamentoComponent(listOf(1L to "asd")){

    }
}