package com.gabriel_barros.usecase.dto

import com.gabriel_barros.domain.entity.Cliente
import com.gabriel_barros.domain.entity.Pedido
import com.gabriel_barros.domain.entity.ProdutoPedido
import com.gabriel_barros.domain.entity.Status
import com.gabriel_barros.domain.entity.StatusPedido
import com.gabriel_barros.domain.entity.TipoPagamento
import java.math.BigDecimal
import java.time.LocalDate

data class ClienteOutput(
    val id: Long,
    val nome: String,
) {
    constructor(cliente: Cliente) : this(
        id = cliente.id,
        nome = cliente.nome,
    )
}


data class PedidoOutput(
    val id: Long,
    val cliente: ClienteOutput,
    val data: LocalDate,
    val status: StatusPedido,
    val produtos: Set<ProdutoPedidoOutPut>
) {
    constructor(pedido: Pedido, cliente: Cliente, produtos: Set<ProdutoPedidoOutPut>) : this(
        id = pedido.id.id,
        cliente = ClienteOutput(cliente),
        data = pedido.dataPedido,
        status = pedido.status,
        produtos = produtos,
    )

//    internal constructor(pedido: Pedido, cliente: Cliente) : this(
//        id = pedido.id.id,
//        cliente = ClienteOutput(cliente),
//        data = pedido.dataPedido,
//        status = pedido.status,
//        produtos = pedido.getProdutos().map { ProdutoPedidoOutPut(it) },
//    )
}

data class ProdutoPedidoOutPut(
    val pedidoId: Long,
    val produtoId: Long,
    val nome: String,
    val qtd: Int,
) {
    internal constructor(nome: String, produto: ProdutoPedido) : this(
        pedidoId = produto.pedidoId.id,
        produtoId = produto.produtoId.id,
        nome = nome,
        qtd = produto.qtd,
    )
}

data class PagamentoOutput(
    val id: Long,
    val freteId: Long,
    val data: LocalDate,
    val valor: BigDecimal,
    val pagamento: TipoPagamento,
)

data class DividaOutput(
    val id: Long,
    val pedido_id: Long,
    val valor: BigDecimal,
    val pagamentos: List<PagamentoOutput>,
)

data class FreteOutput(
    val id: Long,
    val pedidoId: Long,
    val status: Status,
    val produtos: Set<ProdutoEntregueOutput>,
    val entregas: Set<EntregaOutput>
)

data class EntregaOutput(
    val id: Long,
    val freteId: Long,
    val data: LocalDate,
    val produtoEntregues: Set<ProdutoEntregueOutput>
)

data class ProdutoEntregueOutput(
    val produtoNome: String,
    val produtoPedidoId: Long,
    val produtoId: Long,
    val qtdEntregue: Int,
    val qtdFalta: Int,
    val qtdRetornavel: Int,
    val total: Int = qtdEntregue + qtdFalta
)

data class PedidoCompletoOutput(
    val pedido: PedidoOutput,
    val frete: FreteOutput,
    val divida: DividaOutput
)