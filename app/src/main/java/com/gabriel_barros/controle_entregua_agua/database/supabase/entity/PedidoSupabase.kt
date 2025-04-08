package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.database.LocalDateSerializer
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusPedido
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class PedidoSupabase(
    val id: Long = 0,
    val cliente_id: Long = 0,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    @Serializable(with = LocalDateSerializer::class)
    val data_entrega: LocalDate = LocalDate.MIN,
    val troco: Double = 0.0,
    val status: StatusPedido = StatusPedido.PENDENTE,
    val valor_total: Double = 0.0
){
//    fun to(): Pedido{
//        return Pedido(
//            id = id,
//            data = data,
//            troco = troco.toDouble(),
//            status = status,
//            cliente_id = cliente_id,
//        )
//    }
//
//    companion object {
//        fun to(pedidoSupabase: PedidoSupabase): Pedido{
//            return Pedido(
//                id = pedidoSupabase.id,
//                data = pedidoSupabase.data,
//                troco = pedidoSupabase.troco.toDouble(),
//                status = pedidoSupabase.status,
//                cliente_id = pedidoSupabase.cliente_id,
//            )
//        }
//    }
}

enum class StatusPedidoSupabase(status: String) {
    PENDENTE("PENDENTE"),
    FINALIZADO("FINALIZADO"),
}
