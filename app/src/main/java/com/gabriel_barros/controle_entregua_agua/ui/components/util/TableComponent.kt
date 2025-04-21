package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme


@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier =  Modifier
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun <T> TableComponent(
    items: List<T>,
    columns: List<Triple<String, (T) -> String, Float>>, // Nome da coluna + getter
) {
    val corner = RoundedCornerShape(8.dp)

    LazyColumn(Modifier
            //.border(1.dp, Color.Black, corner)
            .clip(corner)
            .fillMaxSize()) {
        item {
            Row(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
                columns.forEachIndexed { index, (title,_ , weight) ->
                    TableCell(text = title, weight = weight)
                }
            }
        }

        items(items.size) { index ->
            val item = items[index]
            Row(Modifier.fillMaxWidth()) {
                columns.forEachIndexed { i, (_, valueGetter, weight) ->
                    TableCell(
                        text = valueGetter(item),
                        weight = weight
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTableComponent() {
    data class Produto(val id: Int, val nome: String, val preco: Double)

    val produtos = listOf(
        Produto(1, "Água 000000 Mineral", 5.50),
        Produto(2, "Refrigerante", 7.99),
        Produto(3, "Suco", 6.75)
    )

    ControleDeEntregaDeAguaTheme {
        TableComponent(
            items = produtos,
            columns = listOf(
                Triple("ID", { it.id.toString() }, 0.2f),
                Triple("Nome", { it.nome }, 0.5f),
                Triple("Preço", { "R$ %.2f".format(it.preco) }, 0.3f),
            ),
        )
    }
}