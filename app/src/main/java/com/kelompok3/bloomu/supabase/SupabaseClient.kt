package com.kelompok3.bloomu.supabase

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient

val supabase = createSupabaseClient(
    supabaseUrl = "https://ehkqdbfqocyzgvxyhyut.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVoa3FkYmZxb2N5emd2eHloeXV0Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzE3NDA3NjMsImV4cCI6MjA4NzMxNjc2M30.fOQQXoxLzVJ34jy7AaXGKt2cLAapnBohLQv2K5Pm3SY"
) {
    install(Auth)
}

