/**
 * @preserve Copyright 2014 Zeno Yu <zeno.yu@gmail.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     * Redistributions of source code must retain the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDER "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package com.ppl.a6.utils;

import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BaseScenario {

    protected static WebDriver driver = null;

    protected int secondsToWait = 10;

    public static WebDriver getDriver() {
        if (null != driver) {
            return driver;
        }

        String envName = System.getProperty("envName", "local");
        String browserName = System.getProperty("browserName", "safari");

        if (envName.equalsIgnoreCase("local")) {
            if (browserName.equalsIgnoreCase("googlechrome")) {
                driver = new ChromeDriver();
            } else if (browserName.equalsIgnoreCase("firefox")) {
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("safari")) {
                driver = new SafariDriver();
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

    public static String getSiteBaseUrl() {
        return System.getProperty("siteUrl");
    }

    public static void closeDriver() {
        if (null != driver) {
            driver.manage().deleteAllCookies();
        }
    }

    public static void quitDriver() {
        if (null != driver) {
            driver.quit();
        }
    }
}
