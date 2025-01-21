package com.gabriel_barros.controle_entregua_agua.api

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class ClienteRequest(
    val entity: String,
    val data: List<ClienteResponse>
)

@Serializable
data class ClienteResponse(
    val id:Int = 0,
    val nome: String = "",
    val ano: String = "",
    val qtd_galao: Int = 0,
    val qtd_emprestado: Int = 0,
    val media_entrega: Double = 0.0,
    val caixa: Double = 0.0,
    val descricao: String = "",
    val preco_galao: Double = 0.0
)

interface ClienteApi {

    //@POST(ENDPOINT)
    //suspend fun post(@Body requestBody: ClienteRequest): Response<Unit>

    @GET(ENDPOINT)
    suspend fun getAll(@Query("entity") entity: String = "cliente",): Response<List<ClienteResponse>>

    @GET(ENDPOINT)
    suspend fun get(@Query("entity") entity: String = "cliente",
                    @Query("id") id: Int): Response<ClienteResponse>
}