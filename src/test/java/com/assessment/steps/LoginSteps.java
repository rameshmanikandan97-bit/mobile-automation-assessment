package com.assessment.steps;

import com.assessment.drivers.DriverManager;
import com.assessment.pages.LoginPage;
import com.assessment.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private LoginPage login() { return new LoginPage(DriverManager.get()); }
    private ProductsPage products() { return new ProductsPage(DriverManager.get()); }

    @Given("the app is launched")
    public void appLaunched() {
        assertTrue(login().isLoaded(), "Login screen not displayed");
    }

    @Given("I am logged in")
    public void iAmLoggedIn() {
        login().login("standard_user", "secret_sauce");
        assertTrue(products().isLoaded(), "Products page not displayed after login");
    }

    @When("I login with valid credentials")
    public void loginValid() {
        login().login("standard_user", "secret_sauce");
    }

    @When("I login with username {string} and password {string}")
    public void loginWith(String user, String pass) {
        login().login(user, pass);
    }

    @When("I logout")
    public void logout() {
        products().logout();
    }

    @Then("I should see the products page")
    public void seeProducts() {
        assertTrue(products().isLoaded(), "Products page not displayed");
    }

    @Then("I should see an error message containing {string}")
    public void seeError(String expected) {
        String actual = login().errorText();
        assertTrue(actual.contains(expected), "Expected error to contain '" + expected + "' but was: " + actual);
    }

    @Then("I should see the login page")
    public void seeLogin() {
        assertTrue(login().isLoaded(), "Login page not displayed");
    }
}
