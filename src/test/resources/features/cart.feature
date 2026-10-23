Feature: Shopping cart

  @regression
  Scenario: Add a product to the cart
    Given the app is launched
    And I am logged in
    When I add the first product to the cart
    Then the cart badge should show "1"
