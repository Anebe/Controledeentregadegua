package com.gabriel_barros.spreedsheetgoogle


fun convert(itens: List<PedidoResponse>?) : List<PedidoEntityApi> {
    val result = ArrayList<PedidoEntityApi>()
    if (itens != null) {
        for (item in itens){
            result.add(
                PedidoEntityApi(
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

    suspend fun adicionar(pedido: PedidoEntityApi): Boolean{
        val response = apiService.post(PedidoRequest("pedido", listOf(pedido)))
        return response.isSuccessful
    }

    suspend fun get(): List<PedidoEntityApi> {
        return convert(apiService.getAll().body())
    }
}
