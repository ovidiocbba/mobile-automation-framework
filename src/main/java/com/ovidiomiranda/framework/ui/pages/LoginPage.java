package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Login' page and its components.
 *
 * @author Ovidio Miranda
 */
public class LoginPage extends BasePage {

  private final MobileLocator usernameInput =
      new MobileLocator(
          AppiumBy.accessibilityId("test-Username"), AppiumBy.accessibilityId("test-Username"));

  private final MobileLocator passwordInput =
      new MobileLocator(
          AppiumBy.accessibilityId("test-Password"), AppiumBy.accessibilityId("test-Password"));

  private final MobileLocator loginButton =
      new MobileLocator(
          AppiumBy.accessibilityId("test-LOGIN"), AppiumBy.accessibilityId("test-LOGIN"));

  private final By errorMessage = AppiumBy.accessibilityId("test-Error message");

  private final MobileLocator errorTextMessage =
      new MobileLocator(
          AppiumBy.xpath("//*[@content-desc='test-Error message']//android.widget.TextView"),
          AppiumBy.xpath(
              "//XCUIElementTypeOther[@name='test-Error message']/XCUIElementTypeStaticText"));

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public LoginPage(
      final ConfigValidator config,
      final DriverContext driverContext,
      final MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Enters username in the input field.
   *
   * @param username user name
   */
  public void enterUsername(final String username) {
    actions.type(resolve(usernameInput), username);
  }

  /**
   * Enters password in the input field.
   *
   * @param password user password
   */
  public void enterPassword(final String password) {
    actions.type(resolve(passwordInput), password);
  }

  /** Taps login button. */
  public void tapLoginButton() {
    actions.tap(resolve(loginButton));
  }

  /**
   * Enters username and password without tapping the login button.
   *
   * @param username user name string
   * @param password user password string
   */
  public void enterCredentials(final String username, final String password) {
    enterUsername(username);
    enterPassword(password);
  }

  /**
   * Performs the full login flow: enter username, password, and tap login.
   *
   * @param username user name string
   * @param password user password string
   */
  public void login(final String username, final String password) {
    enterCredentials(username, password);
    tapLoginButton();
  }

  /**
   * Verifies if an error message is displayed.
   *
   * @return true if visible
   */
  public boolean isErrorMessageDisplayed() {
    return actions.isDisplayed(errorMessage);
  }

  /**
   * Gets the error message text displayed in the login screen.
   *
   * @return error message text
   */
  public String getErrorMessageText() {
    return actions.getText(resolve(errorTextMessage));
  }

  /**
   * Verifies if the 'Login' screen is displayed.
   *
   * @return true if the Login screen is visible, false otherwise
   */
  public boolean isDisplayed() {
    return actions.isDisplayed(resolve(loginButton));
  }
}
