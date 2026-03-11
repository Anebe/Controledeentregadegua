package com.gabriel_barros.data_supabase.dao

import com.gabriel_barros.domain.entity.Frete
import com.gabriel_barros.domain.entity.Status
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class FreteDaoTest {

    private val pedidoDAO = PedidoDAO()
    private val freteDao = FreteDao()

    @Test
    fun must_save_read_delete() {
//        Produto(
//            preco = TODO(),
//            nome = TODO(),
//            custo = TODO(),
//            estoque = TODO(),
//            reservado = TODO(),
//            descricao = TODO()
//        )
//        Cliente(
//            nome = TODO(),
//            credito = TODO(),
//            apelidos = TODO(),
//            descricao = TODO(),
//            enderecos = TODO()
//        )
//        Pedido(
//            cliente_id = TODO(),
//            dataPedido = TODO(),
//            status = TODO()
//        )
//        listOf(
//            ProdutoPedido(
//                produtoId = TODO(),
//                pedidoId = TODO(),
//                qtd = TODO()
//            )
//        )
        val frete = Frete(
            pedido_id = 0,
            status = Status.PENDENTE
        )
//        listOf(
//            ProdutoEntregue(
//                produto_pedido_id = TODO(),
//                entregaId = TODO(),
//                qtdEntregue = TODO(),
//                qtdRetornado = TODO()
//            )
//        )
        runBlocking {
            val freteSalvo = freteDao.saveFrete(frete)

            assertTrue {
                freteSalvo.id > 0
            }
        }
    }

}