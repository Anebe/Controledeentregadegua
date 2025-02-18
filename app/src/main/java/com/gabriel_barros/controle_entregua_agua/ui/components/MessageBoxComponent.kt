package com.gabriel_barros.controle_entregua_agua.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido


@Composable
fun MessageBoxComponent(
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                //.width(300.dp)
                .fillMaxWidth(0.9f)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            content()

            IconButton(onClick = onDismiss,
                modifier = Modifier.align(Alignment.TopEnd)) {
                Icon(Icons.Default.Close, contentDescription = "Fechar")
            }

        }
    }
}

@Preview
@Composable
fun MessageBoxComponentPreview(){
    MessageBoxComponent({}){
        PedidoItemDetalhado(
            pedido = Pedido(),
            cliente = Cliente(nome = ""),
            emptyList(),
            onSave = { a,b,c -> /*TODO*/ }, {}) {
            
        }
    }
}