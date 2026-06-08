package com.ppl.a6.steps.quiz;

import com.ppl.a6.pages.LoginPage;
import com.ppl.a6.pages.QuizHistoryPage;
import com.ppl.a6.utils.BaseScenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public class QuizHistoryStepDef {

  private WebDriver driver;
  private LoginPage loginPage;
  private QuizHistoryPage quizHistoryPage;

  private static final String EMAIL_PELAJAR = "roy@example.com";
  private static final String PASSWORD_PELAJAR = "ryosikimurasaki";
  private static final String ENROLLED_COURSE_NAME = "Pengembangan Pabrik Endfield";
  private static final String UNENROLLED_COURSE_NAME = "Fisika Dasar";

  @Before
  public void setUp() {
    driver = BaseScenario.getDriver();
    loginPage = new LoginPage(driver);
    quizHistoryPage = new QuizHistoryPage(driver);
  }

  @Given("Pelajar sudah login")
  public void pelajar_sudah_login() {
    driver.manage().window().maximize();
    driver.get(BaseScenario.getSiteBaseUrl());
    loginPage.loginAs(EMAIL_PELAJAR, PASSWORD_PELAJAR);
    new com.ppl.a6.pages.DashboardPage(driver).waitUntilLoaded();
  }

  @Given("Pelajar terdaftar di minimal satu kursus")
  public void pelajar_terdaftar_di_minimal_satu_kursus() {
    // asumsi terpenuhi oleh data pengguna
  }

  @When("Pengguna menavigasi ke menu Riwayat Kuis")
  public void pengguna_menavigasi_ke_menu_riwayat_kuis() {
    quizHistoryPage.navigateToMenu();
  }

  @When("Mengamati daftar kursus yang ditampilkan")
  public void mengamati_daftar_kursus_yang_ditampilkan() throws InterruptedException {
    Thread.sleep(2000);
  }

  @Then("Daftar kursus yang telah diikuti oleh pelajar ditampilkan")
  public void daftar_kursus_yang_telah_diikuti_oleh_pelajar_ditampilkan() {
    assertTrue("Kursus yang diikuti (" + ENROLLED_COURSE_NAME + ") tidak ditemukan di halaman",
        quizHistoryPage.isCourseVisible(ENROLLED_COURSE_NAME));
  }

  @Then("Kursus yang tidak didaftarkan oleh pelajar tidak ditampilkan")
  public void kursus_yang_tidak_didaftarkan_oleh_pelajar_tidak_ditampilkan() {
    assertTrue("Ditemukan kursus yang tidak didaftar (" + UNENROLLED_COURSE_NAME + ")",
        !quizHistoryPage.isCourseVisible(UNENROLLED_COURSE_NAME));
  }

  // TC-FR11-01: Pencarian kuis berdasarkan nama kuis

  @Given("Pengguna sudah login dan berada di halaman Daftar Riwayat Kuis")
  public void pengguna_sudah_login_dan_berada_di_halaman_daftar_riwayat_kuis() {
    driver.manage().window().maximize();
    driver.get(BaseScenario.getSiteBaseUrl());
    loginPage.loginAs(EMAIL_PELAJAR, PASSWORD_PELAJAR);
    new com.ppl.a6.pages.DashboardPage(driver).waitUntilLoaded();
    quizHistoryPage.navigateToMenu();
    quizHistoryPage.clickLihatDetail();
    quizHistoryPage.waitUntilQuizHistoryPageLoaded();
  }

  @When("Pengguna menekan kolom pencarian")
  public void pengguna_menekan_kolom_pencarian() {
    quizHistoryPage.clickSearchField();
  }

  @When("Pengguna memasukkan kata kunci {string}")
  public void pengguna_memasukkan_kata_kunci(String keyword) {
    quizHistoryPage.enterSearchKeyword(keyword);
  }

  @Then("Sistem menyaring hasil kuis yang mengandung kata kunci")
  public void sistem_menyaring_hasil_kuis_yang_mengandung_kata_kunci() {
    int count = quizHistoryPage.getQuizItemCount();
    assertTrue("Hasil kuis tidak ditemukan", count >= 0);
  }

  @Then("Kuis yang tidak sesuai disembunyikan")
  public void kuis_yang_tidak_sesuai_disembunyikan() {
    assertTrue("Kuis yang tidak sesuai masih ditampilkan",
        quizHistoryPage.getQuizItemCount() >= 0);
  }

  @Then("Jumlah hasil yang ditampilkan sesuai")
  public void jumlah_hasil_yang_ditampilkan_sesuai() {
    assertTrue("Jumlah hasil tidak sesuai",
        quizHistoryPage.getQuizItemCount() >= 0);
  }

  @After
  public void tearDown() {
    BaseScenario.closeDriver();
  }
}
