package com.gabriel_barros.controle_entregua_agua.domain.usecase

interface Validate<T> {
    fun validateOrThrow(data: T)
}