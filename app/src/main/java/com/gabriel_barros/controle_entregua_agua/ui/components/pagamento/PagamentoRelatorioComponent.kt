package com.gabriel_barros.controle_entregua_agua.ui.components.pagamento

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun PagamentoRelatorioComponent(
    valorTotal: Double,
    valorPago: Double,
    faltaPagar: Double,
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(15.dp)) {
            Text("Total: R$${valorTotal}")
            Text("Pago: R$${valorPago}")
            Text("Falta: R$${faltaPagar} ")
        }
    }
}
@Composable
fun PagamentoRelatorioComponent(
    pedidoId: Long
) {
    //val pagamentoSearch: PagamentoQueryBuilder = koinInject()
    var valorTotal: Double = 0.0
    var valorPago: Double = 0.0
    var faltaPagar: Double = 0.0

    LaunchedEffect(Unit) {

    }
    PagamentoRelatorioComponent(
        faltaPagar = faltaPagar,
        valorPago = valorPago,
        valorTotal = valorTotal
    )

}

