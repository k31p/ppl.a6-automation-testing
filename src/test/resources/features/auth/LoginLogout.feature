@auth
Feature: Login dan Logout
  Sebagai pengguna sistem
  Saya ingin dapat masuk dan keluar dari akun saya
  Agar dapat mengakses fitur-fitur yang tersedia sesuai dengan peran saya

  @positif @TC-FR01-01
  Scenario Outline: Login berhasil dengan kredensial pengguna yang valid sebagai role pelajar
    Given Browser dibuka dan halaman Login dimuat
    When Pengguna memasukkan email "<email>"
    And Pengguna memasukkan kata sandi "<password>"
    And Pengguna menekan tombol Masuk
    Then Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard
    And Nama pengguna yang login ditampilkan

    Examples:
      | email               | password        |
      | roy@example.com     | ryosikimurasaki |

  @negatif @TC-FR01-02
  Scenario Outline: Login gagal dengan email yang tidak terdaftar
    Given Browser dibuka dan halaman Login dimuat
    When Pengguna memasukkan email "<email>"
    And Pengguna memasukkan kata sandi "<password>"
    And Pengguna menekan tombol Masuk
    Then Sistem menampilkan notifikasi "Email atau password salah"
    And Pengguna tetap berada di halaman Login

    Examples:
      | email               | password         |
      | roy@example.com     | roysukabelajar   |

  @positif @TC-FR02-01
  Scenario: Logout berhasil melalui navigasi profil pada header setelah login
    Given Pengguna sudah login dan berada di halaman Dashboard
    When Pengguna menekan Profil pada navigasi header
    And Pengguna menekan Keluar pada menu dropdown profil
    Then Sistem menghapus sesi dan mengarahkan ke halaman Login
    And Tidak ada pesan error yang ditampilkan
