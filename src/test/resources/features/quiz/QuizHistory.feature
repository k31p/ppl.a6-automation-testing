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
