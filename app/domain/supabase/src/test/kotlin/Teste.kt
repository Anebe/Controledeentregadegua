import com.gabriel_barros.supabase.SupabaseClientProvider
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class Teste {
    @Test
    fun `teste de conexão com Supabase`() = runBlocking{
        val client = SupabaseClientProvider.supabase
        assertNotNull(client) // Pode falhar se a inicialização estiver errada
    }
}