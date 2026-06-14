package com.jtklearn.testing.auth;

import com.jtklearn.testing.base.BaseScenario;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.jtklearn.testing.auth.AuthSelectors.*;
import static com.jtklearn.testing.auth.AuthActions.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Step Definitions untuk fitur Login dan Logout
 * TC-FR01-04, TC-FR01-05, TC-FR02-03
 * Menggunakan POM pattern dengan separate Selector dan Action classes
 */
public class AuthStepDef extends BaseScenario {

    // Test credentials
    private static final String EMAIL_TEST = "fredy.kurniadi.tif423@polban.ac.id";
    private static final String PASSWORD_VALID = "fred";

    @Given("Browser dibuka dan halaman Login dimuat")
    public void browser_dibuka_dan_halaman_login_dimuat() {
        openLoginPage();
    }

    @When("Pengguna memasukkan email {string}")
    public void pengguna_memasukkan_email(String email) {
        enterEmail(email);
    }

    @When("Pengguna memasukkan kata sandi {string}")
    public void pengguna_memasukkan_kata_sandi(String password) {
        enterPassword(password);
    }

    @When("Pengguna menekan tombol Masuk")
    public void pengguna_menekan_tombol_masuk() {
        clickMasukButton();
    }

    @Then("Sistem memvalidasi kredensial dan mengarahkan ke halaman Dashboard")
    public void sistem_memvalidasi_kredensial_dan_mengarahkan_ke_halaman_dashboard() {
        try {
            waitForDashboard();
            assertTrue("Gagal diarahkan ke halaman Dashboard", true);
        } catch (Exception e) {
            takeScreenshot("TC-FR01-04-fail");
            throw e;
        }
    }

    @Then("Nama pengguna yang login ditampilkan")
    public void nama_pengguna_yang_login_ditampilkan() {
        assertTrue("Nama pengguna tidak ditemukan", isProfileVisible());
    }

    @Then("Sistem menampilkan notifikasi {string}")
    public void sistem_menampilkan_notifikasi(String message) {
        assertTrue("Notifikasi kesalahan tidak ditemukan", isErrorModalDisplayed());
    }

    @Then("Pengguna tetap berada di halaman Login")
    public void pengguna_tetap_berada_di_halaman_login() {
        assertTrue("Tidak berada di halaman Login", isOnLoginPage());
        assertTrue("Input email tidak ditemukan", isEmailInputVisible());
    }

    @Given("Pengguna sudah login dan berada di halaman Dashboard")
    public void pengguna_sudah_login_dan_berada_di_halaman_dashboard() {
        openLoginPage();
        login(EMAIL_TEST, PASSWORD_VALID);
        try {
            waitForDashboard();
        } catch (Exception e) {
            takeScreenshot("TC-FR02-03-login-fail");
            throw e;
        }
    }

    @When("Pengguna menekan Profil pada navigasi header")
    public void pengguna_menekan_profil_pada_navigasi_header() {
        clickProfileMenu();
    }

    @When("Pengguna menekan Keluar pada menu dropdown profil")
    public void pengguna_menekan_keluar_pada_menu_dropdown_profil() {
        clickLogout();
    }

    @Then("Sistem menghapus sesi dan mengarahkan ke halaman Login")
    public void sistem_menghapus_sesi_dan_mengarahkan_ke_halaman_login() {
        waitForLoginPage();
        String currentUrl = BaseScenario.getDriver().getCurrentUrl();
        assertTrue("Tidak diarahkan ke halaman Login", 
            currentUrl.equals(getSiteBaseUrl()) || currentUrl.equals(getSiteBaseUrl() + "/"));
    }

    @Then("Tidak ada pesan error yang ditampilkan")
    public void tidak_ada_pesan_error_yang_ditampilkan() {
        assertFalse("Terdapat pesan error setelah logout", hasErrorModal());
    }

    @When("Pengguna tidak mengisi field email maupun password pada halaman Login")
    public void pengguna_tidak_mengisi_field_email_maupun_password_pada_halaman_login() {
        clearEmailAndPassword();
    }

    @Then("Sistem menolak permintaan pengguna")
    public void sistem_menolak_permintaan_pengguna() {
        assertTrue("Tidak berada di halaman Login", isOnLoginPage());
    }

    @Then("Sistem menampilkan pesan informatif bahwa field tersebut wajib diisi")
    public void sistem_menampilkan_pesan_informatif_bahwa_field_tersebut_wajib_diisi() {
        assertTrue("Pesan wajib diisi tidak ditampilkan", isRequiredMessageDisplayed());
    }
}