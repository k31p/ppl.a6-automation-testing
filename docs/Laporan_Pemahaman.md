# Laporan Pemahaman Praktikum BDD Testing - POM Implementation

## Kelompok: 3A_010

---

## 1. Knowledge & Terminology BDD with POM

### 1.1 BDD Concepts (Behavior Driven Development)
- **Gherkin**: Domain-specific language untuk mendeskripsikan behavior aplikasi dalam format Given-When-Then
- **Feature File**: File dengan ekstensi `.feature` berisi scenario yang ditulis dalam bahasa Gherkin
- **Step Definitions**: Kode Java yang mengimplementasikan setiap step dari feature file
- **Test Runner**: Class yang menjalankan Cucumber test suite
- **Cucumber Options**: Konfigurasi untuk test execution (plugin, tags, features, glue)

### 1.2 POM Concepts (Page Object Model)
- **POM**: Design pattern untuk membuat test lebih maintainable dan readable
- **Selectors**: Definisi locator elemen web (By.xpath, By.id, By.className, dll.)
- **Actions**: Method yang melakukan operasi pada elemen (click, sendKeys, getText, dll.)
- **Step Definitions**: Layer yang menghubungkan feature files dengan POM classes
- **Base Scenario**: Parent class untuk shared utilities dan WebDriver management

---

## 2. Struktur POM (Page Object Model) yang Diterapkan

### 2.1 POM Separation Pattern
```
src/test/java/com/jtklearn/testing/
├── base/
│   └── BaseScenario.java            # WebDriver management & utilities
├── auth/                            # Module Login/Logout
│   ├── AuthSelectors.java           # All selectors (POM - Selectors)
│   ├── AuthActions.java             # All actions (POM - Actions)  
│   └── AuthStepDef.java             # Step definitions (Cucumber)
├── quiz/                            # Module Quiz History
│   ├── QuizSelectors.java           # All selectors (POM - Selectors)
│   ├── QuizActions.java             # All actions (POM - Actions)
│   └── QuizStepDef.java             # Step definitions (Cucumber)
└── JTKLearnTestRunner.java          # Cucumber test runner
```

### 2.2 Benefit POM Separation
1. **Reusability**: Selector dan action bisa digunakan ulang di berbagai test case
2. **Maintainability**: Perubahan locator hanya perlu dilakukan di satu file (Selectors.java)
3. **Readability**: Step definitions menjadi clean dan mudah dipahami
4. **Scalability**: Mudah menambahkan module baru dengan pattern yang konsisten

---

## 3. Cara Pakai Praktikum Folder Ini

### 3.1 Setup Awal
```bash
# Clone atau download folder JTKLearn-BDD-Testing
cd JTKLearn-BDD-Testing

# Compile project untuk memastikan tidak ada error
mvn compile

# Install dependencies (hanya pertama kali atau ada dependency baru)
mvn clean install
```

### 3.2 Konfigurasi Environment Variables
Set sebelum menjalankan test:
```bash
# Untuk Windows CMD
set siteUrl=https://jtklearn.polban.ac.id
set browserName=googlechrome
set envName=local

# Untuk Windows PowerShell
$env:siteUrl="https://jtklearn.polban.ac.id"
$env:browserName="googlechrome"
$env:envName="local"
```

### 3.3 Menjalankan Test Berdasarkan Module
```bash
# Run semua test
mvn test

# Run module Auth saja
mvn test -Dcucumber.filter.tags="@auth"

# Run module Quiz saja
mvn test -Dcucumber.filter.tags="@quiz"

# Run berdasarkan test case ID
mvn test -Dcucumber.filter.tags="@TC-FR01-04"
mvn test -Dcucumber.filter.tags="@TC-FR01-05"
mvn test -Dcucumber.filter.tags="@TC-FR02-03"
mvn test -Dcucumber.filter.tags="@TC-FR11-05"
```

### 3.4 Workflow Development

#### 3.4.1 Menambah Feature Baru
1. Buat file `.feature` di `src/test/resources/features/`
2. Tulis scenario dalam format Gherkin
3. Buat folder module baru di `src/test/java/com/jtklearn/testing/`
4. Buat 3 file: `Selectors.java`, `Actions.java`, dan `StepDef.java`
5. Update `JTKLearnTestRunner.java` jika perlu

#### 3.4.2 Menambah Test Case pada Module yang Ada
1. Tambah scenario di file `.feature` yang sudah ada
2. Implementasikan method di `StepDef.java`
3. Gunakan method dari `Actions.java` atau buat method baru jika perlu

#### 3.4.3 Update Locator/Elemen Web
1. Edit file `Selectors.java` (misalnya `AuthSelectors.java`)
2. Update selector/element locator
3. Tidak perlu edit `Actions.java` atau `StepDef.java` kecuali ada perubahan behavior

---

## 4. Code Examples & Patterns

### 4.1 Selector Pattern (AuthSelectors.java)
```java
// SEMUA selector dalam satu class
public class AuthSelectors {
    // Login selectors
    public static final By EMAIL_INPUT = By.xpath("//input[@type='email']");
    public static final By PASSWORD_INPUT = By.xpath("//input[@type='password']");
    public static final By SUBMIT_BUTTON = By.xpath("//button[contains(text(), 'Masuk')]");
    
    // Logout selectors  
    public static final By PROFILE_MENU = By.xpath("//*[contains(text(), 'Profil')]");
    public static final By LOGOUT_OPTION = By.xpath("//*[contains(text(), 'Keluar')]");
}
```

