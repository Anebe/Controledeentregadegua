package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Pedido(
    val id: Long,
    val cliente_id: Long,
    val data: LocalDate,
    val data_entrega: LocalDate,
    val troco: Double,
    val status: StatusPedido,
    val valor_total: Double,
){
    companion object{
        fun emptyPedido(): Pedido{
            return Pedido(
                id = 0,
                        cliente_id = 0,
                        data = LocalDate.now(),
                        data_entrega = LocalDate.now(),
                        troco = 0.0,
                        status = StatusPedido.PENDENTE,
                        valor_total = 0.0,
            )
        }
    }
}

enum class StatusPedido {
    PENDENTE,
    FINALIZADO,
}
