package com.gabriel_barros.usecase.usecase

import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.usecase.dto.ClienteInput
import com.gabriel_barros.usecase.dto.EntregaInput
import com.gabriel_barros.usecase.dto.PagamentoInput
import com.gabriel_barros.usecase.dto.PedidoInput

interface ProdutoManager {
    suspend fun registerProduto(produto: Produto): Produto
    suspend fun updateProduto(produto: Produto)
    //suspend fun deleteProduto(id: Long): Produto
}

interface PedidoManager {
    suspend fun makePedido(pedido: PedidoInput)
    suspend fun checkAndFinalizePedido(pedidoId: Long)
}

interface PagamentoManager {
    suspend fun processPagamento(clienteId: Long, pagamento: PagamentoInput)

    suspend fun payPedido(pedidoId: Long, pagamento: PagamentoInput)
    suspend fun payPedidoRemainder(pedidoId: Long)
    suspend fun payDebts(clienteId: Long, pagamento: PagamentoInput)

    suspend fun increaseCredit(clienteId: Long, pagamento: PagamentoInput)
}

interface EntregaManager {
    suspend fun registerEntrega(pedidoId: Long)

    suspend fun registerEntrega(entrega: EntregaInput)

//    suspend fun registerCompleteEntrega(pedidoId: Long): Entrega

}

interface ClienteManager {

    suspend fun registerCliente(cliente: ClienteInput)
    //suspend fun deleteCliente(id: Long): Cliente
}