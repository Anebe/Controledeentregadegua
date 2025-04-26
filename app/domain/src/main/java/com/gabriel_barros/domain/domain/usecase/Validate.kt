package com.gabriel_barros.domain.domain.usecase

interface Validate<T> {
    fun validateOrThrow(data: T)

}