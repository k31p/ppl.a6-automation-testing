package com.jtklearn.testing.auth;

import org.openqa.selenium.By;

/**
 * Page Object Model - Selectors untuk halaman Login/Logout
 * Berisi semua locator/selector yang digunakan dalam fitur auth
 */
public class AuthSelectors {

    // Login Page Selectors
    public static final By EMAIL_INPUT = By.xpath("//input[@type='email']");
    public static final By PASSWORD_INPUT = By.xpath("//input[@type='password']");
    public static final By SUBMIT_BUTTON = By.xpath("//button[contains(text(), 'Masuk')]");
    public static final By ERROR_MODAL = By.xpath("//h2[contains(text(), 'Kesalahan!')]");
    public static final By DASHBOARD = By.className("dashboard");
    public static final By NAVBAR = By.className("navbar");
    public static final By PROFILE = By.className("profile");

    // Profile & Logout Selectors
    public static final By PROFIL_TEXT = By.xpath("//*[contains(text(), 'Profil')]");
    public static final By PROFIL_LOWER = By.xpath("//*[contains(text(), 'profil')]");
    public static final By PROFILE_CLASS = By.xpath("//*[contains(@class, 'profile')]");
    public static final By USER_CLASS = By.xpath("//*[contains(@class, 'user')]");
    public static final By NAV_LINK = By.xpath("//nav//a");

    public static final By KELUAR_TEXT = By.xpath("//*[contains(text(), 'Keluar')]");
    public static final By KELUAR_LOWER = By.xpath("//*[contains(text(), 'keluar')]");
    public static final By LOGOUT_TEXT = By.xpath("//*[contains(text(), 'Logout')]");
    public static final By LOGOUT_LOWER = By.xpath("//*[contains(text(), 'logout')]");
    public static final By LOGOUT_HREF = By.xpath("//a[contains(@href, 'logout')]");
    public static final By KELUAR_BUTTON = By.xpath("//button[contains(text(), 'Keluar')]");

    private AuthSelectors() {
        // Private constructor to prevent instantiation
    }
}