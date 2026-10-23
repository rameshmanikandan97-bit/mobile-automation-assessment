package com.assessment.steps;

import com.assessment.drivers.DriverManager;
import com.assessment.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartSteps {

    private ProductsPage products() { return new ProductsPage(DriverManager.get()); }

    @When("I add the first product to the cart")
    public void addFirst() {
        products().addFirstProductToCart();
    }

    @Then("the cart badge should show {string}")
    public void badge(String expected) {
        assertEquals(expected, products().cartCount());
    }
}
