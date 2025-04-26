package com.gabriel_barros.ioc

import com.gabriel_barros.supabase.dependenceInjectionSupabase
import com.gabriel_barros.usecase.service.dependenceInjectionUsecase
import org.koin.dsl.module


val dependenceInjectionPrincipal = module {
    includes(dependenceInjectionSupabase)
    includes(dependenceInjectionUsecase)
}