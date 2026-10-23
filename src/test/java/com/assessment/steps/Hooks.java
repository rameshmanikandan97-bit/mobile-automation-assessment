package com.assessment.steps;

import com.assessment.drivers.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before(order = 0)
    public void setUp(Scenario scenario) {
        System.out.println("[START] " + scenario.getName());
        DriverManager.start();   // new session per scenario => clean app state
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && DriverManager.isStarted()) {
                byte[] png = ((TakesScreenshot) DriverManager.get()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(png, "image/png", "failure-screenshot");
                System.out.println("[FAILED] " + scenario.getName());
            }
        } catch (Exception e) {
            System.out.println("[WARN] could not capture screenshot: " + e.getMessage());
        } finally {
            DriverManager.quit();
        }
    }
}
