package com.assessment.pages;

import com.assessment.utils.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final AppiumDriver driver;
    private final WebDriverWait wait;

    protected BasePage(AppiumDriver driver) {
        this.driver = driver;
        Duration timeout = Duration.ofSeconds(Long.parseLong(ConfigReader.get("implicit.wait")));
        this.wait = new WebDriverWait(driver, timeout);
        PageFactory.initElements(new AppiumFieldDecorator(driver, timeout), this);
    }

    protected void click(WebElement el) {
        wait.until(ExpectedConditions.elementToBeClickable(el)).click();
    }

    protected void type(WebElement el, String text) {
        WebElement e = wait.until(ExpectedConditions.visibilityOf(el));
        e.clear();
        e.sendKeys(text);
    }

    protected String text(WebElement el) {
        return wait.until(ExpectedConditions.visibilityOf(el)).getText();
    }

    protected boolean isVisible(WebElement el) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(el)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
