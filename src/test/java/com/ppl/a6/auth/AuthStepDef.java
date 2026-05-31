package com.ppl.a6.auth;

import com.ppl.a6.base.BaseScenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AuthStepDef extends BaseScenario {

  private static final String EMAIL_INPUT = "yourEmail";
  private static final String PASSWORD_INPUT = "yourPassword";
  private static final String SUBMIT_BUTTON = "Masuk";
  private static final String EMAIL_TEST = "roy@example.com";
  private static final String PASSWORD_VALID = "ryosikimurasaki";
  private static final String PASSWORD_INVALID = "roysukabelajar";
  private static final String ERROR_MODAL_HEADING = "Kesalahan!";

  @Before
  public void setUp() {
    getDriver();
  }

  @Given("Browser dibuka dan halaman Login dimuat")
  public void browser_dibuka_dan_halaman_login_dimuat() {
    getDriver();
    driver.manage().window().maximize();
    driver.get(getSiteBaseUrl());
  }

  @When("Pengguna memasukkan email {string}")
  public void pengguna_memasukkan_email(String email) {
    WebElement emailField = driver.findElement(By.id(EMAIL_INPUT));
    emailField.clear();
    emailField.sendKeys(email);
  }

  @When("Pengguna memasukkan kata sandi {string}")
  public void pengguna_memasukkan_kata_sandi(String password) {
    WebElement passwordField = driver.findElement(By.id(PASSWORD_INPUT));
    passwordField.clear();
    passwordField.sendKeys(password);
  }

  @When("Pengguna menekan tombol Masuk")
  public void pengguna_menekan_tombol_masuk() {
    WebElement submitButton = driver.findElement(
        By.xpath("//button[contains(text(), '" + SUBMIT_BUTTON + "')]"));
    submitButton.click();
  }

  @Then("Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard")
  public void sistem_memvalidasi_kredensial_dan_mengarahkan_ke_halaman_dashboard() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    boolean isDashboard = wait.until(
        ExpectedConditions.or(
            ExpectedConditions.urlContains("dashboard"),
            ExpectedConditions.urlContains("Dashboard"),
            ExpectedConditions.presenceOfElementLocated(By.className("dashboard"))
        )
    ) != null;
    assertTrue("Gagal diarahkan ke halaman Dashboard", isDashboard);
  }

  @Then("Nama pengguna yang login ditampilkan")
  public void nama_pengguna_yang_login_ditampilkan() {
    boolean profileVisible = driver.findElements(By.className("navbar")).size() > 0
        || driver.findElements(By.className("profile")).size() > 0;
    assertTrue("Nama pengguna tidak ditemukan", profileVisible);
  }

  @Then("Sistem menampilkan notifikasi {string}")
  public void sistem_menampilkan_notifikasi(String message) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement errorModal = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.xpath(
            "//h2[contains(text(), '" + ERROR_MODAL_HEADING + "')]")));
    assertTrue("Notifikasi kesalahan tidak ditemukan", errorModal.isDisplayed());
  }

  @Then("Pengguna tetap berada di halaman Login")
  public void pengguna_tetap_berada_di_halaman_login() {
    String currentUrl = driver.getCurrentUrl();
    boolean onLoginPage = currentUrl.equals(getSiteBaseUrl())
        || currentUrl.equals(getSiteBaseUrl() + "/")
        || currentUrl.contains("login");
    assertTrue("Tidak berada di halaman Login", onLoginPage);
    assertTrue("Input email tidak ditemukan",
        driver.findElement(By.id(EMAIL_INPUT)).isDisplayed());
  }

  @Given("Pengguna sudah login dan berada di halaman Dashboard")
  public void pengguna_sudah_login_dan_berada_di_halaman_dashboard() {
    getDriver();
    driver.manage().window().maximize();
    driver.get(getSiteBaseUrl());

    WebElement emailField = driver.findElement(By.id(EMAIL_INPUT));
    emailField.clear();
    emailField.sendKeys(EMAIL_TEST);

    WebElement passwordField = driver.findElement(By.id(PASSWORD_INPUT));
    passwordField.clear();
    passwordField.sendKeys(PASSWORD_VALID);

    WebElement submitButton = driver.findElement(
        By.xpath("//button[contains(text(), '" + SUBMIT_BUTTON + "')]"));
    submitButton.click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.or(
        ExpectedConditions.urlContains("dashboard"),
        ExpectedConditions.urlContains("Dashboard")
    ));
  }

  @When("Pengguna menekan Profil pada navigasi header")
  public void pengguna_menekan_profil_pada_navigasi_header() {
    By profileLocator = By.xpath("//nav//*[contains(text(), 'Profil')]");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement profileMenu = wait.until(
        ExpectedConditions.elementToBeClickable(profileLocator));
    profileMenu.click();
  }

  @When("Pengguna menekan Keluar pada menu dropdown profil")
  public void pengguna_menekan_keluar_pada_menu_dropdown_profil() {
    By logoutLocator = By.xpath("//*[contains(text(), 'Keluar')]");
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement logoutButton = wait.until(
        ExpectedConditions.elementToBeClickable(logoutLocator));
    logoutButton.click();
  }

  @Then("Sistem menghapus sesi dan mengarahkan ke halaman Login")
  public void sistem_menghapus_sesi_dan_mengarahkan_ke_halaman_login() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.or(
        ExpectedConditions.urlContains("login"),
        ExpectedConditions.urlContains("Login"),
        ExpectedConditions.presenceOfElementLocated(By.id(EMAIL_INPUT))
    ));
    boolean onLogin = driver.getCurrentUrl().equals(getSiteBaseUrl())
        || driver.getCurrentUrl().equals(getSiteBaseUrl() + "/");
    assertTrue("Tidak diarahkan ke halaman Login", onLogin);
  }

  @Then("Tidak ada pesan error yang ditampilkan")
  public void tidak_ada_pesan_error_yang_ditampilkan() {
    boolean hasErrorModal = driver.findElements(
        By.xpath("//h2[contains(text(), '" + ERROR_MODAL_HEADING + "')]")).size() > 0;
    assertFalse("Terdapat pesan error setelah logout", hasErrorModal);
  }

  @After
  public void tearDown() {
    BaseScenario.closeDriver();
  }
}
