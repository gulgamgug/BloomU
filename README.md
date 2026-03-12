# BloomU 
---
BloomU adalah aplikasi self-awareness untuk mahasiswa yang membantu pengguna melacak kondisi emosinya secara harian dan memahami pola mood mereka melalui insight mingguan sederhana. Dengan microlearning ringan seputar kesehatan mental, BloomU mendorong mahasiswa lebih sadar terhadap dirinya dan menjaga kesejahteraan belajar.


##  Fitur Utama
---
Berikut adalah fitur-fitur yang tersedia di BloomU:

- **Sistem Autentikasi** (MVP)
    - Login & Registrasi Akun.
    - Verifikasi OTP via Email.
    - Login menggunakan Google (Native Sign-In).
    - Lupa kata sandi & Reset kata sandi.
- **Daily Check-In** (MVP)
    - Pelacakan mood harian dengan emoji.
    - Penilaian singkat di 3 domain: Mental, Fisik, dan Akademik.
    - Mini Diary untuk mencatat perasaan harian.
- **Daily Fun Fact** (MVP)
  - Fakta unik terkait psikologi dan kesehatan mental yang berganti-ganti tiap user membuka aplikasi
- **Hasil Assessment & Micro-Actions** (MVP)
    - Analisis skor berdasarkan hasil check-in.
    - Rekomendasi tindakan nyata (Micro-actions) yang dipersonalisasi.
- **Kalender Mood & Analitik** (MVP)
    - Visualisasi riwayat mood dalam sebulan.
    - Grafik distribusi mood untuk memantau tren kesehatan mental.
    - Ringkasan aktivitas mingguan di dashboard.
- **Onboarding**
  - Pengenalan nilai utama aplikasi bagi pengguna baru.
- **Misi Harian (Daily Missions)**
    - Empat kategori misi (Fokus, Istirahat, Kebiasaan Kecil, Energi).
    - Sistem kategorisasi misi sesuai kebutuhan pengguna.
    - Pelacakan progres misi harian yang otomatis di-reset setiap hari.
- **Profil & Pengaturan**
    - Pengaturan notifikasi harian untuk pengingat check-in.
    - Logout Akun

## Tech Stack
---
BloomU dibangun dengan teknologi modern Android:

- **Bahasa**: Kotlin
- **UI Framework**: Jetpack Compose
- **Backend-as-a-Service**: Supabase
    - **PostgreSQL**: Untuk penyimpanan data pengguna dan hasil check-in.
    - **GoTrue (Auth)**: Untuk manajemen sesi dan autentikasi.
- **Local Storage**: Multiplatform Settings dan DataStore (Key-value storage untuk preferensi lokal).
- **Navigation**: Jetpack Navigation Compose dengan Type-Safe Routes (Kotlinx Serialization). .
- **Concurrency**: Kotlin Coroutines & Flow.

## Arsitektur Aplikasi
---
BloomU mengikuti pola arsitektur MVVM:

1.  **View (UI Layer)**: Dibangun menggunakan Jetpack Compose. State UI diobservasi dari ViewModel untuk mereaksi perubahan data secara reaktif.
2.  **ViewModel (State Holder)**: Mengelola logika bisnis UI, memproses input pengguna, dan berinteraksi dengan layanan data (Supabase). Menggunakan `State` untuk mengekspos data ke View.
3.  **Model (Data Layer)**: 
    - **Supabase Service**: Menangani komunikasi network dengan database dan layanan autentikasi.
    - **Preference Manager**: Menangani penyimpanan data persisten lokal.

---
