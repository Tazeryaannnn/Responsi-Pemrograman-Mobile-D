# Digimon Blater 🦖

Aplikasi Android penjelajah data Digimon yang dikembangkan menggunakan **Kotlin** dan **Jetpack Compose**, mengimplementasikan arsitektur **MVVM** secara penuh tanpa XML Layout. Dibuat untuk memenuhi tugas Responsi Praktikum.

## 📸 Screenshot Aplikasi
 <img width="299" height="276" alt="WhatsApp Image 2026-10-09 at 11 18 34" src="https://github.com/user-attachments/assets/10b76732-1bd5-4346-b59c-9a2667312e78" />
<img width="610" height="1356" alt="WhatsApp Image 2026-10-09 at 11 18 35" src="https://github.com/user-attachments/assets/84ab8aad-b154-4519-bdb8-940dcc7862a4" />
<img width="610" height="1356" alt="WhatsApp Image 2026-10-09 at 11 18 35(1)" src="https://github.com/user-attachments/assets/d779c59e-d712-4c78-943a-9b91a28ce54e" />


## 🛠️ Penjelasan Teknis & Arsitektur (MVVM)
Aplikasi ini dibangun menggunakan arsitektur **Model-View-ViewModel (MVVM)** dengan pemisahan *layer* yang ketat:

1. **Model (`model/`)**: 
   Memanfaatkan *Data Class* dari Kotlin untuk memetakan respons JSON dari Digi-API. Terdapat penanganan *Null Safety* (`String?`) untuk mengantisipasi data gambar atau atribut yang kosong dari *server*.

2. **Network & Repository (`network/` & `repository/`)**:
   - Komunikasi jaringan menggunakan **Retrofit2** dengan *converter* **Gson**.
   - Pemanggilan ke `https://digi-api.com/api/v1/` menggunakan fungsi `suspend` di dalam `Coroutine` untuk memastikan proses *fetching* berjalan secara asinkron tanpa memblokir antarmuka utama.
   - `DigimonRepository` bertindak sebagai *Single Source of Truth* yang menjembatani data API ke ViewModel.

3. **ViewModel (`viewmodel/`)**:
   - Mengelola logika bisnis dan status layar menggunakan `StateFlow`.
   - Menggunakan `sealed class UiState` untuk merepresentasikan tiga kondisi mutlak: **Loading**, **Success** (berisi data), dan **Error** (berisi pesan kegagalan).

4. **View (`view/` & `MainActivity.kt`)**:
   - **100% Jetpack Compose** & **Material Design 3**.
   - `HomeScreen`: Memanfaatkan `LazyColumn` untuk merender daftar secara efisien dan `Coil (AsyncImage)` untuk memuat aset gambar dari URL. Layar akan merespons langsung terhadap perubahan `UiState`.
   - `DigimonDetailScreen`: Menerima argumen navigasi berupa `nama` Digimon dan menampilkan detail spesifik (Level, Attribute, Type).
   - **Navigation Compose** dipusatkan di `MainActivity` mengatur pergerakan antar dua *screens* secara dinamis.
