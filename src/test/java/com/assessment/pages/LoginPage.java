package com.assessment.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {

    // accessibility id is shared by Android (content-desc) and iOS (name) in the Sauce Labs app
    @AndroidFindBy(accessibility = "test-Username")
    @iOSXCUITFindBy(accessibility = "test-Username")
    private WebElement username;

    @AndroidFindBy(accessibility = "test-Password")
    @iOSXCUITFindBy(accessibility = "test-Password")
    private WebElement password;

    @AndroidFindBy(accessibility = "test-LOGIN")
    @iOSXCUITFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name == 'test-Error message'`]/**/XCUIElementTypeStaticText")
    private WebElement errorMessage;

    public LoginPage(AppiumDriver driver) { super(driver); }

    public boolean isLoaded() { return isVisible(loginButton); }

    public LoginPage login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
        return this;
    }

    public String errorText() { return text(errorMessage); }
}
