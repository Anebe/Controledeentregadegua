package com.gabriel_barros.data_supabase.entity

import com.gabriel_barros.data_supabase.BigDecimalSerializer
import com.gabriel_barros.data_supabase.LocalDateSerializer
import com.gabriel_barros.domain.entity.TipoPagamento
import kotlinx.serialization.Serializable
import java.math.BigDecimal
import java.time.LocalDate

@Serializable
data class PagamentoSupabase(
    val id: Long = 0,
    val divida_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    @Serializable(with = BigDecimalSerializer::class)
    val valor: BigDecimal,
    val pagamento: TipoPagamento = TipoPagamento.DINHEIRO,
){
//    fun to(pedido: Pedido? = null): Pagamento{
//        return Pagamento(
//            id = id,
//            pedido_id = pedido_id,
//            data = data,
//            valor = valor,
//            pagamento = pagamento,
//        )
//    }
}

