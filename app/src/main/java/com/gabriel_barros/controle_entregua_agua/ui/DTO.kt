package com.gabriel_barros.controle_entregua_agua.ui

import java.time.LocalDate

data class ProdutoEntregueUI(
    var produtoPedidoId: Long,
    var produtoId: Long,
    var qtdEntregue: Int,
    var qtdRetornado: Int,
)

data class EntregaUI(
    var pedidoId: Long = 0,
    val produtoEntregues: MutableSet<ProdutoEntregueUI> = mutableSetOf<ProdutoEntregueUI>(),
    var tipoEntregador: TipoEntregadorUI = TipoEntregadorUI.COMERCIO,
    var data: LocalDate = LocalDate.now()
)

enum class TipoEntregadorUI {
    COMERCIO,
    CLIENTE,
}

data class ProdutoPedidoUI(
    val produtoId: Long,
    val produtoPedidoId: Long,
    val produtoNome: String,
    val qtdPedido: Int,
)