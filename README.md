# Automation Testing PPL A6
Proyek ini digunakan untuk mengotomatisasi pengujian pada aplikasi JTK Learn. Test case mencakup modul Login dan Logout serta Riwayat Kuis berdasarkan dokumen 3A_029_Login & Logout dan TC-FR11-01_test result.xlsx dan 3A_011_Login & Quiz History_TC-FR01-03 TC-FR11-03_Test Result.xlsx

## Daftar Test Case

| ID | Modul | Tipe | Skenario |
|----|-------|------|----------|
| TC-FR01-01 | AUTH | Positif | Login berhasil dengan kredensial pengguna yang valid sebagai role pelajar |
| TC-FR01-02 | AUTH | Negatif | Login gagal dengan email yang tidak terdaftar |
| TC-FR01-03 | AUTH | Negatif | Login dengan Form Kosong |
| TC-FR02-01 | AUTH | Positif | Logout berhasil melalui navigasi profil pada header setelah login |
| TC-FR11-01 | QUIZ | Positif | Pencarian kuis berdasarkan nama kuis pada halaman Daftar Riwayat Kuis berhasil |
| TC-FR11-03 | QUIZ | Positif | Halaman Inisialisasi Menampilkan Daftar Kursus yang Diikuti |

## Tujuan Setiap Library

Proyek ini menggunakan tiga library utama yang masing-masing memiliki peran spesifik.

**JUnit 4** (`junit:junit:4.13.2`)

JUnit adalah kerangka kerja pengujian untuk Java. JUnit 4 digunakan sebagai runner utama untuk mengeksekusi test Cucumber. Anotasi `@RunWith(Cucumber.class)` memberitahu JUnit untuk menggunakan Cucumber sebagai mesin eksekusi test. Anotasi `@AfterClass` digunakan untuk membersihkan resource setelah semua skenario selesai.

**Cucumber 7** (`io.cucumber:cucumber-java:7.34.3` dan `io.cucumber:cucumber-junit:7.34.3`)

Cucumber adalah kerangka kerja Behavior Driven Development (BDD) yang memungkinkan penulisan test case dalam bahasa alami menggunakan format Gherkin. Cucumber versi 7 menggunakan paket `io.cucumber.*` untuk semua API. Dua modul digunakan:

- `cucumber-java`: Menyediakan anotasi step definitions (`@Given`, `@When`, `@Then`) dan hooks (`@Before`, `@After`).
- `cucumber-junit`: Menyediakan integrasi dengan JUnit 4 melalui `@RunWith(Cucumber.class)`.

**Selenium 4** (`org.seleniumhq.selenium:selenium-java:4.44.0`)

Selenium WebDriver adalah alat untuk mengotomatisasi browser web. Selenium 4 memiliki Selenium Manager bawaan yang secara otomatis mengunduh dan mengelola driver browser (chromedriver, geckodriver) tanpa konfigurasi manual.

## Cara Kerja Sistem

Sistem bekerja dalam tiga lapisan yang saling terhubung.

**Lapisan 1: Feature File (Gherkin)**

Feature file berisi skenario test dalam bahasa alami. Contoh dari `LoginLogout.feature`:

```gherkin
Feature: Login dan Logout
  Scenario Outline: Login berhasil dengan kredensial pengguna yang valid
    Given Browser dibuka dan halaman Login dimuat
    When Pengguna memasukkan email "<email>"
    And Pengguna memasukkan kata sandi "<password>"
    And Pengguna menekan tombol Masuk
    Then Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard
```

**Lapisan 2: Step Definitions (Java)**

Step definitions memetakan setiap baris Gherkin ke kode Java. Cucumber mencocokkan teks dari feature file dengan pola regex atau ekspresi Cucumber (`{string}`, `{int}`) yang dideklarasikan di anotasi.

```java
@When("Pengguna memasukkan email {string}")
public void pengguna_memasukkan_email(String email) {
    loginPage.enterEmail(email);
}
```

**Lapisan 3: Page Objects (Java)**

Page Object Model (POM) mengenkapsulasi locator dan method interaksi untuk setiap halaman. Step definitions tidak pernah memanggil `driver.findElement()` langsung, melainkan melalui method Page Object.

```
Feature File (Gherkin)
       |
       v
Step Definitions (@Given/@When/@Then)
       |
       v
Page Objects (LoginPage, DashboardPage, QuizHistoryPage)
       |
       v
Selenium WebDriver (ChromeDriver, FirefoxDriver)
       |
       v
Browser (Chrome, Firefox)
```

