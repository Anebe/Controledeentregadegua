package com.gabriel_barros.spreedsheetgoogle

import com.gabriel_barros.controle_entregua_agua.api.ClienteApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


// Representa uma entidade
//@Serializable
//data class EntityPost(
//    var entity: String,
//    val data: List<Pedido>
//)
//
//@Serializable
//data class EntityGet(
//    var entities: List<String>
//)


//interface ApiService {
//    @POST("pedido/")
//    suspend fun postData(@Body requestBody: EntityPost): retrofit2.Response<Unit>
//
//    @GET("pedido/")
//    suspend fun get(@Body requestBody: EntityGet): retrofit2.Response<ResponseBody>
//}
const val key = "AKfycbxy4GXF2NBNTe2OFy0vsUVTjVrezPtPti04R9IUUEJGhAXA_I2gFt6x-wrHwHWVWM0Z"
const val ENDPOINT = "macros/s/$key/exec"
object RetrofitInstance {
    private const val BASE_URL = "https://script.google.com/"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val pedido: PedidoApi by lazy {
        retrofit.create(PedidoApi::class.java)
    }
    val cliente: ClienteApi by lazy {
        retrofit.create(ClienteApi::class.java)
    }
}