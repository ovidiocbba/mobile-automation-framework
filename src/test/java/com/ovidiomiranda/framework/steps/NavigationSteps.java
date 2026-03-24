package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.navigation.MenuNavigation;
import io.cucumber.java.en.When;

/**
 * Steps for navigation actions.
 *
 * @author Ovidio Miranda
 */
public class NavigationSteps {

  private final MenuNavigation menuNavigation;

  /**
   * Constructor.
   *
   * @param menuNavigation menu Navigation
   */
  public NavigationSteps(MenuNavigation menuNavigation) {
    this.menuNavigation = menuNavigation;
  }

  /** Navigates to 'Login' screen. */
  @When("I go to the login screen")
  public void goToLogin() {
    menuNavigation.goToLogin();
  }

  /** Logs out the user. */
  @When("I logout from the app")
  public void logout() {
    menuNavigation.logout();
  }
}
