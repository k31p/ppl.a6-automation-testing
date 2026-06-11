package com.jtklearn.testing.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

/**
 * Base class for all test scenarios
 * Provides WebDriver management and common utilities
 */
public class BaseScenario {

    protected static WebDriver driver = null;
    protected static String siteBaseUrl = null;
    protected static Properties webDriverProps = null;
    protected int secondsToWait = 10;
    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final String WEB_DRIVER_PROPS = "web-driver.properties";

    /**
     * Load web-driver.properties dari classpath
     */
    private static Properties getWebDriverProps() {
        if (webDriverProps == null) {
            webDriverProps = new Properties();
            try (InputStream is = BaseScenario.class.getClassLoader().getResourceAsStream(WEB_DRIVER_PROPS)) {
                if (is != null) {
                    webDriverProps.load(is);
                }
            } catch (Exception e) {
                System.err.println("Cannot load " + WEB_DRIVER_PROPS + ": " + e.getMessage());
            }
        }
        return webDriverProps;
    }

    public static WebDriver getDriverInstance() {
        if (null != driver) {
            return driver;
        }

        Properties props = getWebDriverProps();
        String envName = System.getProperty("envName", props.getProperty("env.name", "local"));
        String browserName = System.getProperty("browserName", props.getProperty("browser.name", "googlechrome"));

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
     * Get static site base URL — baca dari web-driver.properties, fallback ke system property
     */
    public static String getSiteBaseUrlStatic() {
        if (siteBaseUrl == null) {
            siteBaseUrl = System.getProperty("siteUrl");
            if (siteBaseUrl == null) {
                siteBaseUrl = getWebDriverProps().getProperty("site.url");
            }
        }
        return siteBaseUrl;
    }

    /**
     * Clear all cookies dan storage (untuk reset state antar skenario)
     * JTKLearn React SPA menyimpan auth state di localStorage, harus dibersihkan juga
     */
    public static void closeDriver() {
        if (null != driver) {
            driver.manage().deleteAllCookies();
            try {
                // Clear React app state stored in localStorage/sessionStorage
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
            } catch (Exception e) {
                // ignore if JS execution fails
            }
        }
    }

    /**
     * Quit WebDriver
     */
    public static void quitDriver() {
        if (null != driver) {
            driver.quit();
            driver = null;
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