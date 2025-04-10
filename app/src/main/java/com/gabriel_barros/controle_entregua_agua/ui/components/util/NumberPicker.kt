package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.gabriel_barros.controle_entregua_agua.ui.theme.ControleDeEntregaDeAguaTheme

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    minValue: Int = Int.MIN_VALUE,
    maxValue: Int = Int.MAX_VALUE,
    fontSize: TextUnit =  MaterialTheme.typography.bodyLarge.fontSize
) {
    val SCALE_SP_TO_DP = 1.2
    val iconSize = (fontSize.value * SCALE_SP_TO_DP).dp

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.size(iconSize),
            onClick = {
                if (value > minValue) onValueChange(value - 1)
            },
            enabled = value > minValue,
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Subtrair")
        }
        Text(
            text = value.toString(),
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            fontWeight = FontWeight(1000),
            modifier = Modifier
        )
        IconButton(
            modifier = Modifier.size(iconSize),
            onClick = {
                if (value < maxValue) onValueChange(value + 1)
            },
            enabled = value < maxValue
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Adicionar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NumberPickerPreview() {
    ControleDeEntregaDeAguaTheme {
        NumberPicker(0,{}, fontSize = 10.sp)

    }
}