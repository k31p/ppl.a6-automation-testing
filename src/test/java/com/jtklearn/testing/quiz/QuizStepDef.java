package com.jtklearn.testing.quiz;

import com.jtklearn.testing.base.BaseScenario;

import io.cucumber.java.After;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.jtklearn.testing.quiz.QuizActions.*;
import static org.junit.Assert.assertTrue;

/**
 * Step Definitions untuk fitur Quiz History
 * TC-FR11-05
 * Menggunakan POM pattern dengan separate Selector dan Action classes
 */
public class QuizStepDef extends BaseScenario {

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
}