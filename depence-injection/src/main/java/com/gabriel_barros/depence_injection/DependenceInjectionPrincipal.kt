package com.gabriel_barros.depence_injection

import com.gabriel_barros.data_supabase.dependenceInjectionSupabase
import com.gabriel_barros.usecase.dependenceInjectionUsecase


val dependenceInjectionPrincipal = listOf (
    dependenceInjectionSupabase,
    dependenceInjectionUsecase,
)