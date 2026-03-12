package com.kelompok3.bloomu.supabase

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object AuthService {

    suspend fun signIn(email: String, password: String) {
        supabase.auth.signInWith(Email) {
            this.email = email
            this.password = password
        }
    }

    suspend fun signUp(email: String, password: String, name: String) {
        supabase.auth.signUpWith(Email) {
            this.email = email
            this.password = password
            data = buildJsonObject { put("name", name) }
        }
    }

    suspend fun sendResetPasswordEmail(email: String) {
        supabase.auth.resetPasswordForEmail(
            email = email,
            redirectUrl = "bloomu://reset-password"
        )
    }

    suspend fun updatePassword(password: String) {
        supabase.auth.updateUser {
            this.password = password
        }
    }


    suspend fun signOut() {
        supabase.auth.signOut()
    }
}