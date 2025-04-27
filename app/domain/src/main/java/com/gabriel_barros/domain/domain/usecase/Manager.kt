package com.gabriel_barros.domain.domain.usecase

import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.Entrega
import com.gabriel_barros.domain.domain.entity.ItensEntrega
import com.gabriel_barros.domain.domain.entity.ItensPedido
import com.gabriel_barros.domain.domain.entity.PagamentoEntity
import com.gabriel_barros.domain.domain.entity.PedidoEntity
import com.gabriel_barros.domain.domain.entity.ProdutoEntity
import com.gabriel_barros.domain.domain.entity.TipoPagamento
import java.time.LocalDate

interface ProdutoManager {
    suspend fun registerProduto(produto: ProdutoEntity): ProdutoEntity
    //suspend fun deleteProduto(id: Long): Produto
}

interface PedidoManager {
    data class PedidoInput(
        val clienteId: Long,
        val itensDoPedido: Set<ItemDoPedidoInput>,
        val dataAgendadaParaEntrega: LocalDate = LocalDate.now()
    )

    data class ItemDoPedidoInput(val qtd: Int, val produtoId: Long)

    suspend fun makePedido(pedido: PedidoInput): PedidoEntity
    suspend fun checkAndFinalizePedido(pedidoId: Long)
    suspend fun validateStockForPedido(itens: List<ItensPedido>): Boolean
}

interface PagamentoManager {
    data class PagamentoDTO(val valor: Double, val tipoPagamento: TipoPagamento)

    suspend fun processPagamento(clienteId: Long, pagamento: PagamentoDTO): List<PagamentoEntity>

    suspend fun payPedido(pedidoId: Long, pagamento: PagamentoDTO)
    suspend fun payPedidoRemainder(pedidoId: Long)
    suspend fun payDebts(clienteId: Long, pagamento: PagamentoDTO)

    suspend fun increaseCredit(clienteId: Long, pagamento: PagamentoDTO)
}

interface EntregaManager {
    suspend fun registerEntrega(entrega: Entrega, produtosEntregues: List<ItensEntrega>): Entrega
    suspend fun registerCompleteEntrega(pedidoId: Long): Entrega

}

interface ClienteManager {

    suspend fun registerCliente(cliente: Cliente): Cliente
    //suspend fun deleteCliente(id: Long): Cliente
}