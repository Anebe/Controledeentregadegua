package com.gabriel_barros.controle_entregua_agua.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gabriel_barros.controle_entregua_agua.ui.pedido.FormPedido
import com.gabriel_barros.controle_entregua_agua.ui.pedido.PedidoItemState
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeÁguaTheme

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (value > minValue) onValueChange(value - 1)
            },
            enabled = value > minValue,
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Subtrair")
        }
        Text(
            text = value.toString(),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight(1000),
            modifier = Modifier
        )
        IconButton(
            onClick = {
                if (value < maxValue) onValueChange(value + 1)
            },
            enabled = value < maxValue
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Adicionar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview() {
    ControleDeEntregaDeÁguaTheme {
        NumberPicker(0,{})

    }
}