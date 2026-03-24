package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Cart' page and its components.
 *
 * @author Ovidio Miranda
 */
public class CartPage extends BasePage {

  private final By productNameLabel = AppiumBy.id("titleTV");

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public CartPage(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Returns the product name displayed in the cart.
   *
   * @return product name displayed in the cart
   */
  public String getProductNameLabel() {
    return actions.getText(productNameLabel);
  }
}