### 4.2 Action Pattern (AuthActions.java)
```java
public class AuthActions {
    // Action method yang menerima parameter
    public static void enterEmail(String email) {
        WebElement emailField = driver.findElement(AuthSelectors.EMAIL_INPUT);
        emailField.clear();
        emailField.sendKeys(email);
    }
    
    // Action method tanpa parameter
    public static void clickMasukButton() {
        driver.findElement(AuthSelectors.SUBMIT_BUTTON).click();
    }
    
    // Composite action (multiple actions)
    public static void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickMasukButton();
    }
}
```

### 4.3 Step Definition Pattern (AuthStepDef.java)
```java
public class AuthStepDef {
    @When("Pengguna memasukkan email {string}")
    public void pengguna_memasukkan_email(String email) {
        // CLEAN: hanya memanggil action method
        AuthActions.enterEmail(email);
    }
    
    @Then("Sistem mengarahkan ke halaman Dashboard")
    public void sistem_mengarahkan_ke_halaman_dashboard() {
        // CLEAN: hanya memanggil action method dan assertion
        assertTrue(AuthActions.isDashboardDisplayed());
    }
}
```

---

## 5. Debugging dan Troubleshooting

### 5.1 Common Issues
- **Selector not found**: Cek XPath/CSS Selector di browser DevTools
- **Timeout waiting for element**: Tambah wait time atau cek network latency
- **Test failure on assertions**: Verifikasi expected condition di aplikasi web

### 5.2 Debug Mode
```bash
# Run test dengan debug output
mvn test -Dtest=JTKLearnTestRunner

# Skip tests (compile only)
mvn compile -DskipTests=true
```

### 5.3 Check Reports
Setelah test selesai:
1. Buka `target/cucumber-reports/cucumber-pretty.html` di browser
2. Lihat detail setiap scenario (passed/failed)
3. Screenshot otomatis tersimpan di `target/screenshots/` untuk test yang fail

---

## 6. Best Practices yang Diterapkan

### 6.1 POM Best Practices
1. **Separate concerns**: Selectors dan Actions dalam file terpisah
2. **Static imports**: Gunakan `import static` untuk cleaner code di StepDef
3. **Method naming**: Gunakan nama method yang deskriptif dalam Bahasa Inggris atau Indonesia
4. **Reusable methods**: Buat method composite untuk sequence actions yang sering digunakan

### 6.2 BDD Best Practices  
1. **Given-When-Then**: Format scenario dengan jelas
2. **Tagging**: Gunakan tags untuk grouping dan filtering (@auth, @quiz, @TC-FR01-04)
3. **Examples**: Gunakan scenario outline dengan examples untuk data-driven testing
4. **Background**: Gunakan background untuk setup yang sama di semua scenario dalam satu feature

### 6.3 Java Coding Standards
1. **Naming convention**: CamelCase untuk class/method, UPPER_CASE untuk constants
2. **Comments**: Tambah komentar untuk method yang complex
3. **Exception handling**: Gunakan try-catch untuk operation yang mungkin fail
4. **Assertions**: Gunakan JUnit assertions untuk verification

---

## 7. Integration dengan Tools Lain

### 7.1 CI/CD Integration (Jenkins/GitHub Actions)
```yaml
# Contoh GitHub Actions
name: BDD Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
      - name: Run BDD Tests
        run: mvn test
        env:
          siteUrl: ${{ secrets.SITE_URL }}
```

### 7.2 Jira/Test Management Integration
- Test case ID dalam tags (contoh: @TC-FR01-04) bisa diintegrasikan dengan Jira
- Generate XML/JSON report untuk import ke test management tools

---

## 8. Extensions dan Future Improvements

### 8.1 Potensi Enhancement
1. **Parallel execution**: Run tests in parallel untuk faster execution
2. **Cross-browser testing**: Support untuk Chrome, Firefox, Edge
3. **API testing**: Tambah layer API testing bersama UI testing
4. **Performance testing**: Integrasi dengan JMeter/Gatling

### 8.2 Advanced POM Patterns
1. **Page Factory**: Gunakan @FindBy annotations
2. **Component Pattern**: Buat reusable components (Header, Footer, Modal)
3. **Builder Pattern**: Untuk complex form filling

---

## 9. References dan Learning Resources

### 9.1 Documentation
- [Cucumber Documentation](https://cucumber.io/docs)
- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [Maven Documentation](https://maven.apache.org/guides/)

### 9.2 Tutorials
- [Cucumber Java Tutorial](https://cucumber.io/docs/guides/10-minute-tutorial/)
- [Selenium WebDriver Tutorial](https://www.selenium.dev/documentation/webdriver/)
- [BDD with Cucumber & Selenium](https://www.toolsqa.com/cucumber/cucumber-tutorial/)

### 9.3 Example Projects
- [BDD Automation Framework](https://github.com/zenoyu/java-maven-cucumber-selenium)
- [Selenium Test Framework](https://github.com/SeleniumHQ/selenium)

---

## 10. Team Contribution Guidelines

### 10.1 Workflow untuk Tim
1. **Clone repository** ke local machine
2. **Create branch** untuk feature/bugfix
3. **Implement changes** sesuai pembagian tugas
4. **Run tests locally** sebelum commit
5. **Commit and push** dengan descriptive message
6. **Create pull request** untuk review

### 10.2 Code Review Checklist
- [ ] POM pattern diikuti (Selectors, Actions, StepDef terpisah)
- [ ] Step definitions clean dan readable
- [ ] Selectors menggunakan format yang tepat (XPath/CSS)
- [ ] Actions methods memiliki error handling
- [ ] Tests pass dengan data yang valid
- [ ] Screenshot capability bekerja untuk fail cases

---

**Last Updated**: June 4, 2026  
**Version**: 2.0 (POM Implementation)