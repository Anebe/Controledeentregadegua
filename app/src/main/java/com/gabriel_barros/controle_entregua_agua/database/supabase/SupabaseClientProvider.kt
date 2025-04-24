package com.gabriel_barros.controle_entregua_agua.database.supabase

import com.gabriel_barros.controle_entregua_agua.BuildConfig
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClientProvider {
    var schema: String = SUPABASE_SCHEMA.TESTE.toString()
    val supabase = createSupabaseClient(
        supabaseUrl = "https://vfqisgektavejjzqlayg.supabase.co",
        supabaseKey = BuildConfig.SUPABASE_KEY
    ) {
//        install(Auth)
        install(Postgrest) {}

    }
}
enum class SUPABASE_SCHEMA(val schemaName: String) {
    PUBLIC("public"),
    TESTE("teste");

    override fun toString(): String {
        return schemaName
    }
}
