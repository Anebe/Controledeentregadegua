package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
        , elevation = CardDefaults.cardElevation(1.dp),
    ) {
        content()
    }
}