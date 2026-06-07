package com.ppl.a6.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class QuizHistoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By menuRiwayatKuis = By.xpath("//a[contains(text(), 'Riwayat Kuis')]");
    private final By courseCards     = By.xpath("//div[contains(@class, 'course-card')]");

    public QuizHistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuRiwayatKuis));
        menu.click();
    }

    public List<WebElement> getCourseCards() {
        return driver.findElements(courseCards);
    }

    public boolean isCourseVisible(String courseName) {
        return getCourseCards().stream()
            .anyMatch(card -> card.getText().contains(courseName));
    }
}