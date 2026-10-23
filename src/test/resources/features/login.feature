Feature: Login and logout

  @smoke @regression
  Scenario: Successful login
    Given the app is launched
    When I login with valid credentials
    Then I should see the products page

  @regression
  Scenario: Unsuccessful login with invalid credentials
    Given the app is launched
    When I login with username "invalid_user" and password "wrong_pass"
    Then I should see an error message containing "do not match"

  @smoke @regression
  Scenario: Logout
    Given the app is launched
    And I am logged in
    When I logout
    Then I should see the login page
