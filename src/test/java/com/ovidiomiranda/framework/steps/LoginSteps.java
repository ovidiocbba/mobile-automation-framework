package com.ovidiomiranda.framework.steps;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PASSWORD;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.USERNAME;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.ui.navigation.MenuNavigation;
import com.ovidiomiranda.framework.ui.pages.LoginPage;
import com.ovidiomiranda.framework.ui.pages.ProductsPage;
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
  private final ProductsPage productsPage;
  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param config         configuration validator
   * @param menuNavigation menu navigation utility
   * @param loginPage      login page object
   * @param productsPage   products page object
   */
  public LoginSteps(ConfigValidator config, MenuNavigation menuNavigation, LoginPage loginPage,
      ProductsPage productsPage) {
    this.config = config;
    this.menuNavigation = menuNavigation;
    this.loginPage = loginPage;
    this.productsPage = productsPage;
  }

  /**
   * Navigates to 'Login' screen.
   */
  @Given("the user is on the login screen")
  public void userIsOnLoginScreen() {
    menuNavigation.goToLogin();
  }

  /**
   * Performs login using valid credentials from configuration.
   */
  @When("the user logs in with valid credentials")
  public void login() {
    String username = config.require(USERNAME);
    String password = config.require(PASSWORD);
    loginPage.login(username, password);
  }

  /**
   * Verifies that the products screen is displayed.
   */
  @Then("the user should see the products screen")
  public void validateLogin() {
    Assert.assertTrue(productsPage.isDisplayed(), "Products screen not displayed");
  }
}
