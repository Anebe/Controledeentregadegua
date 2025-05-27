package com.gabriel_barros.controle_entregua_agua.ui.components.pedido

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.components.entrega.CadastrarEntregaResumidoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.entrega.EntregaRelatorioComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.pagamento.CadastrarPagamentoResumidoComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.pagamento.PagamentoRelatorioComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MyBox
import com.gabriel_barros.usecase.dto.ClienteOutput
import com.gabriel_barros.usecase.dto.DividaOutput
import com.gabriel_barros.usecase.dto.FreteOutput
import com.gabriel_barros.usecase.dto.PedidoOutput
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.PagamentoManager
import com.gabriel_barros.usecase.usecase.PedidoManager
import org.koin.compose.koinInject


@Composable
fun PedidoItemDetalhado(pedidoId: Long){

    val pedidoManager: PedidoManager = koinInject()
    val entregaManager: EntregaManager = koinInject()
    val pagamentoManager: PagamentoManager = koinInject()
}
@Composable
fun PedidoItemDetalhado(
    pedido: PedidoOutput,
    cliente: ClienteOutput,
    frete: FreteOutput,
    divida: DividaOutput,
//    onSave: (valorPago: Double, entrega: Int, galaoSeco: Int) -> Unit,
//    onDelete: () -> Unit,
//    onAddEntrega: () -> Unit,
//    onSaveEntrega: (EntregaEntity, List<ItensEntrega>) -> Unit,
//    onSavePagamento: (Long, Double, TipoPagamento) -> Unit
) {


    var entrega by remember { mutableIntStateOf(0) }
    var galaoSeco by remember { mutableIntStateOf(0) }
    var showCadastroEntrega by remember { mutableStateOf(false) }
    var showCadastroPagamento by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {

    }

    Column(
        modifier = Modifier.padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(text = "Cliente: ${cliente.nome}")
        MyBox(modifier = Modifier.fillMaxWidth()) {

            if (showCadastroEntrega) {
                CadastrarEntregaResumidoComponent(
                    freteId = frete.id
                )
            } else {
                EntregaRelatorioComponent(frete.produtos)
                Button(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    onClick = { showCadastroEntrega = true }) {
                    Text(text = "Adicionar")
                }
            }
        }
        MyBox(modifier = Modifier.fillMaxWidth()) {
            if (showCadastroPagamento) {
                CadastrarPagamentoResumidoComponent(clienteId = cliente.id)
            } else {
                PagamentoRelatorioComponent(pedidoId = pedido.id)
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = { showCadastroPagamento = true }) {
                    Text(text = "Adicionar")
                }
            }
        }
//        dataEntregaAgendada?.let{
//            Text(text = "Fazer Entrega Em: ${dataEntregaAgendada.dayOfMonth}/${dataEntregaAgendada.monthValue}/${dataEntregaAgendada.year}")
//        }


    }

}



