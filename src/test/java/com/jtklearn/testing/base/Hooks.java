package com.jtklearn.testing.base;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Hooks extends BaseScenario {

    @After(order = 10)
    public void takeScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                String filename = scenario.getName().replaceAll("[^a-zA-Z0-9_-]", "_");
                for (String tag : scenario.getSourceTagNames()) {
                    if (tag.startsWith("@TC-")) {
                        filename = tag.replace("@", "");
                        break;
                    }
                }
                
                if (getDriverInstance() != null) {
                    File screenshotDir = new File("target/screenshots");
                    if (!screenshotDir.exists()) {
                        screenshotDir.mkdirs();
                    }
                    File screenshot = ((TakesScreenshot) getDriverInstance()).getScreenshotAs(OutputType.FILE);
                    String filepath = "target/screenshots/" + filename + ".png";
                    Files.copy(screenshot.toPath(), Paths.get(filepath));
                    System.out.println("Scenario Failed. Screenshot saved: " + filepath);
                }
            } catch (Exception e) {
                System.err.println("Failed to take failure screenshot: " + e.getMessage());
            }
        }
    }

    @After(order = 1)
    public void tearDown() {
        BaseScenario.closeDriver();
        try {
            if (BaseScenario.getDriver() != null) {
                BaseScenario.getDriver().get(getSiteBaseUrlStatic());
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
