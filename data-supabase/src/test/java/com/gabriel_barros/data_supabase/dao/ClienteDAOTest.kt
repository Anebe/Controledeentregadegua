package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.data_supabase.dao.query.ClienteQuery
import com.gabriel_barros.domain.entity.Cliente
import com.gabriel_barros.domain.error.NotFoundException
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ClienteDAOTest {

    private val clienteDAO = ClienteDAO()
    private val clienteQuery = ClienteQuery()

    //    @Test
//    fun must_save_cliente() {
//        runBlocking {
//            val cliente = clienteDAO.saveCliente(
//                Cliente(
//                    id = 0,
//                    nome = "Gabriel",
//                    credito = 0.0,
//                    apelidos = listOf("Gab, Barr"),
//                    descricao = "Uma descrição ai",
//                )
//            )
//
//            assertEquals("Gabriel", cliente.nome)
//            assert(cliente.id > 0)
//        }
//    }

    @Test
    fun must_save_find_delete_cliente() {


        runBlocking {
            val nomeEsperado = "Gabriel para deletar"
            val cliente = Cliente(
                nome = nomeEsperado,
                credito = 0.0,
                apelidos = listOf("Del"),
                descricao = "Para deletar",
            )
            val salvo = clienteDAO.saveCliente(cliente)

            assertEquals(nomeEsperado, cliente.nome)
            assert(salvo.id > 0)

            val encontrado = clienteQuery.getClienteById(salvo.id).buildExecuteAsSingle()
            assertEquals(salvo, encontrado)

            val deletado = clienteDAO.deleteCliente(salvo.id)
            assertEquals(salvo.id, deletado.id)
            assertThrows<NotFoundException> {
                val buscado = clienteQuery.getClienteById(salvo.id).buildExecuteAsSingle()
            }
        }
    }


//    @Test
//    fun must_throw_delete() {
//        runBlocking {
//            assertThrows<NotFoundException> {
//                clienteDAO.deleteCliente(-1)
//            }
//        }
//    }
}