package com.gabriel_barros.usecase.usecase

import com.gabriel_barros.domain.entity.Produto
import com.gabriel_barros.usecase.dto.ClienteInput
import com.gabriel_barros.usecase.dto.ClienteOutput
import com.gabriel_barros.usecase.dto.EntregaInput
import com.gabriel_barros.usecase.dto.PagamentoInput
import com.gabriel_barros.usecase.dto.PedidoInput
import com.gabriel_barros.usecase.dto.PedidoOutput
import com.gabriel_barros.usecase.dto.ProdutoInput
import com.gabriel_barros.usecase.dto.ProdutoOutput


interface ProdutoManager {
    suspend fun registerProduto(produto: ProdutoInput): ProdutoOutput
    suspend fun updateProduto(produto: Produto)
    suspend fun delete(id: Long)
    //suspend fun deleteProduto(id: Long): Produto
}

interface PedidoManager {
    suspend fun makePedido(pedido: PedidoInput): PedidoOutput
    suspend fun checkAndFinalizePedido(pedidoId: Long)
    suspend fun delete(id: Long)
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

    suspend fun registerCliente(cliente: ClienteInput): ClienteOutput
    suspend fun deleteCliente(id: Long): ClienteOutput
}