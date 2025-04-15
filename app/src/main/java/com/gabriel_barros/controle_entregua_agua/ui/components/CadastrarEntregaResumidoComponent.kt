package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusEntrega
import com.gabriel_barros.controle_entregua_agua.ui.components.util.MyBox
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme
import java.time.LocalDate


@Composable
fun CadastrarEntregaResumidoComponent(
    produtosEntregues: List<Produto>,
    entregasAnteriores: List<ItensEntrega>,
    itensPedido: List<ItensPedido>,
    onSave: (Entrega, List<ItensEntrega>) -> Unit){
    //TODO Adicionar Tipo do pagamento na UI
    val resultItensEntregue = ArrayList<ItensEntrega>()


    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Produto")
            Text("Entregue")
            Text("Retornado")
        }
        for (prodEntregue in produtosEntregues){
            //TODO pegar qtd do que falta entregar pra virar limite no numberPicker
            val produtoPedido = itensPedido.find { it.produto_id == prodEntregue.id }
            if(produtoPedido != null){
                val qtdPedida = produtoPedido.qtd
                val produtoEntregue = entregasAnteriores.filter { produtoPedido.id == it.itemPedido_id }
                val qtdEntregue = produtoEntregue.sumOf { it.qtdEntregue }
                val qtdFalta = qtdPedida - qtdEntregue

                var entregaAtual by remember { mutableStateOf(ItensEntrega(0, produtoPedido.id, 0, 0)) }
                resultItensEntregue.add(entregaAtual)
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){

                    Text(prodEntregue.nome)
                    NumberPicker(value = entregaAtual.qtdEntregue,
                        onValueChange = { entregaAtual = entregaAtual.copy(qtdEntregue =  it)},
                        minValue = 0, maxValue = qtdFalta )
                    NumberPicker(value = entregaAtual.qtdRetornado, onValueChange = { entregaAtual = entregaAtual.copy(qtdRetornado =  it)})
                }
            }
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val resultEntrega = Entrega(id=0,data= LocalDate.now(), status = StatusEntrega.FINALIZADO,
                    pedido_id = itensPedido[0].pedido_id)
                onSave(resultEntrega, resultItensEntregue)
            }) {
            Text(text = "Salvar")
        }
    }
}

@Preview
@Composable
private fun preview(){
    ControleDeEntregaDeAguaTheme {
        MyBox {
            CadastrarEntregaResumidoComponent(emptyList(), emptyList(), emptyList(),
                { } as (Entrega, List<ItensEntrega>) -> Unit)
        }
    }
}