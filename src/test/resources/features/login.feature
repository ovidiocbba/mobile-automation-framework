Feature: Login functionality

  @TC-00001 @regression @smoke
  Scenario: Successful login
    Given the user is on the login screen
    When the user logs in with valid credentials
    Then the user should see the products screen

  @TC-00002 @smoke @regression
  Scenario: Add one product to the cart
    Given the user is logged in
    When the user adds a product to the cart
    Then the cart badge should show 1 item
