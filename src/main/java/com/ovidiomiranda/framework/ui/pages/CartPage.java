package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;

/**
 * Represents the 'Cart' page and its components.
 *
 * @author Ovidio Miranda
 */
public class CartPage extends BasePage {

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public CartPage(
      final ConfigValidator config,
      final DriverContext driverContext,
      final MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Verifies if a product is displayed in the cart.
   *
   * @param productName expected product name
   * @return true if displayed
   */
  public boolean isProductDisplayed(final String productName) {
    final MobileLocator productsTitle =
        new MobileLocator(
            AppiumBy.androidUIAutomator("new UiSelector().text(\"" + productName + "\")"),
            AppiumBy.accessibilityId(productName));
    return actions.isDisplayed(resolve(productsTitle));
  }
}
