package com.ppl.a6;

import com.ppl.a6.base.BaseScenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.ppl.a6"
)
public class PplA6AutomationTest {

  @AfterClass
  public static void tearDownClass() {
    BaseScenario.quitDriver();
  }
}
