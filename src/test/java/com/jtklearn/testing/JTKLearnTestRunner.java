package com.jtklearn.testing;

import com.jtklearn.testing.base.BaseScenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.jtklearn.testing",
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-pretty.html",
        "json:target/cucumber-reports/cucumber.json",
        "junit:target/cucumber-reports/cucumber.xml"
    },
    monochrome = true
)
public class JTKLearnTestRunner {

    @AfterClass
    public static void tearDownClass() {
        BaseScenario.quitDriver();
    }
}
