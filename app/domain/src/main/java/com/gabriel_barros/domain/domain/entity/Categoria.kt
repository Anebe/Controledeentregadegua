package com.gabriel_barros.domain.domain.entity

import kotlinx.serialization.Serializable


@Serializable
data class Categoria(
    val id: Long = 0,
    val categoria_pai: Long,
    val nome: String
) {
    companion object {
        fun emptyCategoria(): Categoria {
            return Categoria(
                categoria_pai = 0,
                nome = "",
            )
        }
    }
}