# Automation Testing PPL A6

Proyek ini digunakan untuk mengotomatisasi pengujian pada aplikasi JTK Learn. Test case mencakup modul Login dan Logout serta Riwayat Kuis berdasarkan dokumen `3A_029_Login & Logout dan TC-FR11-01_test result.xlsx`.

## Daftar Test Case

| ID | Modul | Tipe | Skenario |
|----|-------|------|----------|
| TC-FR01-01 | AUTH | Positif | Login berhasil dengan kredensial pengguna yang valid sebagai role pelajar |
| TC-FR01-02 | AUTH | Negatif | Login gagal dengan email yang tidak terdaftar |
| TC-FR02-01 | AUTH | Positif | Logout berhasil melalui navigasi profil pada header setelah login |
| TC-FR11-01 | QUIZ | Positif | Pencarian kuis berdasarkan nama kuis pada halaman Daftar Riwayat Kuis berhasil |

## Prasyarat

- Java 21 (Temurin) - dikelola melalui SDKMAN
- Maven 3.9+
- Chrome/Chromium terbaru

## Cara Menjalankan

Jalankan semua test case:

```
mvn test
```

Jalankan test case spesifik berdasarkan tag:

```
mvn test -Dcucumber.filter.tags="@TC-FR01-01"
mvn test -Dcucumber.filter.tags="@auth"
mvn test -Dcucumber.filter.tags="@quiz"
```

Menjalankan dengan browser berbeda:

```
mvn test -DbrowserName="googlechrome"
mvn test -DbrowserName="firefox"
```

## Struktur Proyek

```
src/
├── test/
│   ├── java/com/ppl/a6/
│   │   ├── PplA6AutomationTest.java    (runner)
│   │   ├── base/BaseScenario.java       (WebDriver management)
│   │   ├── common/CommonStepDef.java    (step definitions umum)
│   │   ├── auth/AuthStepDef.java        (step definitions login/logout)
│   │   └── quiz/QuizStepDef.java        (step definitions riwayat kuis)
│   └── resources/features/
│       ├── auth/LoginLogout.feature
│       └── quiz/QuizHistory.feature
```

## Teknologi

- Java 21 + JUnit 4
- Cucumber 7 (BDD)
- Selenium 4 (WebDriver)
- Maven

## Kredit

- [zenoyu](https://github.com/zenoyu/java-maven-cucumber-selenium) untuk template Maven Cucumber Selenium yang digunakan sebagai dasar proyek ini
