package com.gabriel_barros.controle_entregua_agua.domain.entity

data class Categoria(
    val id: Long,
    val categoria_pai: Long,
    val nome: String
)
{
    companion object{
        fun emptyCategoria(): Categoria{
            return Categoria(
                id = 0,
                        categoria_pai = 0,
                    nome = "",
            )
        }
    }
}