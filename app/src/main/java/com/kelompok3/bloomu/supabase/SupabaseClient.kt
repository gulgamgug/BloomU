package com.kelompok3.bloomu.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://ehkqdbfqocyzgvxyhyut.supabase.co",
    supabaseKey = "sb_publishable_uKoALeln5THkWPHp_T0bUw_HEabbqf8"
) {
    install(Auth)
    install(Postgrest)
}

