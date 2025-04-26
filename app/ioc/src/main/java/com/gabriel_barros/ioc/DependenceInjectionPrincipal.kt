package com.gabriel_barros.ioc

import com.gabriel_barros.supabase.dependenceInjectionSupabase
import com.gabriel_barros.usecase.service.dependenceInjectionUsecase


val dependenceInjectionPrincipal = listOf (
    dependenceInjectionSupabase,
    dependenceInjectionUsecase,
)