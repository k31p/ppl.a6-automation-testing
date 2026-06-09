# JTKLearn BDD Testing Project

Proyek ini digunakan untuk mengotomatisasi pengujian pada aplikasi JTKLearn menggunakan Selenium WebDriver dengan Cucumber (BDD).

## Daftar Test Case

| TC ID | Modul | Jenis | Deskripsi |
|-------|-------|-------|-----------|
| TC-FR01-04 | AUTH | Positif | Login berhasil dengan kredensial pengguna yang valid |
| TC-FR01-05 | AUTH | Negatif | Login gagal dengan kredensial yang tidak valid |
| TC-FR02-03 | AUTH | Positif | Logout berhasil melalui navigasi profil pada header |
| TC-FR11-05 | QUIZ | Positif | Melihat daftar riwayat kuis |

## Prasyarat

- Java JDK 11 atau lebih tinggi
- Maven 3.9+
- Chrome/Chromium terbaru

## Cara Menjalankan

### Jalankan semua test case:
```bash
mvn test
```

### Jalankan test case spesifik berdasarkan tag:
```bash
mvn test -Dcucumber.filter.tags="@TC-FR01-04"
mvn test -Dcucumber.filter.tags="@auth"
mvn test -Dcucumber.filter.tags="@quiz"
```

### Menjalankan dengan browser berbeda:
```bash
mvn test -DbrowserName="googlechrome"
mvn test -DbrowserName="firefox"
```

## Struktur Proyek

```
src/
├── test/
│   ├── java/com/jtklearn/testing/
│   │   ├── JTKLearnTestRunner.java      (test runner)
│   │   ├── base/
│   │   │   └── BaseScenario.java        (WebDriver management)
│   │   ├── auth/
│   │   │   └── AuthStepDef.java         (step definitions login/logout)
│   │   └── quiz/
│   │       └── QuizStepDef.java         (step definitions riwayat kuis)
│   └── resources/features/
│       ├── auth/
│       │   └── LoginLogout.feature      (TC-FR01-04, TC-FR01-05, TC-FR02-03)
│       └── quiz/
│           └── QuizHistory.feature      (TC-FR11-05)
```

## Test Reports

Setelah test selesai, report dapat ditemukan di:
- HTML Report: `target/cucumber-reports/cucumber-pretty.html`
- JSON Report: `target/cucumber-reports/cucumber.json`
- JUnit XML: `target/cucumber-reports/cucumber.xml`

## Pembagian Tugas

| Anggota | Test Case |
|---------|-----------|
| Anggota A | TC-FR01-04 (Login valid) |
| Anggota B | TC-FR01-05 (Login invalid) |
| Anggota C | TC-FR02-03 (Logout) + TC-FR11-05 (Quiz History) |

## Konfigurasi

### Mengubah Kredensial Test
Edit file `AuthStepDef.java` dan `QuizStepDef.java`:
```java
private static final String EMAIL_TEST = "email_anda@example.com";
private static final String PASSWORD_VALID = "password_anda";
```

### Mengubah URL Aplikasi
Edit `pom.xml` pada bagian:
```xml
<systemPropertyVariables>
    <siteUrl>https://polban-space.cloudias79.com/jtk-learn/</siteUrl>
</systemPropertyVariables>
```

## Teknologi

- Java 11 + JUnit 4
- Cucumber 7 (BDD)
- Selenium 4 (WebDriver)
- Maven

## Kredit

- Template berdasarkan [zenoyu/java-maven-cucumber-selenium](https://github.com/zenoyu/java-maven-cucumber-selenium)
- Referensi: [k31p/ppl.a6-automation-testing](https://github.com/k31p/ppl.a6-automation-testing)

## License

MIT License
