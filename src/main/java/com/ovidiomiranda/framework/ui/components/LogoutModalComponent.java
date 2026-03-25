package com.ovidiomiranda.framework.ui.components;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Log Out' modal.
 *
 * @author Ovidio Miranda
 */
public class LogoutModalComponent extends BaseComponent {

  private final By modalTitle = AppiumBy.id("alertTitle");
  private final By logoutButton = AppiumBy.xpath("//*[contains(@text,'LOGOUT')]");
  private final By cancelButton = AppiumBy.xpath("//*[contains(@text,'CANCEL')]");

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param driverContext driver context
   * @param actions mobile actions utility
   */
  public LogoutModalComponent(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /** Taps the 'LOGOUT' button. */
  public void tapLogoutButton() {
    actions.tap(logoutButton);
  }

  /** Taps the 'CANCEL' button. */
  public void tapCancelButton() {
    actions.tap(cancelButton);
  }

  /**
   * Gets the modal title text.
   *
   * @return modal title text
   */
  public String getModalTitle() {
    return actions.getText(modalTitle);
  }

  /**
   * Verifies if the 'Log Out' modal is displayed.
   *
   * @return true if the modal is visible, false otherwise
   */
  public boolean isDisplayed() {
    return actions.isDisplayed(modalTitle);
  }
}
