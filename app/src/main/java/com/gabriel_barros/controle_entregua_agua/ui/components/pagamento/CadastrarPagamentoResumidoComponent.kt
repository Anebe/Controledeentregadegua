package com.gabriel_barros.controle_entregua_agua.ui.components.pagamento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.gabriel_barros.usecase.dto.PagamentoInput
import com.gabriel_barros.usecase.dto.TipoPagamentoDTO
import com.gabriel_barros.usecase.usecase.PagamentoManager
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.math.BigDecimal


@Composable
fun CadastrarPagamentoResumidoComponent(
    clienteId: Long,
) {
    val pagamentoManager: PagamentoManager = koinInject()
    val scope = rememberCoroutineScope()

    CadastrarPagamentoResumidoComponent { valor, tipo ->
        scope.launch {
            pagamentoManager.payDebts(
                clienteId = clienteId, PagamentoInput(
                    valor = valor,
                    tipoPagamento = tipo
                )
            )

        }
    }
}

@Composable
fun CadastrarPagamentoResumidoComponent(onSave: (BigDecimal, TipoPagamentoDTO) -> Unit) {
    //TODO Adicionar Tipo do pagamento na UI
    var totalStr by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        OutlinedTextField(
            value = totalStr,
            onValueChange = { newText ->
                totalStr = ""
                totalStr = newText.filter { it.isDigit() }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(75.dp)
        )

        Button(onClick = { onSave(BigDecimal(totalStr), TipoPagamentoDTO.PIX) }) {
            Text("Salvar")
        }
    }

}