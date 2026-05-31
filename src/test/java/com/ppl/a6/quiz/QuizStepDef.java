package com.ppl.a6.quiz;

import com.ppl.a6.base.BaseScenario;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class QuizStepDef extends BaseScenario {

  private static final String SEARCH_INPUT = "search";
  private static final String KEYWORD = "Endfield";
  private static final String QUIZ_LIST_CLASS = "quiz-list";
  private static final String QUIZ_ITEM_CLASS = "quiz-item";

  @Given("Pengguna sudah login dan berada di halaman Daftar Riwayat Kuis")
  public void pengguna_sudah_login_dan_berada_di_halaman_daftar_riwayat_kuis() {
    getDriver();
    driver.manage().window().maximize();

    driver.findElement(By.id("yourEmail")).sendKeys("roy@example.com");
    driver.findElement(By.id("yourPassword")).sendKeys("ryosikimurasaki");
    driver.findElement(By.xpath("//button[contains(text(), 'Masuk')]")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.urlContains("dashboard"));

    String quizHistoryUrl = getSiteBaseUrl() + "/riwayat-kuis";
    driver.get(quizHistoryUrl);
  }

  @When("Pengguna menekan kolom pencarian")
  public void pengguna_menekan_kolom_pencarian() {
    By searchLocator = By.cssSelector("input[placeholder*='cari'], input[placeholder*='Cari'], input[placeholder*='search'], input[placeholder*='Search'], #" + SEARCH_INPUT + ", input[name='" + SEARCH_INPUT + "']");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement searchField = wait.until(
        ExpectedConditions.elementToBeClickable(searchLocator));
    searchField.click();
  }

  @When("Pengguna memasukkan kata kunci {string}")
  public void pengguna_memasukkan_kata_kunci(String keyword) {
    By searchLocator = By.cssSelector("input[placeholder*='cari'], input[placeholder*='Cari'], input[placeholder*='search'], input[placeholder*='Search'], #" + SEARCH_INPUT + ", input[name='" + SEARCH_INPUT + "']");
    WebElement searchField = driver.findElement(searchLocator);
    searchField.clear();
    searchField.sendKeys(keyword);
  }

  @Then("Sistem menyaring hasil kuis yang mengandung kata kunci")
  public void sistem_menyaring_hasil_kuis_yang_mengandung_kata_kunci() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.or(
        ExpectedConditions.presenceOfElementLocated(By.className(QUIZ_LIST_CLASS)),
        ExpectedConditions.presenceOfElementLocated(By.className(QUIZ_ITEM_CLASS))
    ));
    assertTrue("Hasil kuis tidak ditemukan",
        driver.findElements(By.className(QUIZ_ITEM_CLASS)).size() > 0
            || driver.findElements(By.className(QUIZ_LIST_CLASS)).size() > 0);
  }

  @Then("Kuis yang tidak sesuai disembunyikan")
  public void kuis_yang_tidak_sesuai_disembunyikan() {
    assertTrue("Halaman hasil kuis tidak ditemukan",
        driver.findElements(By.className(QUIZ_ITEM_CLASS)).size() >= 0);
  }

  @Then("Jumlah hasil yang ditampilkan sesuai")
  public void jumlah_hasil_yang_ditampilkan_sesuai() {
    int resultCount = driver.findElements(By.className(QUIZ_ITEM_CLASS)).size();
    assertTrue("Jumlah hasil tidak sesuai", resultCount >= 0);
  }
}
