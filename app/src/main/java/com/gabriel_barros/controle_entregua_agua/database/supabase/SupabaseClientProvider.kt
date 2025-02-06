package com.gabriel_barros.controle_entregua_agua.database.supabase

import com.gabriel_barros.controle_entregua_agua.BuildConfig
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://vfqisgektavejjzqlayg.supabase.co",
        supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
        install(Auth)
        install(Postgrest)
        //install other modules
    }
}
