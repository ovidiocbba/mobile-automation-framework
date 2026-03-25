package com.ovidiomiranda.framework.steps;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PASSWORD;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.USERNAME;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.ui.navigation.MenuNavigation;
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

  private final MenuNavigation menuNavigation;
  private final LoginPage loginPage;
  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param menuNavigation menu navigation utility
   * @param loginPage login page object
   */
  public LoginSteps(ConfigValidator config, MenuNavigation menuNavigation, LoginPage loginPage) {
    this.config = config;
    this.menuNavigation = menuNavigation;
    this.loginPage = loginPage;
  }

  /** Navigates to 'Login' screen. */
  @Given("I navigate to the 'Login' screen")
  public void navigateToLoginScreen() {
    menuNavigation.goToLogin();
  }

  /** Logs in using valid credentials. */
  @When("I log in with valid credentials")
  public void loginWithValidCredentials() {
    String username = config.require(USERNAME);
    String password = config.require(PASSWORD);
    loginPage.login(username, password);
  }

  /** Ensures the user is logged in. */
  @Given("I am logged in")
  public void ensureUserIsLoggedIn() {
    navigateToLoginScreen();
    loginWithValidCredentials();
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

  /** Taps the 'Login' button. */
  @And("I tap the 'Login' button")
  public void tapLoginButton() {
    loginPage.tapLoginButton();
  }

  /** Verifies the username required message is displayed. */
  @Then("the username required message should be displayed")
  public void verifyUsernameRequiredMessage() {
    Assert.assertTrue(
        loginPage.isUsernameRequiredMessageDisplayed(),
        "Username required message was not displayed");
  }

  /** Verifies the password required message is displayed. */
  @Then("the password required message should be displayed")
  public void verifyPasswordRequiredMessage() {
    Assert.assertTrue(
        loginPage.isPasswordRequiredMessageDisplayed(),
        "Password required message was not displayed");
  }

  /** Verifies that the 'Login' screen is displayed. */
  @Then("the 'Login' screen should be displayed")
  public void verifyLoginScreenIsDisplayed() {
    Assert.assertTrue(loginPage.isDisplayed(), "Login screen was not displayed");
  }
}
