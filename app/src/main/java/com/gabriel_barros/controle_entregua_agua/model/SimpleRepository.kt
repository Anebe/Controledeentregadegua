package com.gabriel_barros.controle_entregua_agua.model

import com.gabriel_barros.controle_entregua_agua.api.PedidoApi
import com.gabriel_barros.controle_entregua_agua.api.PedidoRequest
import com.gabriel_barros.controle_entregua_agua.api.PedidoResponse
import com.gabriel_barros.controle_entregua_agua.api.RetrofitInstance


fun convert(itens: List<PedidoResponse>?) : List<Pedido> {
    val result = ArrayList<Pedido>()
    if (itens != null) {
        for (item in itens){
            result.add(
                Pedido(
                    item.id,
                    item.cliente.id,
                    item.qtd,
                    item.data_para_entrega,
                    item.troco,
                )
            )
        }
    }
    return result
}

class SimpleRepository() {
    private val apiService: PedidoApi = RetrofitInstance.pedido

    suspend fun adicionar(pedido: Pedido): Boolean{
        val response = apiService.post(PedidoRequest("pedido", listOf(pedido)))
        return response.isSuccessful
    }

    suspend fun get(): List<Pedido> {
        return convert(apiService.getAll().body())
    }
}
