package com.gabriel_barros.controle_entregua_agua

import com.gabriel_barros.controle_entregua_agua.api.PedidoRequest
import com.gabriel_barros.controle_entregua_agua.api.RetrofitInstance
import com.gabriel_barros.controle_entregua_agua.database.exposed.MainViewModel
import com.gabriel_barros.controle_entregua_agua.model.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

//    @Test
//    fun apiPost(): Unit = runBlocking{
//        val a = RetrofitInstance.pedido.post(
//            PedidoRequest("pedido",
//            listOf(
//                Pedido(clienteId = 1, qtd_agua = 3, troco = 40),
//                Pedido(clienteId = 2, qtd_agua = 1, troco = 30),
//            )))
//        println(a)
//    }
//
//    @Test
//    fun apiGet(): Unit = runBlocking{
//
//        //val a = RetrofitInstance.pedido.get("pedido")
//        //println(RetrofitInstance.pedido.get(id = 1))
//       println(RetrofitInstance.cliente.getAll())
//    }

}
