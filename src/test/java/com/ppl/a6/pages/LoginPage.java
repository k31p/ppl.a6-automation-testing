package com.ppl.a6.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By emailInput    = By.xpath("//input[@type='email']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By submitButton  = By.xpath("//button[contains(text(), 'Masuk')]");
    private final By errorModal    = By.xpath("//h2[contains(text(), 'Kesalahan!')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Actions
    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailInput));
    }

    public void enterEmail(String email) {
        WebElement field = driver.findElement(emailInput);
        field.clear();
        field.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement field = driver.findElement(passwordInput);
        field.clear();
        field.sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(submitButton).click();
    }

    public void loginAs(String email, String password) {
        waitUntilLoaded();
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }

    // Assertions helpers
    public boolean isErrorModalDisplayed() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(errorModal)).isDisplayed();
    }

    public boolean isOnLoginPage(String baseUrl) {
        String url = driver.getCurrentUrl();
        return url.equals(baseUrl) || url.equals(baseUrl + "/") || url.contains("login");
    }

    public boolean isEmailInputVisible() {
        return driver.findElement(emailInput).isDisplayed();
    }

    public void waitForRedirectToLogin() {
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("login"),
            ExpectedConditions.urlContains("Login"),
            ExpectedConditions.presenceOfElementLocated(emailInput)
        ));
    }

    public boolean isErrorModalNotDisplayed() {
        return driver.findElements(errorModal).isEmpty();
    }
}