package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;

/**
 * Represents the 'Login' page and its components.
 *
 * @author Ovidio Miranda
 */
public class LoginPage extends BasePage {

  private final MobileLocator usernameInput = new MobileLocator(AppiumBy.id("nameET"),
      AppiumBy.accessibilityId("username"));

  private final MobileLocator passwordInput = new MobileLocator(AppiumBy.id("passwordET"),
      AppiumBy.accessibilityId("password"));

  private final MobileLocator loginButton = new MobileLocator(AppiumBy.id("loginBtn"),
      AppiumBy.accessibilityId("login_button"));

  /**
   * Constructor.
   *
   * @param config        config validator
   * @param driverContext driver context
   * @param actions       MobileElementActions utility
   */
  public LoginPage(ConfigValidator config, DriverContext driverContext,
      MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Enters username in the input field.
   *
   * @param username user name
   */
  public void enterUsername(String username) {
    actions.type(resolve(usernameInput), username);
  }

  /**
   * Enters password in the input field.
   *
   * @param password user password
   */
  public void enterPassword(String password) {
    actions.type(resolve(passwordInput), password);
  }

  /**
   * Taps login button.
   */
  public void tapLoginButton() {
    actions.tap(resolve(loginButton));
  }

  /**
   * Performs the full login flow: enter username, password, and tap login.
   *
   * @param username user name string
   * @param password user password string
   */
  public void login(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    tapLoginButton();
  }
}
