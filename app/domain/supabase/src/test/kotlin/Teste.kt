
import com.gabriel_barros.domain.domain.entity.Cliente
import com.gabriel_barros.domain.domain.entity.Produto
import com.gabriel_barros.domain.domain.portout.ClientePortOut
import com.gabriel_barros.domain.domain.portout.EntregaPortOut
import com.gabriel_barros.domain.domain.portout.PagamentoPortOut
import com.gabriel_barros.domain.domain.portout.PedidoPortOut
import com.gabriel_barros.domain.domain.portout.ProdutoPortOut
import com.gabriel_barros.domain.domain.portout.query.ClienteFilterBuilder
import com.gabriel_barros.domain.domain.portout.query.ClienteSelecBuilder
import com.gabriel_barros.domain.domain.portout.query.EntregaQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ItemPedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PagamentoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.PedidoQueryBuilder
import com.gabriel_barros.domain.domain.portout.query.ProdutoQueryBuilder
import com.gabriel_barros.supabase.SupabaseClientProvider
import com.gabriel_barros.supabase.dependenceInjectionSupabase
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class Teste : KoinTest {
    init {
        stopKoin()
        startKoin{
            modules(dependenceInjectionSupabase)
        }
    }
    val produtoPort: ProdutoPortOut by inject()
    val pedidoPort: PedidoPortOut by inject()
    val pagamentoPort: PagamentoPortOut by inject()
    val entregaPort: EntregaPortOut by inject()
    val clientePort: ClientePortOut by inject()

    val produtoQuery: ProdutoQueryBuilder by inject()
    val pedidoQuery: PedidoQueryBuilder by inject()
    val itenspedidosQuery: ItemPedidoQueryBuilder by inject()
    val pagamentoQuery: PagamentoQueryBuilder by inject()
    val entregaQuery: EntregaQueryBuilder by inject()
    val clienteFilter: ClienteFilterBuilder by inject()
    val clienteQuery: ClienteSelecBuilder by inject()


    @Test
    fun bbbbb() {
        val client = SupabaseClientProvider.supabase
        println(client)
    }

    @Test
    fun add() {
        runBlocking {
            // Criando produtos
            val produto1 = Produto(

                preco = 10.0,
                nome = "Coca-Cola",
                custo = 5.0,
                estoque = 50,
                descricao = "Refrigerante 2L",
                reservado = 0
            )
            val produto2 = Produto(

                preco = 5.0,
                nome = "Chocolate",
                custo = 2.5,
                estoque = 30,
                descricao = "Chocolate ao leite",
                reservado = 0
            )
            val produto3 = Produto(

                preco = 20.0,
                nome = "Pizza",
                custo = 12.0,
                estoque = 10,
                descricao = "Pizza grande de calabresa",
                reservado = 0
            )
            val produto4 = Produto(

                preco = 8.0,
                nome = "Suco de Laranja",
                custo = 4.0,
                estoque = 20,
                descricao = "Suco natural 500ml",
                reservado = 0
            )

            produtoPort.saveProduto(produto1)
            produtoPort.saveProduto(produto2)
            produtoPort.saveProduto(produto3)
            produtoPort.saveProduto(produto4)
//
//// Criando clientes
            val cliente1 = Cliente( credito = 00.0, enderecos =  emptyList(), descricao =  "", apelidos =  listOf(""))
            val cliente2 = Cliente( nome = "Maria Souza", credito = 00.0, enderecos =  emptyList(), descricao =  "", apelidos =  listOf(""))
            val cliente3 = Cliente( nome = "Carlos Oliveira", credito = 0.0, enderecos =  emptyList(), descricao =  "", apelidos =  listOf(""))
            val cliente4 = Cliente( nome = "Ana Pereira", credito = 00.0, enderecos =  emptyList(), descricao =  "", apelidos =  listOf(""))

            clientePort.saveCliente(cliente1)
            clientePort.saveCliente(cliente2)
            clientePort.saveCliente(cliente3)
            clientePort.saveCliente(cliente4)

        }
    }
}