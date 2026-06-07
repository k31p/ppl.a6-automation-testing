@quiz
Feature: Riwayat Kuis
  Sebagai pengguna dengan role pelajar
  Saya ingin mencari kuis berdasarkan nama
  Agar dapat melihat riwayat kuis yang spesifik

  @positif @TC-FR11-01
  Scenario Outline: Pencarian kuis berdasarkan nama kuis pada halaman Daftar Riwayat Kuis berhasil dengan kata kunci yang ditemukan
    Given Pengguna sudah login dan berada di halaman Daftar Riwayat Kuis
    When Pengguna menekan kolom pencarian
    And Pengguna memasukkan kata kunci "<keyword>"
    Then Sistem menyaring hasil kuis yang mengandung kata kunci
    And Kuis yang tidak sesuai disembunyikan
    And Jumlah hasil yang ditampilkan sesuai

    Examples:
      | keyword   |
      | karbit  |

  @positif @TC-FR11-03
    Scenario: Halaman Inisialisasi Menampilkan Daftar Kursus yang Diikuti
      Given Pelajar sudah login
      And Pelajar terdaftar di minimal satu kursus
      When Pengguna menavigasi ke menu Riwayat Kuis
      And Mengamati daftar kursus yang ditampilkan
      Then Daftar kursus yang telah diikuti oleh pelajar ditampilkan
      And Kursus yang tidak didaftarkan oleh pelajar tidak ditampilkan
