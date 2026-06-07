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

    private final By menuRiwayatKuis  = By.xpath("//a[contains(text(), 'Riwayat Kuis')]");
    private final By lihatDetailBtn   = By.xpath("//button[contains(text(), 'Lihat Detail')]");
    private final By searchInput      = By.cssSelector("input[placeholder='Search']");
    private final By historyCard      = By.cssSelector("div.history-card");
    private final By courseCards      = By.xpath("//div[contains(@class, 'course-card')]");

    public QuizHistoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void navigateToMenu() {
        WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(menuRiwayatKuis));
        menu.click();
    }

    public void clickLihatDetail() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(lihatDetailBtn));
            btn.click();
        } catch (Exception e) {
            // mungkin sudah di halaman detail
        }
    }

    public void waitUntilQuizHistoryPageLoaded() {
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("history-quiz"),
            ExpectedConditions.presenceOfElementLocated(searchInput),
            ExpectedConditions.presenceOfElementLocated(historyCard)
        ));
    }

    public void clickSearchField() {
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        search.click();
    }

    public void enterSearchKeyword(String keyword) {
        WebElement search = driver.findElement(searchInput);
        search.clear();
        search.sendKeys(keyword);
    }

    public List<WebElement> getQuizItems() {
        List<WebElement> items = driver.findElements(historyCard);
        return items;
    }

    public boolean isQuizVisible(String keyword) {
        return getQuizItems().stream()
            .anyMatch(item -> item.getText().toLowerCase().contains(keyword.toLowerCase()));
    }

    public boolean isQuizNotVisible(String keyword) {
        return getQuizItems().stream()
            .noneMatch(item -> item.getText().toLowerCase().contains(keyword.toLowerCase()));
    }

    public int getQuizItemCount() {
        return getQuizItems().size();
    }

    // TC-FR11-03 methods

    public List<WebElement> getCourseCards() {
        List<WebElement> cards = driver.findElements(courseCards);
        if (cards.isEmpty()) {
            cards = driver.findElements(By.cssSelector(".card"));
        }
        return cards;
    }

    public boolean isCourseVisible(String courseName) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), courseName));
        } catch (Exception e) {
            return false;
        }
    }
}
