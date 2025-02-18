package com.gabriel_barros.controle_entregua_agua

import com.gabriel_barros.controle_entregua_agua.database.supabase.SupabaseClientProvider
import com.gabriel_barros.controle_entregua_agua.database.supabase.dao.ClienteDAO
import com.gabriel_barros.controle_entregua_agua.domain.entity.Cliente
import com.gabriel_barros.controle_entregua_agua.domain.entity.Endereco
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    @Test
    fun apiPost(): Unit = runBlocking{
//        SupabaseClientProvider.supabase.auth.signInAnonymously()
        val dao = ClienteDAO(SupabaseClientProvider.supabase)
        dao.saveCliente(Cliente(nome = "ggggggggg", descricao = "fffffff", enderecos =
            listOf(Endereco(cep = "asdasd")),
            apelidos = mutableListOf("rrrrr","qqqqqqq")
        ))
    }
//
//    @Test
//    fun apiGet(): Unit = runBlocking{
//
//        //val a = RetrofitInstance.pedido.get("pedido")
//        //println(RetrofitInstance.pedido.get(id = 1))
//       println(RetrofitInstance.cliente.getAll())
//    }

}
