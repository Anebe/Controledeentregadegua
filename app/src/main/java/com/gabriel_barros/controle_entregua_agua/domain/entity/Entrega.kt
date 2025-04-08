package com.gabriel_barros.controle_entregua_agua.domain.entity

import kotlinx.serialization.Serializable
import java.time.LocalDate

data class Entrega (
    val id: Long,
    val data: LocalDate,
    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
    val status: StatusEntrega = StatusEntrega.PENDENTE,
    val pedido_id: Long,
){
    companion object{
        fun emptyEntrega(): Entrega{
            return Entrega(
                id = 0,
                        data = LocalDate.now(),
                        entregador = TipoEntregador.COMERCIO,
                        status = StatusEntrega.PENDENTE,
                        pedido_id = 0,
            )
        }
    }
}

enum class StatusEntrega{
    FINALIZADO,
    ANDAMENTO,
    PENDENTE
}
enum class TipoEntregador {
    COMERCIO,
    CLIENTE,
}