Alur eksekusi dimulai ketika Maven menjalankan `PplA6AutomationTest`. Cucumber membaca feature file, mencocokkan langkah dengan step definitions, yang kemudian memanggil method Page Object untuk berinteraksi dengan browser melalui Selenium WebDriver.

## Strategi Selenium WebDriver

### Manajemen Driver

`BaseScenario.java` mengelola siklus hidup WebDriver. Driver dibuat sekali dan digunakan kembali untuk semua skenario dalam satu sesi Maven.

```java
public static WebDriver getDriver() {
    if (null != driver) {
        return driver;
    }
    String envName = System.getProperty("envName", "local");
    String browserName = System.getProperty("browserName", "googlechrome");
    // buat driver sesuai browser
}
```

Selenium 4 menggunakan Selenium Manager yang secara otomatis mendeteksi versi Chrome/Firefox yang terinstal dan mengunduh driver yang sesuai. Tidak perlu menyimpan binary chromedriver di repositori.

### Strategi Pencarian Elemen (Locator Strategy)

Setiap elemen di halaman web ditemukan menggunakan pencari (locator). Prioritas pemilihan locator:

1. **By.cssSelector**: Paling cepat dan spesifik. Digunakan untuk elemen dengan class atau atribut unik.
2. **By.xpath**: Paling fleksibel. Digunakan ketika elemen tidak memiliki id atau class yang jelas.
3. **By.className**: Untuk elemen dengan class name yang unik.
4. **By.id**: Cepat tetapi elemen pada aplikasi React sering tidak memiliki atribut id.

Pada aplikasi React yang diuji, elemen input tidak memiliki atribut id sehingga digunakan XPath:

```java
private final By emailInput    = By.xpath("//input[@type='email']");
private final By searchInput   = By.cssSelector("input[placeholder='Search']");
private final By submitButton  = By.xpath("//button[contains(text(), 'Masuk')]");
```

### Manajemen State Antar Skenario

Hook `@After` memanggil `BaseScenario.closeDriver()` yang menghapus cookies setelah setiap skenario. Browser tetap terbuka untuk skenario berikutnya (menghemat waktu) tetapi session direset. Hook `@AfterClass` di runner memanggil `BaseScenario.quitDriver()` yang menutup browser setelah semua skenario selesai.

## Spesifikasi pom.xml

### Group dan Artifact

```xml
<groupId>com.ppl.a6</groupId>
<artifactId>ppl.a6-automation-testing</artifactId>
<version>1.0-SNAPSHOT</version>
```

### Plugin Compiler

Mengompilasi kode Java 21:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.15.0</version>
    <configuration>
        <source>21</source>
        <target>21</target>
    </configuration>
</plugin>
```

### Plugin Surefire (Test Runner)

Menjalankan test dan menyediakan system properties default. Semua properti bisa dioverride melalui command line `-D`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.6</version>
    <configuration>
        <systemPropertyVariables>
            <siteUrl>https://polban-space.cloudias79.com/jtk-learn/</siteUrl>
            <browserName>googlechrome</browserName>
            <envName>local</envName>
        </systemPropertyVariables>
    </configuration>
</plugin>
```

### Konfigurasi Cucumber

File `src/test/resources/cucumber.properties`:

```
cucumber.plugin=pretty, html:target/cucumber, json:target/cucumber.json
cucumber.filter.tags=@focus
```

- `pretty`: Cetak hasil test yang diformat dengan warna di konsol.
- `html:target/cucumber`: Hasilkan laporan HTML di `target/cucumber`.
- `json:target/cucumber.json`: Hasilkan laporan JSON di `target/cucumber.json`.
- `cucumber.filter.tags`: Tag default (bisa dioverride dengan `-Dcucumber.filter.tags`).

## Implementasi Page Object Model (POM)

### Struktur POM

```
com.ppl.a6.pages
├── LoginPage.java        Halaman login
├── DashboardPage.java    Halaman dashboard (setelah login)
└── QuizHistoryPage.java  Halaman riwayat kuis
```

### Prinsip POM

Setiap Page Object memiliki tiga bagian:

1. **Locators**: By variables yang mendefinisikan cara menemukan elemen. Bersifat private.
2. **Constructor**: Menerima `WebDriver` dan membuat `WebDriverWait`.
3. **Action Methods**: Method publik untuk berinteraksi dengan halaman.

Contoh dari `LoginPage.java`:

```java
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By emailInput    = By.xpath("//input[@type='email']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By submitButton  = By.xpath("//button[contains(text(), 'Masuk')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Action methods
    public void enterEmail(String email) {
        WebElement field = driver.findElement(emailInput);
        field.clear();
        field.sendKeys(email);
    }

    public void loginAs(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }
}
```

