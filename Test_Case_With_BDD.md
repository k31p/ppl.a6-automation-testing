# Test Case Document - JTKLearn BDD Testing

## Kelompok: 3A_010

---

## Daftar Test Case

| TC ID | Modul | Jenis | Deskripsi | File Feature |
|-------|-------|-------|-----------|--------------|
| TC-FR01-04 | AUTH | Positif | Login berhasil dengan kredensial pengguna yang valid | LoginLogout.feature |
| TC-FR01-05 | AUTH | Negatif | Login gagal dengan kredensial yang tidak valid | LoginLogout.feature |
| TC-FR02-03 | AUTH | Positif | Logout berhasil melalui navigasi profil pada header | LoginLogout.feature |
| TC-FR11-05 | QUIZ | Positif | Melihat daftar riwayat kuis | QuizHistory.feature |

---

## Detail Test Case

### TC-FR01-04: Login berhasil dengan kredensial valid

| Field | Value |
|-------|-------|
| **ID** | TC-FR01-04 |
| **Modul** | Authentication |
| **Jenis** | Positif |
| **Pre-condition** | Browser dibuka dan halaman Login dimuat |
| **Test Data** | Email: fredy.kurniadi.tif423@polban.ac.id, Password: fred |

**Test Steps:**
1. Pengguna memasukkan email valid
2. Pengguna memasukkan kata sandi valid
3. Pengguna menekan tombol Masuk

**Expected Result:**
- Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard
- Nama pengguna yang login ditampilkan

**Gherkin:**
```gherkin
@positif @TC-FR01-04
Scenario Outline: Login berhasil dengan kredensial pengguna yang valid
  Given Browser dibuka dan halaman Login dimuat
  When Pengguna memasukkan email "<email>"
  And Pengguna memasukkan kata sandi "<password>"
  And Pengguna menekan tombol Masuk
  Then Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard
  And Nama pengguna yang login ditampilkan

  Examples:
    | email           | password                 |
    | fredy.kurniadi.tif423@polban.ac.id | fred     |
```

---

### TC-FR01-05: Login gagal dengan kredensial tidak valid

| Field | Value |
|-------|-------|
| **ID** | TC-FR01-05 |
| **Modul** | Authentication |
| **Jenis** | Negatif |
| **Pre-condition** | Browser dibuka dan halaman Login dimuat |
| **Test Data** | Email: invalid@example.com, Password: wrongpassword |

**Test Steps:**
1. Pengguna memasukkan email tidak valid
2. Pengguna memasukkan kata sandi tidak valid
3. Pengguna menekan tombol Masuk

**Expected Result:**
- Sistem menampilkan notifikasi "Kesalahan!"
- Pengguna tetap berada di halaman Login

**Gherkin:**
```gherkin
@negatif @TC-FR01-05
Scenario Outline: Login gagal dengan kredensial yang tidak valid
  Given Browser dibuka dan halaman Login dimuat
  When Pengguna memasukkan email "<email>"
  And Pengguna memasukkan kata sandi "<password>"
  And Pengguna menekan tombol Masuk
  Then Sistem menampilkan notifikasi "Kesalahan!"
  And Pengguna tetap berada di halaman Login

  Examples:
    | email               | password      |
    | invalid@example.com | wrongpassword |
```

---

### TC-FR02-03: Logout berhasil

| Field | Value |
|-------|-------|
| **ID** | TC-FR02-03 |
| **Modul** | Authentication |
| **Jenis** | Positif |
| **Pre-condition** | Pengguna sudah login dan berada di halaman Dashboard |

**Test Steps:**
1. Pengguna menekan Profil pada navigasi header
2. Pengguna menekan Keluar pada menu dropdown profil

**Expected Result:**
- Sistem menghapus sesi dan mengarahkan ke halaman Login
- Tidak ada pesan error yang ditampilkan

**Gherkin:**
```gherkin
@positif @TC-FR02-03
Scenario: Logout berhasil melalui navigasi profil pada header setelah login
  Given Pengguna sudah login dan berada di halaman Dashboard
  When Pengguna menekan Profil pada navigasi header
  And Pengguna menekan Keluar pada menu dropdown profil
  Then Sistem menghapus sesi dan mengarahkan ke halaman Login
  And Tidak ada pesan error yang ditampilkan
```

---

### TC-FR11-05: Melihat daftar riwayat kuis

| Field | Value |
|-------|-------|
| **ID** | TC-FR11-05 |
| **Modul** | Quiz |
| **Jenis** | Positif |
| **Pre-condition** | Pengguna sudah login dan berada di halaman Dashboard |

**Test Steps:**
1. Pengguna membuka halaman Daftar Riwayat Kuis

**Expected Result:**
- Sistem menampilkan daftar riwayat kuis
- Kolom tanggal ditampilkan
- Kolom skor ditampilkan

**Gherkin:**
```gherkin
@positif @TC-FR11-05
Scenario: Melihat daftar riwayat kuis pada halaman Daftar Riwayat Kuis
  Given Pengguna sudah login dan berada di halaman Dashboard
  When Pengguna membuka halaman Daftar Riwayat Kuis
  Then Sistem menampilkan daftar riwayat kuis
  And Kolom tanggal ditampilkan
  And Kolom skor ditampilkan
```

---

## Test Result Summary

| TC ID | Tanggal Test | Tester | Status | Catatan |
|-------|--------------|--------|--------|---------|
| TC-FR01-04 | - | Anggota A | Pending | - |
| TC-FR01-05 | - | Anggota B | Pending | - |
| TC-FR02-03 | - | Anggota C | Pending | - |
| TC-FR11-05 | - | Anggota C | Pending | - |

---

## Cara Menjalankan Test

### Semua test:
```bash
mvn test
```

### Test spesifik:
```bash
mvn test -Dcucumber.filter.tags="@TC-FR01-04"
mvn test -Dcucumber.filter.tags="@TC-FR01-05"
mvn test -Dcucumber.filter.tags="@TC-FR02-03"
mvn test -Dcucumber.filter.tags="@TC-FR11-05"
```

### By module:
```bash
mvn test -Dcucumber.filter.tags="@auth"
mvn test -Dcucumber.filter.tags="@quiz"
```
