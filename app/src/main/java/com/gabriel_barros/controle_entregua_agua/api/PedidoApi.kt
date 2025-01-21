package com.gabriel_barros.controle_entregua_agua.api

import com.gabriel_barros.controle_entregua_agua.model.LocalDateSerializer
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.LocalDate

@Serializable
data class PedidoRequest(
    val entity: String,
    val data: List<Pedido>
)

@Serializable
data class PedidoResponse(
    val id: Int,
    val cliente: ClienteResponse,
    val qtd: Int,
    @Serializable(with = LocalDateSerializer::class)
    val data_para_entrega: LocalDate,
    val troco: Int
)

interface PedidoApi  {
    @POST(ENDPOINT)
    suspend fun post(@Body requestBody: PedidoRequest): Response<Unit>

    @GET(ENDPOINT)
    suspend fun getAll(@Query("entity") entity: String = "pedido"): Response<List<PedidoResponse>>

    @GET(ENDPOINT)
    suspend fun get(
        @Query("entity") entity: String = "pedido",
        @Query("id") id: Int
    ): Response<PedidoResponse>
}