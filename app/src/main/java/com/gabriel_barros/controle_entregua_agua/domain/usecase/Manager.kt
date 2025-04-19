package com.gabriel_barros.controle_entregua_agua.domain.usecase

import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.ItensPedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.Produto
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento

interface ProdutoManager {
    suspend fun registerProduto(produto: Produto): Produto
    //suspend fun deleteProduto(id: Long): Produto
}

interface PedidoManager{
    data class PedidoDTO(val clienteId: Long, val itensDoPedido: Set<ItemDoPedido>)
    data class ItemDoPedido(val qtd: Int, val produtoId: Long)

    suspend fun makePedido(pedido: PedidoDTO): Pedido
    suspend fun checkAndFinalizePedido(pedidoId: Long)
    suspend fun validateStockForPedido(itens: List<ItensPedido>): Boolean
}

interface PagamentoManager{
    data class PagamentoDTO(val valor: Double, val tipoPagamento: TipoPagamento)

    suspend fun processPagamento(clienteId: Long, pagamento: PagamentoDTO): List<Pagamento>
    suspend fun payPedido(pedidoId: Long, pagamento: PagamentoDTO)
    suspend fun payDebts(clienteId: Long, pagamento: PagamentoDTO)
    suspend fun increaseCredit(clienteId: Long, pagamento: PagamentoDTO)
}

interface EntregaManager {
    suspend fun registerEntrega(entrega: Entrega, produtosEntregues: List<ItensEntrega>): Entrega

}

interface ClienteManager {
    suspend fun registerCliente(cliente: Cliente): Cliente
    //suspend fun deleteCliente(id: Long): Cliente
}