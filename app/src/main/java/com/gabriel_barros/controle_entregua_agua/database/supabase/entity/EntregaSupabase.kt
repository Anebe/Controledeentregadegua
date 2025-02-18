package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.database.LocalDateSerializer
import com.gabriel_barros.controle_entregua_agua.domain.entity.Entrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.Pedido
import com.gabriel_barros.controle_entregua_agua.domain.entity.StatusEntrega
import com.gabriel_barros.controle_entregua_agua.domain.entity.TipoEntregador
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EntregaSupabase(
    val id: Long = 0,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
    val status: StatusEntrega = StatusEntrega.PENDENTE,
//    val pedido: Pedido,
    val pedido_id: Long,
){
//    fun to():Entrega{
//        return Entrega(
//            id = id,
//            data = data,
//            entregador = entregador,
//            pedido_id=  pedido_id,
//        )
//    }
}