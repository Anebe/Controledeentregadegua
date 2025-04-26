package com.gabriel_barros.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

internal object SupabaseClientProvider {
    var schema: String = SUPABASE_SCHEMA.TESTE.toString()
    val supabase = createSupabaseClient(
        supabaseUrl = System.getProperty("SUPABASE_URL"),
        supabaseKey = System.getProperty("SUPABASE_KEY")
    ) {
        install(Auth)
        install(Postgrest)
    }
}
enum class SUPABASE_SCHEMA(val schemaName: String) {
    PUBLIC("public"),
    TESTE("teste");

    override fun toString(): String {
        return schemaName
    }
}
