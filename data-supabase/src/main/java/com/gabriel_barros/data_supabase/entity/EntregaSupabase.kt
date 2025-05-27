package com.gabriel_barros.data_supabase.entity

import com.gabriel_barros.data_supabase.LocalDateSerializer
import com.gabriel_barros.domain.entity.TipoEntregador
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class EntregaSupabase(
    val id: Long = 0,
    @Serializable(with = LocalDateSerializer::class)
    val data: LocalDate = LocalDate.MIN,
    val entregador: TipoEntregador = TipoEntregador.COMERCIO,
    val frete_id: Long,
)