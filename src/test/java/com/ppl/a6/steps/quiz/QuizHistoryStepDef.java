package com.ppl.a6.quiz;

import com.ppl.a6.utils.BaseScenario;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class QuizHistoryStepDef extends BaseScenario {

    private static final String QUIZ_HISTORY_MENU_XPATH = "//a[contains(text(), 'Riwayat Kuis')]"; 
    private static final String COURSE_CARD_XPATH = "//div[contains(@class, 'course-card')]";
    
    private static final String EMAIL_PELAJAR = "roy@example.com";
    private static final String PASSWORD_PELAJAR = "ryosikimurasaki";
    private static final String ENROLLED_COURSE_NAME = "Matematika Diskrit"; 

    @Given("Pelajar sudah login")
    public void pelajar_sudah_login() {
        getDriver();
        driver.manage().window().maximize();
        driver.get(getSiteBaseUrl());
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        
        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='email']")));
        emailField.clear();
        emailField.sendKeys(EMAIL_PELAJAR);

        WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
        passwordField.clear();
        passwordField.sendKeys(PASSWORD_PELAJAR);

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Masuk')]"));
        submitButton.click();

        wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    @Given("Pelajar terdaftar di minimal satu kursus")
    public void pelajar_terdaftar_di_minimal_satu_kursus() {
    }

    @When("Pengguna menavigasi ke menu Riwayat Kuis")
    public void pengguna_menavigasi_ke_menu_riwayat_kuis() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement menuRiwayat = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(QUIZ_HISTORY_MENU_XPATH)));
        menuRiwayat.click();
        
    }

    @When("Mengamati daftar kursus yang ditampilkan")
    public void mengamati_daftar_kursus_yang_ditampilkan() throws InterruptedException {
        Thread.sleep(2000); 
    }

    @Then("Daftar kursus yang telah diikuti oleh pelajar ditampilkan")
    public void daftar_kursus_yang_telah_diikuti_oleh_pelajar_ditampilkan() {
        List<WebElement> courseCards = driver.findElements(By.xpath(COURSE_CARD_XPATH));
        assertTrue("Halaman inisialisasi kosong, daftar kursus tidak muncul", courseCards.size() > 0);
        
        boolean isCourseFound = false;
        for (WebElement card : courseCards) {
            if (card.getText().contains(ENROLLED_COURSE_NAME)) {
                isCourseFound = true;
                break;
            }
        }
        assertTrue("Kursus yang diikuti (" + ENROLLED_COURSE_NAME + ") tidak ditemukan di layar", isCourseFound);
    }

    @Then("Kursus yang tidak didaftarkan oleh pelajar tidak ditampilkan")
    public void kursus_yang_tidak_didaftarkan_oleh_pelajar_tidak_ditampilkan() {
        // Asumsi "Fisika Dasar" adalah kursus yang TIDAK di-enroll oleh user ini
        String unenrollCourse = "Fisika Dasar"; 
        
        List<WebElement> courseCards = driver.findElements(By.xpath(COURSE_CARD_XPATH));
        for (WebElement card : courseCards) {
            assertTrue("Ditemukan kursus yang tidak didaftar (" + unenrollCourse + ")", 
                       !card.getText().contains(unenrollCourse));
        }
    }

    @After
    public void tearDown() {
        BaseScenario.closeDriver();
    }
}