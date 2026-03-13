package com.kelompok3.bloomu.supabase

import com.russhwolf.settings.Settings
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.SettingsSessionManager
import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://ehkqdbfqocyzgvxyhyut.supabase.co",
    supabaseKey = "sb_publishable_uKoALeln5THkWPHp_T0bUw_HEabbqf8"
) {
    install(Auth) {
        sessionManager = SettingsSessionManager(Settings())
        scheme = "bloomu"
        host = "reset-password"
    }
    install(ComposeAuth){
        googleNativeLogin(serverClientId = "138131137737-0feoata5vkin85flototo06snk1jqtod.apps.googleusercontent.com")
    }
    install(Postgrest)
}

//key API supabase
