package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.database.supabase.entity.EntregaSupabase
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoEntregador
import com.gabriel_barros.controle_entregua_agua.ui.components.util.ComboBoxComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DatePicker
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropDownMenuComponent
import com.gabriel_barros.controle_entregua_agua.ui.components.util.DropdownWithQuantity
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CadastrarEntregaComponent (
    clientePedidoId: List<Pair<Long, String>>,
    galoes: Map<Long, String>,
    onSave: (Entrega, List<ItensEntrega>) -> Unit)
{
    var pedidoId by remember { mutableLongStateOf(0L) }
    var galoesId = remember { mutableStateListOf<ItensEntrega>() }
//    var qtdEntregue by remember { mutableIntStateOf(0)}
//    var qtdSecos by remember { mutableIntStateOf(0)}
    var tipoEntregador by remember { mutableStateOf(TipoEntregador.COMERCIO) }
    var data by remember { mutableStateOf(LocalDate.now())}

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        ComboBoxComponent(label = "Cliente", itens = clientePedidoId) {
            pedidoId = it.first
        }

//        Text(text = "Entregue(s)")
//        NumberPicker(value = qtdEntregue,
//            onValueChange = {qtdEntregue = it},
//            minValue = 0)
        DropDownMenuComponent(itens = galoes, label = "Escolher Galões") {
            galoesId.clear()
            galoesId.add(
                ItensEntrega(0,itemPedido_id = it.first, qtdEntregue = 0, qtdRetornado = 0)
            )
//            qtdEntregue = it.entries.sumOf { qtd-> qtd.value }
        }
//        DropdownWithQuantity(options = galoes, "Escolher Galões"){
//            galoesId.clear()
//            galoesId.addAll(it.map {
//                item-> ItensEntrega(0,itemPedido_id = item.key, qtdEntregue = item.value, qtdRetornado = 0)
//            })
//            qtdEntregue = it.entries.sumOf { qtd-> qtd.value }
//        }
        LazyColumn {
            galoesId.forEachIndexed { index, itensEntrega ->
                item {
                    Row {
                        Text(text = "Entregue(s)")
                        NumberPicker(value = itensEntrega.qtdEntregue,
                            onValueChange = {galoesId[index] = galoesId[index].copy(qtdEntregue = it)},
                            minValue = 0)
                        Text(text = "Seco(s)")
                        NumberPicker(value = itensEntrega.qtdRetornado,
                            onValueChange = {galoesId[index] = galoesId[index].copy(qtdRetornado = it)},
                            minValue = 0)
                    }
                }
            }
        }

        DatePicker(label = data.format(DateTimeFormatter.ofPattern("dd-MM-uuuu"))) {
            data = it
        }

        DropDownMenuComponent(itens = TipoEntregador.entries.associateBy { it.name }, "Escolher Entregador") {
            tipoEntregador = it.second
        }

        Button(onClick = { onSave(
            Entrega(
            pedido_id = pedidoId,
            data = data,
            entregador = tipoEntregador,
        ), galoesId.toList()
        ) }) {
            Text(text = "Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CadastrarEntregaComponentPreview(){
    MessageBoxComponent(onDismiss = { /*TODO*/ }) {
        Box(modifier = Modifier.padding(30.dp)){
            CadastrarEntregaComponent(clientePedidoId = listOf(1L to "a"),
                galoes = mapOf(3L  to "asd")) { a,b ->
    
            }
            
        }

    }
}