package com.kelompok3.bloomu.presentation.authentication

import com.kelompok3.bloomu.supabase.supabase
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
        // SESUAI DOCUMENTATION
        supabase.auth.resetPasswordForEmail(
            email = email,
            redirectUrl = "bloomu://reset-password"
        )
    }

    suspend fun updatePassword(password: String) {
        // SESUAI DOCUMENTATION: updateUser untuk mengganti password setelah login via link
        supabase.auth.updateUser {
            this.password = password
        }
    }

    // Fungsi logout jika diperlukan nanti
    suspend fun signOut() {
        supabase.auth.signOut()
    }
}
