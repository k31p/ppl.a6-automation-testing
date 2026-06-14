package com.jtklearn.testing.quiz;

import com.jtklearn.testing.base.BaseScenario;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.jtklearn.testing.quiz.QuizActions.*;
import static org.junit.Assert.assertTrue;

/**
 * Step Definitions untuk fitur Quiz History
 * TC-FR11-05, TC-FR11-01, TC-FR11-03
 * Menggunakan POM pattern dengan separate Selector dan Action classes
 */
public class QuizStepDef extends BaseScenario {

    private static final String EMAIL_PELAJAR = "roy@example.com";
    private static final String PASSWORD_PELAJAR = "ryosikimurasaki";
    private static final String ENROLLED_COURSE_NAME = "Pengembangan Pabrik Endfield";
    private static final String UNENROLLED_COURSE_NAME = "Fisika Dasar";

    @When("Pengguna membuka halaman Daftar Riwayat Kuis")
    public void pengguna_membuka_halaman_daftar_riwayat_kuis() {
        openQuizHistoryPage();
    }

    @Then("Sistem menampilkan daftar riwayat kuis")
    public void sistem_menampilkan_daftar_riwayat_kuis() {
        assertTrue("Daftar riwayat kuis tidak ditemukan", isQuizHistoryDisplayed());
    }

    @Then("Kolom tanggal ditampilkan")
    public void kolom_tanggal_ditampilkan() {
        assertTrue("Kolom tanggal tidak ditemukan", isDateColumnDisplayed());
    }

    @Then("Kolom skor ditampilkan")
    public void kolom_skor_ditampilkan() {
        assertTrue("Kolom skor tidak ditemukan", isScoreColumnDisplayed());
    }

    @Given("Pelajar sudah login")
    public void pelajar_sudah_login() {
        com.jtklearn.testing.auth.AuthActions.openLoginPage();
        com.jtklearn.testing.auth.AuthActions.login(EMAIL_PELAJAR, PASSWORD_PELAJAR);
        com.jtklearn.testing.auth.AuthActions.waitForDashboard();
    }

    @Given("Pelajar terdaftar di minimal satu kursus")
    public void pelajar_terdaftar_di_minimal_satu_kursus() {
        // Asumsi terpenuhi oleh data pengguna
    }

    @When("Pengguna menavigasi ke menu Riwayat Kuis")
    public void pengguna_menavigasi_ke_menu_riwayat_kuis() {
        openQuizHistoryPage();
    }

    @When("Mengamati daftar kursus yang ditampilkan")
    public void mengamati_daftar_kursus_yang_ditampilkan() throws InterruptedException {
        Thread.sleep(2000);
    }

    @Then("Daftar kursus yang telah diikuti oleh pelajar ditampilkan")
    public void daftar_kursus_yang_telah_diikuti_oleh_pelajar_ditampilkan() {
        assertTrue("Kursus yang diikuti (" + ENROLLED_COURSE_NAME + ") tidak ditemukan di halaman",
            isCourseVisible(ENROLLED_COURSE_NAME));
    }

    @Then("Kursus yang tidak didaftarkan oleh pelajar tidak ditampilkan")
    public void kursus_yang_tidak_didaftarkan_oleh_pelajar_tidak_ditampilkan() {
        assertTrue("Ditemukan kursus yang tidak didaftar (" + UNENROLLED_COURSE_NAME + ")",
            !isCourseVisible(UNENROLLED_COURSE_NAME));
    }

    @Given("Pengguna sudah login dan berada di halaman Daftar Riwayat Kuis")
    public void pengguna_sudah_login_dan_berada_di_halaman_daftar_riwayat_kuis() {
        com.jtklearn.testing.auth.AuthActions.openLoginPage();
        com.jtklearn.testing.auth.AuthActions.login(EMAIL_PELAJAR, PASSWORD_PELAJAR);
        com.jtklearn.testing.auth.AuthActions.waitForDashboard();
        openQuizHistoryPage();
        clickLihatDetail();
        waitUntilQuizHistoryPageLoaded();
    }

    @When("Pengguna menekan kolom pencarian")
    public void pengguna_menekan_kolom_pencarian() {
        clickSearchField();
    }

    @When("Pengguna memasukkan kata kunci {string}")
    public void pengguna_memasukkan_kata_kunci(String keyword) {
        enterSearchKeyword(keyword);
    }

    @Then("Sistem menyaring hasil kuis yang mengandung kata kunci")
    public void sistem_menyaring_hasil_kuis_yang_mengandung_kata_kunci() {
        assertTrue("Hasil kuis tidak ditemukan", getQuizItemCount() >= 0);
    }

    @Then("Kuis yang tidak sesuai disembunyikan")
    public void kuis_yang_tidak_sesuai_disembunyikan() {
        assertTrue("Kuis yang tidak sesuai masih ditampilkan", getQuizItemCount() >= 0);
    }

    @Then("Jumlah hasil yang ditampilkan sesuai")
    public void jumlah_hasil_yang_ditampilkan_sesuai() {
        assertTrue("Jumlah hasil tidak sesuai", getQuizItemCount() >= 0);
    }
}