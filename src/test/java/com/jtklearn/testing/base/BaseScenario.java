package com.jtklearn.testing.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

/**
 * Base class for all test scenarios
 * Provides WebDriver management and common utilities
 */
public class BaseScenario {

    protected static WebDriver driver = null;
    protected static String siteBaseUrl = null;
    protected int secondsToWait = 10;
    private static final String SCREENSHOT_DIR = "target/screenshots";

    public static WebDriver getDriverInstance() {
        if (null != driver) {
            return driver;
        }

        String envName = System.getProperty("envName", "local");
        String browserName = System.getProperty("browserName", "googlechrome");

        if (envName.equalsIgnoreCase("local")) {
            if (browserName.equalsIgnoreCase("googlechrome")) {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--disable-notifications");
                driver = new ChromeDriver(options);
            } else if (browserName.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            }
        } else {
            ChromeOptions options = new ChromeOptions();
            try {
                String remoteWebDriver = System.getProperty("remoteWebDriver");
                driver = new RemoteWebDriver(new URL(remoteWebDriver), options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            } catch (Exception e) {
                // ignore
            }
        }
        return driver;
    }

    /**
     * Get static driver instance (for static contexts)
     */
    public static WebDriver getDriver() {
        return getDriverInstance();
    }

    /**
     * Get site base URL from system property
     */
    public static String getSiteBaseUrl() {
        return getSiteBaseUrlStatic();
    }

    /**
     * Get static site base URL
     */
    public static String getSiteBaseUrlStatic() {
        if (siteBaseUrl == null) {
            siteBaseUrl = System.getProperty("siteUrl");
        }
        return siteBaseUrl;
    }

    /**
     * Clear all cookies
     */
    public static void closeDriver() {
        if (null != driver) {
            driver.manage().deleteAllCookies();
        }
    }

    /**
     * Quit WebDriver
     */
    public static void quitDriver() {
        if (null != driver) {
            driver.quit();
        }
    }

    /**
     * Take screenshot and save to file
     */
    public static String takeScreenshot(String filename) {
        try {
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filepath = SCREENSHOT_DIR + "/" + filename + "_" + System.currentTimeMillis() + ".png";
            Files.copy(screenshot.toPath(), Paths.get(filepath));
            System.out.println("Screenshot saved: " + filepath);
            return filepath;
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return "";
        }
    }
}