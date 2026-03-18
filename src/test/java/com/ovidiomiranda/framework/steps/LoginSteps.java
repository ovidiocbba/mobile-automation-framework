package com.ovidiomiranda.framework.steps;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PASSWORD;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.USERNAME;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.ui.pages.LoginPage;
import com.ovidiomiranda.framework.ui.navigation.MenuNavigation;
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

  private final MenuNavigation menuNavigation = new MenuNavigation();
  private final LoginPage loginPage = new LoginPage();
  private final ProductsPage productsPage = new ProductsPage();

  /**
   * Navigates to 'Login' screen.
   */
  @Given("the user is on the login screen")
  public void userIsOnLoginScreen() {
    menuNavigation.goToLogin();
  }

  /**
   * Performs login with valid credentials.
   */
  @When("the user logs in with valid credentials")
  public void login() {
    String username = ConfigValidator.require(USERNAME);
    String password = ConfigValidator.require(PASSWORD);
    loginPage.login(username, password);
  }

  /**
   * Verifies products screen is displayed.
   */
  @Then("the user should see the products screen")
  public void validateLogin() {
    Assert.assertTrue(productsPage.isDisplayed(), "Products screen not displayed");
  }
}
