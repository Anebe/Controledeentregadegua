package com.gabriel_barros.controle_entregua_agua.ui.components.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.gabriel_barros.usecase.dto.ProdutoEntregueOutput


@Composable
fun EntregaRelatorioComponent(
    produtosEntregues: Set<ProdutoEntregueOutput>
){
    val produtoWeight = 0.3f
    val entregaWeight = 0.3f
    val retornadoWeight = 0.3f

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Produto", Modifier.weight(produtoWeight))
            Text("Entregue", Modifier.weight(entregaWeight))
            Text("Retornado", Modifier.weight(retornadoWeight))
        }
        LazyColumn {
            produtosEntregues.forEachIndexed { index, itensPedido ->
                item {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(itensPedido.produtoNome, Modifier.weight(produtoWeight))
                        Text("${itensPedido.qtdEntregue}/${itensPedido.total}", Modifier.weight(entregaWeight))
                        Text("${itensPedido.qtdRetornavel}", Modifier.weight(retornadoWeight))
                    }
                }
            }
        }
    }


}




//@Composable
//fun EntregaRelatorioComponent(
//    produtos: List<ProdutoEntity>,
//    itensPedidos: List<ItensPedido>,
//    produtosEntregues: List<ItensEntrega>
//) {
//    val produtoWeight = 0.3f
//    val entregaWeight = 0.3f
//    val retornadoWeight = 0.3f
//
//    Column {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Text("Produto", Modifier.weight(produtoWeight))
//            Text("Entregue", Modifier.weight(entregaWeight))
//            Text("Retornado", Modifier.weight(retornadoWeight))
//        }
//        LazyColumn {
//            itensPedidos.forEachIndexed { index, itensPedido ->
//                item {
//
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        val entregue = produtosEntregues
//                            .filter { it.itemPedido_id == itensPedido.id }
//                            .sumOf { it.qtdEntregue }
//                        val retornado = produtosEntregues
//                            .filter { it.itemPedido_id == itensPedido.id }
//                            .sumOf { it.qtdRetornado }
//                        val prod = produtos.find { itensPedido.produto_id == it.id }?.nome
//
//                        Text("${prod}", Modifier.weight(produtoWeight))
//                        Text("${entregue}/${itensPedido.qtd}", Modifier.weight(entregaWeight))
//                        Text("${retornado}", Modifier.weight(retornadoWeight))
//                    }
//                }
//            }
//        }
//    }
//
//}

