package com.ovidiomiranda.framework.steps;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PASSWORD;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.USERNAME;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.ui.pages.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions related to actions performed on the 'Login' page.
 *
 * @author Ovidio Miranda
 */
public class LoginSteps {

  private final LoginPage loginPage;
  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param loginPage login page object
   */
  public LoginSteps(final ConfigValidator config, final LoginPage loginPage) {
    this.config = config;
    this.loginPage = loginPage;
  }

  /** Leaves the 'Username' field empty. */
  @And("I leave the 'Username' field empty")
  public void leaveUsernameFieldEmpty() {
    loginPage.enterUsername("");
  }

  /**
   * Enters a username.
   *
   * @param username value to enter
   */
  @When("I enter {string} in the Username field")
  public void enterUsername(final String username) {
    loginPage.enterUsername(username);
  }

  /**
   * Enters a password.
   *
   * @param password value to enter
   */
  @And("I enter {string} in the Password field")
  public void enterPassword(final String password) {
    loginPage.enterPassword(password);
  }

  /** Enters valid username and password without tapping the login button. */
  @When("I enter valid credentials")
  public void enterValidCredentials() {
    final String username = config.require(USERNAME);
    final String password = config.require(PASSWORD);
    loginPage.enterCredentials(username, password);
  }

  /** Taps the 'Login' button. */
  @And("I tap the 'Login' button")
  public void tapLoginButton() {
    loginPage.tapLoginButton();
  }

  /** Logs in using valid credentials. */
  @When("I log in with valid credentials")
  public void loginWithValidCredentials() {
    final String username = config.require(USERNAME);
    final String password = config.require(PASSWORD);
    loginPage.login(username, password);
  }

  /** Ensures the user is logged in. */
  @Given("I am logged in")
  public void ensureUserIsLoggedIn() {
    loginWithValidCredentials();
  }

  /** Verifies the Error message is displayed. */
  @Then("the Error message should be displayed")
  public void verifyUsernameRequiredMessage() {
    Assert.assertTrue(
        loginPage.isErrorMessageDisplayed(), "Username required message was not displayed");
  }

  /**
   * Verifies that the expected error message is displayed..
   *
   * @param expectedMessage the expected error message text
   */
  @Then("the {string} error message should be displayed")
  public void verifyErrorMessage(final String expectedMessage) {
    final String actualMessage = loginPage.getErrorMessageText();

    Assert.assertTrue(
        actualMessage.contains(expectedMessage),
        "Expected '" + expectedMessage + "' but got: " + actualMessage);
  }

  /** Verifies that the 'Login' screen is displayed. */
  @Then("the 'Login' screen should be displayed")
  public void verifyLoginScreenIsDisplayed() {
    Assert.assertTrue(loginPage.isDisplayed(), "Login screen was not displayed");
  }
}
