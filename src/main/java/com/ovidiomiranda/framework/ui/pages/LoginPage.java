package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Login' page and its components.
 *
 * @author Ovidio Miranda
 */
public class LoginPage extends BasePage {

  private final By usernameInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/nameET");
  private final By passwordInput = AppiumBy.id("com.saucelabs.mydemoapp.android:id/passwordET");
  private final By loginButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/loginBtn");

  /**
   * Enters username in the input field.
   *
   * @param username user name
   */
  public void enterUsername(String username) {
    MobileElementActions.type(usernameInput, username);
  }

  /**
   * Enters password in the input field.
   *
   * @param password user password
   */
  public void enterPassword(String password) {
    MobileElementActions.type(passwordInput, password);
  }

  /**
   * Taps login button.
   */
  public void tapLoginButton() {
    MobileElementActions.tap(loginButton);
  }

  /**
   * Performs full login flow.
   *
   * @param username user name
   * @param password user password
   */
  public void login(String username, String password) {
    enterUsername(username);
    enterPassword(password);
    tapLoginButton();
  }
}
