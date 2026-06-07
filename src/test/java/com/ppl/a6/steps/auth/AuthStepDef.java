package com.ppl.a6.steps.auth;

import com.ppl.a6.pages.DashboardPage;
import com.ppl.a6.pages.LoginPage;
import com.ppl.a6.utils.BaseScenario;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class AuthStepDef {

  private WebDriver driver;
  private LoginPage loginPage;
  private DashboardPage dashboardPage;

  private static final String EMAIL_TEST = "roy@example.com";
  private static final String PASSWORD_VALID = "ryosikimurasaki";

  @Before
  public void setUp() {
    driver = BaseScenario.getDriver();
    loginPage = new LoginPage(driver);
    dashboardPage = new DashboardPage(driver);
  }

  @Given("Browser dibuka dan halaman Login dimuat")
  public void browser_dibuka_dan_halaman_login_dimuat() {
    driver.manage().window().maximize();
    driver.get(BaseScenario.getSiteBaseUrl());
    loginPage.waitUntilLoaded();
  }

  @When("Pengguna memasukkan email {string}")
  public void pengguna_memasukkan_email(String email) {
    loginPage.enterEmail(email);
  }

  @When("Pengguna memasukkan kata sandi {string}")
  public void pengguna_memasukkan_kata_sandi(String password) {
    loginPage.enterPassword(password);
  }

  @When("Pengguna menekan tombol Masuk")
  public void pengguna_menekan_tombol_masuk() {
    loginPage.clickSubmit();
  }

  @Then("Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard")
  public void sistem_memvalidasi_kredensial_dan_mengarahkan_ke_halaman_dashboard() {
    dashboardPage.waitUntilLoaded();
    assertTrue("Gagal diarahkan ke halaman Dashboard",
        driver.getCurrentUrl().contains("dashboard"));
  }

  @Then("Nama pengguna yang login ditampilkan")
  public void nama_pengguna_yang_login_ditampilkan() {
    assertTrue("Nama pengguna tidak ditemukan", dashboardPage.isProfileVisible());
  }

  @Then("Sistem menampilkan notifikasi {string}")
  public void sistem_menampilkan_notifikasi(String message) {
    assertTrue("Notifikasi kesalahan tidak ditemukan", loginPage.isErrorModalDisplayed());
  }

  @Then("Pengguna tetap berada di halaman Login")
  public void pengguna_tetap_berada_di_halaman_login() {
    assertTrue("Tidak berada di halaman Login",
        loginPage.isOnLoginPage(BaseScenario.getSiteBaseUrl()));
    assertTrue("Input email tidak ditemukan", loginPage.isEmailInputVisible());
  }

  @Given("Pengguna sudah login dan berada di halaman Dashboard")
  public void pengguna_sudah_login_dan_berada_di_halaman_dashboard() {
    driver.manage().window().maximize();
    driver.get(BaseScenario.getSiteBaseUrl());
    loginPage.loginAs(EMAIL_TEST, PASSWORD_VALID);
    dashboardPage.waitUntilLoaded();
  }

  @When("Pengguna menekan Profil pada navigasi header")
  public void pengguna_menekan_profil_pada_navigasi_header() {
    dashboardPage.clickProfile();
  }

  @When("Pengguna menekan Keluar pada menu dropdown profil")
  public void pengguna_menekan_keluar_pada_menu_dropdown_profil() {
    dashboardPage.clickLogout();
    driver.navigate().to(BaseScenario.getSiteBaseUrl());
  }

  @Then("Sistem menghapus sesi dan mengarahkan ke halaman Login")
  public void sistem_menghapus_sesi_dan_mengarahkan_ke_halaman_login() {
    loginPage.waitForRedirectToLogin();
    assertTrue("Tidak diarahkan ke halaman Login",
        loginPage.isOnLoginPage(BaseScenario.getSiteBaseUrl()));
  }

  @Then("Tidak ada pesan error yang ditampilkan")
  public void tidak_ada_pesan_error_yang_ditampilkan() {
    assertTrue("Terdapat pesan error setelah logout", loginPage.isErrorModalNotDisplayed());
  }

  @After
  public void tearDown() {
    BaseScenario.closeDriver();
    try {
      if (driver != null) {
        driver.get(BaseScenario.getSiteBaseUrl());
      }
    } catch (Exception e) {
      // ignore
    }
  }
}
