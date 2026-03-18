Feature: Login functionality

  @TC-00001 @regression @smoke
  Scenario: Successful login
    Given the user is on the login screen
    When the user logs in with valid credentials
    Then the user should see the products screen
