package com.gabriel_barros.usecase.usecase

import com.gabriel_barros.usecase.dto.FreteOutput
import com.gabriel_barros.usecase.dto.PedidoOutput


interface DividaSearch {

    fun getDividaById(dividaId: Long)

    fun getPedidoById(pedidoId: Long)
}

interface FreteSearch{

    fun getFreteById(dividaId: Long): FreteOutput

    fun getPedidoById(pedidoId: Long)
}

interface PedidoSearch{

    suspend fun getByClienteId(clienteId: Long): PedidoOutput

    suspend fun getPedidoById(pedidoId: Long): PedidoOutput
}

interface ProdutoSearch{
    suspend fun getAllProdutosNomes(): Map<Long, String>

    suspend fun getAllProdutosNomesById(id: List<Long>): Map<Long, String>
}

interface ClienteSearch{

    suspend fun getAllClientesNomes(): Map<Long, String>
}