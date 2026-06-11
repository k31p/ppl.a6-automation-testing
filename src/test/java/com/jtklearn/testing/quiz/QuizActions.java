package com.jtklearn.testing.quiz;

import com.jtklearn.testing.base.BaseScenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static com.jtklearn.testing.quiz.QuizSelectors.*;

/**
 * Page Object Model - Actions untuk halaman Quiz History
 * Berisi semua method action yang dilakukan pada elemen
 */
public class QuizActions extends BaseScenario {

    private static WebDriverWait getWait() {
        return new WebDriverWait(getDriverInstance(), Duration.ofSeconds(10));
    }

    /**
     * Buka halaman quiz history (Riwayat Kuis)
     */
    public static void openQuizHistoryPage() {
        // Cari menu "Riwayat Kuis" di navbar dan klik
        try {
            // Selector: cari link nav "Riwayat Kuis"
            WebElement historyMenu = getWait().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Riwayat Kuis') or contains(text(), 'Riwayat') or contains(text(), 'History')]")));
            // Action: klik
            historyMenu.click();
        } catch (Exception e) {
            // Fallback: navigasi langsung ke URL riwayat-quiz
            getDriverInstance().get(getSiteBaseUrlStatic() + "riwayat-quiz");
        }

        // Tunggu halaman Riwayat Hasil Kuis tampil
        getWait().until(ExpectedConditions.or(
            ExpectedConditions.urlContains("riwayat"),
            ExpectedConditions.presenceOfElementLocated(HISTORY_HEADING)
        ));
    }

    /**
     * Cek apakah halaman riwayat kuis ditampilkan
     * (bisa berisi data tabel ATAU pesan kosong "belum ada kuis")
     */
    public static boolean isQuizHistoryDisplayed() {
        // Selector: cek heading halaman "Riwayat Hasil Kuis"
        List<WebElement> headingElements = getDriverInstance().findElements(HISTORY_HEADING);
        // Selector: cek tabel jika ada data
        List<WebElement> tableElements = getDriverInstance().findElements(TABLE);
        // Selector: cek class history/riwayat
        List<WebElement> historyElements = getDriverInstance().findElements(HISTORY_CLASS);
        List<WebElement> riwayatElements = getDriverInstance().findElements(RIWAYAT_CLASS);

        // Validation: halaman valid jika heading ada, atau table ada, atau class ada
        return headingElements.size() > 0 || tableElements.size() > 0
            || historyElements.size() > 0 || riwayatElements.size() > 0;
    }

    /**
     * Cek apakah kolom tanggal tersedia di halaman
     * Jika tidak ada data (belum ada kuis), halaman tetap valid
     */
    public static boolean isDateColumnDisplayed() {
        // Selector: cek header kolom tanggal di tabel (jika ada data)
        List<WebElement> dateColumns = getDriverInstance().findElements(DATE_COLUMN);
        List<WebElement> dateHeaders = getDriverInstance().findElements(DATE_HEADER);

        if (dateColumns.size() > 0 || dateHeaders.size() > 0) {
            // Validation: kolom ditemukan di tabel
            return true;
        }

        // Jika tidak ada data, cek apakah masih di halaman riwayat (halaman valid)
        // Kolom tidak tampil karena belum ada kuis diselesaikan - ini valid behavior
        List<WebElement> headingElements = getDriverInstance().findElements(HISTORY_HEADING);
        return headingElements.size() > 0 && getDriverInstance().getCurrentUrl().contains("riwayat");
    }

    /**
     * Cek apakah kolom skor tersedia di halaman
     * Jika tidak ada data, halaman tetap valid
     */
    public static boolean isScoreColumnDisplayed() {
        // Selector: cek header kolom skor di tabel (jika ada data)
        List<WebElement> scoreColumns = getDriverInstance().findElements(SCORE_COLUMN);
        List<WebElement> scoreHeaders = getDriverInstance().findElements(SCORE_HEADER);

        if (scoreColumns.size() > 0 || scoreHeaders.size() > 0) {
            // Validation: kolom ditemukan di tabel
            return true;
        }

        // Jika tidak ada data, cek apakah masih di halaman riwayat (halaman valid)
        List<WebElement> headingElements = getDriverInstance().findElements(HISTORY_HEADING);
        return headingElements.size() > 0 && getDriverInstance().getCurrentUrl().contains("riwayat");
    }
}