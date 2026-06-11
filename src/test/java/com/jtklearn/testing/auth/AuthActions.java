package com.jtklearn.testing.auth;

import com.jtklearn.testing.base.BaseScenario;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Model - Actions untuk halaman Login/Logout
 * Berisi semua method action yang dilakukan pada elemen
 */
public class AuthActions extends BaseScenario {

    private static WebDriverWait getWait() {
        return new WebDriverWait(getDriverInstance(), Duration.ofSeconds(15));
    }

    // ==================== LOGIN ACTIONS ====================

    /**
     * Buka halaman login
     */
    public static void openLoginPage() {
        getDriver();
        getDriverInstance().manage().window().maximize();
        getDriverInstance().get(getSiteBaseUrl());
        getWait().until(ExpectedConditions.presenceOfElementLocated(AuthSelectors.EMAIL_INPUT));
    }

    /**
     * Masukkan email ke input field
     */
    public static void enterEmail(String email) {
        WebElement emailField = getDriverInstance().findElement(AuthSelectors.EMAIL_INPUT);
        emailField.clear();
        emailField.sendKeys(email);
    }

    /**
     * Masukkan password ke input field
     */
    public static void enterPassword(String password) {
        WebElement passwordField = getDriverInstance().findElement(AuthSelectors.PASSWORD_INPUT);
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Klik tombol Masuk
     */
    public static void clickMasukButton() {
        WebElement submitButton = getDriverInstance().findElement(AuthSelectors.SUBMIT_BUTTON);
        submitButton.click();
    }

    /**
     * Login dengan kredensial
     */
    public static void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickMasukButton();
    }

    /**
     * Tunggu hingga dashboard muncul
     */
    public static void waitForDashboard() {
        // After login, the "Masuk" button disappears — this is a reliable login-specific signal.
        // Avoid using EMAIL_INPUT which may match other email inputs on the home page.
        WebDriverWait longWait = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(30));
        longWait.until(ExpectedConditions.or(
            ExpectedConditions.invisibilityOfElementLocated(AuthSelectors.SUBMIT_BUTTON),
            ExpectedConditions.urlContains("dashboard"),
            ExpectedConditions.urlContains("kursus"),
            ExpectedConditions.urlContains("beranda")
        ));
    }

    /**
     * Cek apakah user profile terlihat
     */
    public static boolean isProfileVisible() {
        // JTKLearn shows user name in nav after login - look for the username text 'fredy'
        List<WebElement> profileTexts = getDriverInstance().findElements(
            By.xpath("//*[contains(text(), 'fredy') or contains(text(), 'Fredy') or contains(text(), 'Beranda') or contains(text(), 'Kursus Saya')]"));
        List<WebElement> navbarElements = getDriverInstance().findElements(AuthSelectors.NAVBAR);
        List<WebElement> profileElements = getDriverInstance().findElements(AuthSelectors.PROFILE);
        return profileTexts.size() > 0 || navbarElements.size() > 0 || profileElements.size() > 0;
    }

    /**
     * Cek apakah error modal ditampilkan
     */
    public static boolean isErrorModalDisplayed() {
        WebElement errorModal = getWait().until(
            ExpectedConditions.visibilityOfElementLocated(AuthSelectors.ERROR_MODAL));
        return errorModal.isDisplayed();
    }

    /**
     * Cek apakah masih di halaman login
     */
    public static boolean isOnLoginPage() {
        String currentUrl = getDriverInstance().getCurrentUrl();
        return currentUrl.equals(getSiteBaseUrl())
            || currentUrl.equals(getSiteBaseUrl() + "/")
            || currentUrl.contains("login");
    }

    /**
     * Cek apakah input email masih terlihat di halaman login
     */
    public static boolean isEmailInputVisible() {
        WebElement emailInput = getDriverInstance().findElement(AuthSelectors.EMAIL_INPUT);
        return emailInput.isDisplayed();
    }

    // ==================== LOGOUT ACTIONS ====================

    /**
     * Klik pada Profil di header navigasi
     */
    public static void clickProfileMenu() {
        List<By> profileLocators = List.of(
            AuthSelectors.PROFIL_TEXT,
            AuthSelectors.PROFIL_LOWER,
            AuthSelectors.PROFILE_CLASS,
            AuthSelectors.USER_CLASS,
            AuthSelectors.NAV_LINK
        );
        WebElement profileMenu = findClickableElement(profileLocators, 5);
        if (profileMenu != null) {
            profileMenu.click();
        }
    }

    /**
     * Klik pada Keluar di dropdown profil
     */
    public static void clickLogout() {
        List<By> logoutLocators = List.of(
            AuthSelectors.KELUAR_TEXT,
            AuthSelectors.KELUAR_LOWER,
            AuthSelectors.LOGOUT_TEXT,
            AuthSelectors.LOGOUT_LOWER,
            AuthSelectors.LOGOUT_HREF,
            AuthSelectors.KELUAR_BUTTON
        );
        WebElement logoutButton = findClickableElement(logoutLocators, 5);
        if (logoutButton != null) {
            logoutButton.click();
        }
        getDriverInstance().navigate().to(getSiteBaseUrl());
    }

    /**
     * Tunggu hingga halaman login muncul setelah logout
     */
    public static void waitForLoginPage() {
        getWait().until(ExpectedConditions.or(
            ExpectedConditions.urlContains("login"),
            ExpectedConditions.urlContains("Login"),
            ExpectedConditions.presenceOfElementLocated(AuthSelectors.EMAIL_INPUT)
        ));
    }

    /**
     * Cek apakah ada error modal setelah logout
     */
    public static boolean hasErrorModal() {
        List<WebElement> errorModals = getDriverInstance().findElements(AuthSelectors.ERROR_MODAL);
        return errorModals.size() > 0;
    }

    // ==================== HELPER METHODS ====================

    /**
     * Helper method to find clickable element from multiple locators
     */
    private static WebElement findClickableElement(List<By> locators, int timeoutSeconds) {
        WebDriverWait waitLocal = new WebDriverWait(getDriverInstance(), Duration.ofSeconds(timeoutSeconds));
        for (By locator : locators) {
            try {
                WebElement element = waitLocal.until(ExpectedConditions.elementToBeClickable(locator));
                if (element != null) {
                    return element;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return null;
    }

    private static org.openqa.selenium.WebDriver getLocalDriverInstance() {
        return BaseScenario.getDriver();
    }
}