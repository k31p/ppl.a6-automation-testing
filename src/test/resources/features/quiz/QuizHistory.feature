@quiz
Feature: Riwayat Kuis
  Sebagai pengguna dengan role pelajar
  Saya ingin melihat riwayat kuis yang pernah saya kerjakan
  Agar dapat memantau progress belajar saya

  @positif @TC-FR11-05
  Scenario: TC-FR11-05 Melihat daftar riwayat kuis pada halaman Daftar Riwayat Kuis
    Given Pengguna sudah login dan berada di halaman Dashboard
    When Pengguna membuka halaman Daftar Riwayat Kuis
    Then Sistem menampilkan daftar riwayat kuis
    And Kolom tanggal ditampilkan
    And Kolom skor ditampilkan

  @positif @TC-FR11-01
  Scenario Outline: TC-FR11-01 Pencarian kuis berdasarkan nama kuis pada halaman Daftar Riwayat Kuis berhasil dengan kata kunci yang ditemukan
    Given Pengguna sudah login dan berada di halaman Daftar Riwayat Kuis
    When Pengguna menekan kolom pencarian
    And Pengguna memasukkan kata kunci "<keyword>"
    Then Sistem menyaring hasil kuis yang mengandung kata kunci
    And Kuis yang tidak sesuai disembunyikan
    And Jumlah hasil yang ditampilkan sesuai

    Examples:
      | keyword |
      | karbit  |

  @positif @TC-FR11-03
  Scenario: TC-FR11-03 Halaman Inisialisasi Menampilkan Daftar Kursus yang Diikuti
    Given Pelajar sudah login
    And Pelajar terdaftar di minimal satu kursus
    When Pengguna menavigasi ke menu Riwayat Kuis
    And Mengamati daftar kursus yang ditampilkan
    Then Daftar kursus yang telah diikuti oleh pelajar ditampilkan
    And Kursus yang tidak didaftarkan oleh pelajar tidak ditampilkan
