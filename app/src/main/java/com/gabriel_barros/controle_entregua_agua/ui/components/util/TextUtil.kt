package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun H1(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.displayLarge, modifier = modifier, color = color)
}

@Composable
fun H2(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.displayMedium, modifier = modifier, color = color)
}

@Composable
fun H3(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.displaySmall, modifier = modifier, color = color)
}

@Composable
fun H4(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.headlineMedium, modifier = modifier, color = color)
}

@Composable
fun H5(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.headlineSmall, modifier = modifier, color = color)
}

@Composable
fun Body1(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.bodyLarge, modifier = modifier, color = color)
}

@Composable
fun Body2(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.bodyMedium, modifier = modifier, color = color)
}

@Composable
fun Body3(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(text, style = MaterialTheme.typography.bodySmall, modifier = modifier, color = color)
}

// Body4 e Body5 podem ser variações mais leves ou itálicas, você pode definir custom
@Composable
fun Body4(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text,
        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light),
        modifier = modifier,
        color = color
    )
}

@Composable
fun Body5(text: String, modifier: Modifier = Modifier, color: Color = Color.Unspecified) {
    Text(
        text,
        style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic),
        modifier = modifier,
        color = color
    )
}
