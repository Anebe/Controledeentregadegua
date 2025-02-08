package com.gabriel_barros.controle_entregua_agua.database.supabase.entity

import com.gabriel_barros.controle_entregua_agua.model.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Pedido(
    val id: Long = 0,
    val cliente_id: Long = 0,
    val qtd: Int = 0,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    val troco: Int = 0
)
