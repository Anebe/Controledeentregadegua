package com.gabriel_barros.controle_entregua_agua.ui.components.entrega

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.gabriel_barros.controle_entregua_agua.ui.EntregaUI
import com.gabriel_barros.controle_entregua_agua.ui.ProdutoEntregueUI
import com.gabriel_barros.controle_entregua_agua.ui.components.util.NumberPicker
import com.gabriel_barros.usecase.dto.EntregaInput
import com.gabriel_barros.usecase.usecase.EntregaManager
import com.gabriel_barros.usecase.usecase.FreteSearch
import com.gabriel_barros.usecase.usecase.ProdutoSearch
import kotlinx.coroutines.launch
import org.koin.compose.koinInject


data class ProdutoEntregue(
    val produtoPedidoId: Long,
    val produtoId: Long,
    val qtdEntregue: Int,
    val qtdRetornado: Int,
    val qtdPedido: Int,
    val produtoNome: String,
)

@Composable
fun CadastrarEntregaResumidoComponent(
    freteId: Long
) {
    val entregaManager: EntregaManager = koinInject()
    val freteSearch: FreteSearch = koinInject()
    val produtoSearch: ProdutoSearch = koinInject()
    val scope = rememberCoroutineScope()

    var produtosEntregue = mutableSetOf<ProdutoEntregue>()
    LaunchedEffect(Unit) {
        val frete = freteSearch.getFreteById(freteId)
        val produtosId = frete.produtos.map { it.produtoId }
        val produtos = produtoSearch.getAllProdutosNomesById(produtosId)

        frete.produtos.forEach { produtoEntregue ->
            val produto = produtos[produtoEntregue.produtoId]
            produtosEntregue.add(
                ProdutoEntregue(
                    produtoPedidoId = produtoEntregue.produtoPedidoId,
                    produtoId = produtoEntregue.produtoId,
                    qtdEntregue = produtoEntregue.qtdEntregue,
                    qtdRetornado = produtoEntregue.qtdRetornavel,
                    qtdPedido = produtoEntregue.qtdEntregue + produtoEntregue.qtdFalta,
                    produtoNome = produto ?: "NONE"
                )
            )

        }
    }
    CadastrarEntregaResumidoComponent(produtosEntregue) {
        scope.launch {
            entregaManager.registerEntrega(
                EntregaInput(
                    freteId = TODO(),
                    produtosEntregues = TODO(),
                    entregador = TODO()
                )
            )
        }
    }
}


@Composable
fun CadastrarEntregaResumidoComponent(
    produtosJaEntregues: Set<ProdutoEntregue>,
    onSave: (EntregaUI) -> Unit,
) {
    val entregaUI by remember { mutableStateOf(EntregaUI()) }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Produto")
            Text("Entregue")
            Text("Retornado")
        }
        for (produtoEntregue in produtosJaEntregues) {
            val produto = ProdutoEntregueUI(
                produtoPedidoId = produtoEntregue.produtoPedidoId,
                produtoId = produtoEntregue.produtoId,
                qtdEntregue = produtoEntregue.qtdEntregue,
                qtdRetornado = produtoEntregue.qtdRetornado
            )
            entregaUI.produtoEntregues.add(produto)
            val qtdFalta = produtoEntregue.qtdPedido - produtoEntregue.qtdEntregue
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Text(produtoEntregue.produtoNome)
                NumberPicker(
                    value = produto.qtdEntregue + produtoEntregue.qtdEntregue,
                    onValueChange = { produto.qtdEntregue = it },
                    minValue = 0, maxValue = qtdFalta
                )
                NumberPicker(
                    value = produto.qtdRetornado + produtoEntregue.qtdRetornado,
                    onValueChange = { produto.qtdRetornado = it })
            }
        }
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                onSave(entregaUI)


            }) {
            Text(text = "Salvar")
        }
    }
}

//@Composable
//fun CadastrarEntregaResumidoComponent(
//    produtosEntregues: List<ProdutoEntity>,
//    entregasAnteriores: List<ItensEntrega>,
//    itensPedido: List<ItensPedido>,
////    onSave: (EntregaEntity, List<ItensEntrega>) -> Unit
//) {
//    val entregaUI = remember { mutableStateOf(EntregaUI()) }
//
//    val entregaManager: EntregaManager = koinInject()
//    val entregaDTO = EntregaManager.EntregaRegister()
//    val scope = rememberCoroutineScope()
//
//    val resultItensEntregue = ArrayList<ItensEntrega>()
//    val entregaQuery: EntregaQueryBuilder = koinInject()
//
//    LaunchedEffect(Unit) {
//
//    }
//    Column {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Text("Produto")
//            Text("Entregue")
//            Text("Retornado")
//        }
//        for (prodEntregue in produtosEntregues) {
//            //TODO pegar qtd do que falta entregar pra virar limite no numberPicker
//            val produtoPedido = itensPedido.find { it.produto_id == prodEntregue.id }
//            if (produtoPedido != null) {
//                val qtdPedida = produtoPedido.qtd
//                val produtoEntregue =
//                    entregasAnteriores.filter { produtoPedido.id == it.itemPedido_id }
//                val qtdEntregue = produtoEntregue.sumOf { it.qtdEntregue }
//                val qtdFalta = qtdPedida - qtdEntregue
//
//                var entregaAtual by remember {
//                    mutableStateOf(
//                        EntregaManager.ProdutoEntregue(
//                            qtdEntregue = 0,
//                            produtoPedidoId = produtoPedido.id,
//                            qtdRetornavel = 0
//                        )
//                    )
//                }
//                entregaDTO.produtosEntregues.add(entregaAtual)
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//
//                    Text(prodEntregue.nome)
//                    NumberPicker(
//                        value = entregaAtual.qtdEntregue,
//                        onValueChange = { entregaAtual.qtdEntregue = it },
//                        minValue = 0, maxValue = qtdFalta
//                    )
//                    NumberPicker(
//                        value = entregaAtual.qtdRetornavel,
//                        onValueChange = { entregaAtual.qtdRetornavel = it })
//                }
//            }
//        }
//        Button(
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            onClick = {
//                val resultEntrega = EntregaEntity(
//                    id = 0, data = LocalDate.now(), status = StatusEntrega.FINALIZADO,
//                    pedido_id = itensPedido[0].pedido_id
//                )
//
//                scope.launch { entregaManager.registerEntrega(entregaDTO) }
////                onSave(resultEntrega, resultItensEntregue)
//            }) {
//            Text(text = "Salvar")
//        }
//    }
//}
