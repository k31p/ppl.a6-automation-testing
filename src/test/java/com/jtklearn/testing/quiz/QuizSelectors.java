package com.jtklearn.testing.quiz;

import org.openqa.selenium.By;

/**
 * Page Object Model - Selectors untuk halaman Quiz History
 * Berisi semua locator/selector yang digunakan dalam fitur quiz
 */
public class QuizSelectors {

    // Quiz History Page Selectors
    public static final By HISTORY_MENU = By.xpath("//*[contains(text(), 'Riwayat') or contains(text(), 'History')]");
    public static final By TABLE = By.tagName("table");
    public static final By HISTORY_HEADING = By.xpath("//*[contains(text(), 'Riwayat Hasil Kuis') or contains(text(), 'Riwayat Kuis') or contains(text(), 'Quiz History')]");
    public static final By HISTORY_CLASS = By.xpath("//*[contains(@class, 'history')]");
    public static final By RIWAYAT_CLASS = By.xpath("//*[contains(@class, 'riwayat')]");
    public static final By DATE_COLUMN = By.xpath("//*[contains(text(), 'Tanggal') or contains(text(), 'Date') or contains(text(), 'Waktu Mulai') or contains(text(), 'Waktu Selesai')]");
    public static final By DATE_HEADER = By.xpath("//th[contains(text(), 'Tanggal') or contains(text(), 'Date')]");
    public static final By SCORE_COLUMN = By.xpath("//*[contains(text(), 'Skor') or contains(text(), 'Score') or contains(text(), 'Nilai')]");
    public static final By SCORE_HEADER = By.xpath("//th[contains(text(), 'Skor') or contains(text(), 'Score') or contains(text(), 'Nilai')]");
    public static final By LIHAT_DETAIL_BUTTON = By.xpath("//a[contains(text(), 'Lihat Detail') or contains(text(), 'Detail')]");

    private QuizSelectors() {
        // Private constructor to prevent instantiation
    }
}