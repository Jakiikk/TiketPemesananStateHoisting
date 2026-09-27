# TiketPemesananStateHoisting

Aplikasi Android Pemesanan Tiket sederhana yang dibangun menggunakan **Jetpack Compose** dengan menerapkan pola **State Hoisting** dan konsep Unidirectional Data Flow (UDF).

## Fitur Utama
- **State Hoisting**: State dikelola di level parent (`TicketScreen`) dan diteruskan ke child composables melalui parameter/event.
- **Form Nama Pembeli**: Validasi input nama pembeli real-time.
- **Kuantitas & Harga Tiket**: Perhitungan harga total secara dinamis berdasarkan jumlah tiket.
- **Simulasi Proses Pesanan**: Menggunakan `LaunchedEffect` dan `delay` coroutine untuk mensimulasikan pemrosesan transaksi.
- **Kartu Status**: Menampilkan status pesanan dan indikator warna secara dinamis.

## Tampilan UI
- Didesain dengan komponen Material 3.
- Palette warna Hijau Tosca.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Minimum SDK**: 24
- **Target SDK**: 37