### DashboardPage

Mengelola interaksi setelah login. Menggunakan pola multiple locators dengan `clickFirstClickable()` untuk menangani variasi struktur halaman yang tidak diketahui secara pasti:

```java
public void clickProfile() {
    List<By> locators = List.of(
        By.cssSelector("li.nav-name.dropdown > a.nav-link"),
        By.cssSelector("img.profile-img"),
        By.xpath("//*[contains(@class, 'profile')]")
    );
    clickFirstClickable(locators);
}
```

### Step Definitions dengan POM

Step definitions tidak lagi extends BaseScenario. Mereka mendapatkan driver melalui `BaseScenario.getDriver()` dan membuat instance Page Object:

```java
public class AuthStepDef {
    private WebDriver driver;
    private LoginPage loginPage;
    private DashboardPage dashboardPage;

    @Before
    public void setUp() {
        driver = BaseScenario.getDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
    }
}
```

## Struktur Proyek Lengkap

```
ppl.a6-automation-testing/
├── .gitignore
├── .sdkmanrc                        (Java 21 via SDKMAN)
├── LICENSE
├── README.md
├── pom.xml
├── cucumber.properties              (konfigurasi Cucumber)
├── 3A_029_Login & Logout dan TC-FR11-01_test result.xlsx
├── drivers/                         (referensi, tidak dipakai)
│   ├── README.md
│   ├── CHROME_VERSION.txt
│   ├── chromedriver
│   └── chromedriver.exe
└── src/
    └── test/
        ├── java/com/ppl/a6/
        │   ├── PplA6AutomationTest.java          (runner JUnit + Cucumber)
        │   ├── utils/BaseScenario.java            (manajemen WebDriver)
        │   ├── pages/
        │   │   ├── LoginPage.java                 (halaman login)
        │   │   ├── DashboardPage.java             (halaman dashboard)
        │   │   └── QuizHistoryPage.java           (halaman riwayat kuis)
        │   └── steps/
        │       ├── auth/AuthStepDef.java          (step login/logout)
        │       └── quiz/QuizHistoryStepDef.java   (step riwayat kuis)
        └── resources/
            └── features/
                ├── auth/LoginLogout.feature
                └── quiz/QuizHistory.feature
```

## Contoh Penggunaan Cepat

### Menjalankan Semua Test

```bash
mvn test
```

Hasil: Laporan HTML di `target/cucumber` dan JSON di `target/cucumber.json`.

### Menjalankan Test Berdasarkan Tag

```bash
# Hanya test modul autentikasi
mvn test -Dcucumber.filter.tags="@auth"

# Hanya test login positif
mvn test -Dcucumber.filter.tags="@TC-FR01-01"

# Hanya test modul quiz
mvn test -Dcucumber.filter.tags="@quiz"

# Test positif saja
mvn test -Dcucumber.filter.tags="@positif"
```

### Melihat Laporan

```bash
# Buka laporan HTML di browser (Linux)
xdg-open target/cucumber

# Atau buka manual di browser dari file manager
```

## Mengubah Opsi

### Mengubah Browser

Default browser adalah Google Chrome. Untuk mengganti:

```bash
# Firefox
mvn test -DbrowserName="firefox"

# Safari (macOS)
mvn test -DbrowserName="safari"
```

### Mengubah Lingkungan Eksekusi

```bash
# Lokal (default)
mvn test -DenvName="local"

# Remote (TestingBot/SauceLabs)
mvn test -DenvName="remote" -DremoteWebDriver="http://key@hub.testingbot.com:4444/wd/hub"
```

### Mengubah URL Aplikasi

Default URL ditentukan di `pom.xml`. Untuk menguji di URL berbeda:

```bash
mvn test -DsiteUrl="https://staging.example.com/"
```

### Kombinasi Opsi

```bash
mvn test \
  -DbrowserName="firefox" \
  -Dcucumber.filter.tags="@auth" \
  -DsiteUrl="https://staging.example.com/"
```

Urutan prioritas properti (tertinggi ke terendah):
1. Command line `-D` (saat run)
2. `pom.xml` systemPropertyVariables (saat kompilasi)
3. Default di kode Java `System.getProperty("key", "default")`

## Teknologi

- Java 21 + JUnit 4
- Cucumber 7 (BDD)
- Selenium 4 (WebDriver) dengan Selenium Manager
- Maven 3.9+

## Kredit

- [zenoyu](https://github.com/zenoyu/java-maven-cucumber-selenium) untuk template Maven Cucumber Selenium yang digunakan sebagai dasar proyek ini
