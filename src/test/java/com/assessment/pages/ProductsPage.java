package com.assessment.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductsPage extends BasePage {

    @AndroidFindBy(accessibility = "test-PRODUCTS")
    @iOSXCUITFindBy(accessibility = "test-PRODUCTS")
    private WebElement title;

    @AndroidFindBy(accessibility = "test-Menu")
    @iOSXCUITFindBy(accessibility = "test-Menu")
    private WebElement menu;

    @AndroidFindBy(accessibility = "test-LOGOUT")
    @iOSXCUITFindBy(accessibility = "test-LOGOUT")
    private WebElement logout;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeOther[`name == 'test-Cart'`]/**/XCUIElementTypeStaticText")
    private WebElement cartBadge;

    public ProductsPage(AppiumDriver driver) { super(driver); }

    public boolean isLoaded() { return isVisible(title); }

    public ProductsPage addFirstProductToCart() {
        List<WebElement> buttons = driver.findElements(AppiumBy.accessibilityId("test-ADD TO CART"));
        if (buttons.isEmpty()) throw new IllegalStateException("No 'ADD TO CART' button found");
        click(buttons.get(0));
        return this;
    }

    public String cartCount() { return text(cartBadge); }

    public void logout() {
        click(menu);
        click(logout);
    }
}
