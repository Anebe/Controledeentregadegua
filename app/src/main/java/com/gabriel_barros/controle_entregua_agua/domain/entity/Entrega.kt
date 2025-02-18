package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Entrega (
    val id: Long = 0,
    val data: LocalDate,
    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
    val status: StatusEntrega = StatusEntrega.PENDENTE,
//    val pedido: Pedido,
    val pedido_id: Long,
)

enum class StatusEntrega{
    FINALIZADO,
    ANDAMENTO,
    PENDENTE
}
enum class TipoEntregador {
    COMERCIO,
    CLIENTE,
}

