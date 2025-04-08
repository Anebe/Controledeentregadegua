package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.database.LocalDateSerializer
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pagamento
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoPagamento
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PagamentoSupabase(
    val id: Long = 0,
    val pedido_id: Long = 0,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    val valor: Double = 0.0,
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

