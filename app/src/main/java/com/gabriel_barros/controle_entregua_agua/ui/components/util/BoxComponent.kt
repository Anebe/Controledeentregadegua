package com.gabriel_barros.controle_entregua_agua.ui.components.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun MyBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier
        , elevation = CardDefaults.cardElevation(4.dp),
    ) {
        content()
    }
}

@Composable
fun MySection(
    content: @Composable () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                shadowElevation = 3f
                //shape = RoundedCornerShape(12.dp)
                clip = true
                // deslocamento sutil da sombra
                translationX = 0f
                translationY = 0f
            }
    ){
        content()
    }
}