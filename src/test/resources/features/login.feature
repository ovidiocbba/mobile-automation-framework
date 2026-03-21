Feature: Login functionality

  @TC-00001 @regression @smoke
  Scenario: Successful login
    Given I navigate to the 'Login' screen
    When I log in with valid credentials
    Then the 'Products' screen should be displayed

  @TC-00002 @smoke @regression
  Scenario: Add one product to the cart
    Given I am logged in
    When I add a product to the cart
    Then the 'Cart' badge should show 1 item

  @TC-00003 @regression @negative
  Scenario: Login with empty username
    Given I navigate to the 'Login' screen
    And I leave the 'Username' field empty
    When I tap the 'Login' button
    Then the username required message should be displayed

  @TC-00004 @regression @negative
  Scenario: Login with empty password
    Given I navigate to the 'Login' screen
    And I enter "standard_user" in the Username field
    When I tap the 'Login' button
    Then the password required message should be displayed

  @TC-00005 @regression
  Scenario: Open product detail and validate product information
    Given I am logged in
    When I open the product "Sauce Labs Backpack"
    Then the product name should be "Sauce Labs Backpack"
    And the product price should be "$ 29.99"

  @TC-00006 @regression
  Scenario: Verify correct product is added to the cart
    Given I am logged in
    When I open the product "Sauce Labs Backpack"
    And I tap the 'Add to cart' button
    And I tap the 'Cart' badge
    Then the product "Sauce Labs Backpack" should be displayed in the Cart
