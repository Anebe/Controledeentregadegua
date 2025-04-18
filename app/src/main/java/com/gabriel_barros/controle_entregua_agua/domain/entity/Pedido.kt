package com.gabriel_barros.controle_entregua_agua.domain.entity

import com.gabriel_barros.controle_entregua_agua.database.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Pedido(
    val id: Long,
    val cliente_id: Long,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val data_entrega: LocalDate, //TODO alterar pra um nome que dê a ideia de data escolhida pelo cliente
    val troco: Double,//Substiuir por observação pois dá na mesma
    val status: StatusPedido,
    val valor_total: Double,
) {
    companion object {
        fun emptyPedido(): Pedido {
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
