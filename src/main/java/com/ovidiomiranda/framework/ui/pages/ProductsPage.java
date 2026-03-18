package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Product' page and its components.
 *
 * @author Ovidio Miranda
 */
public class ProductsPage extends BasePage {

  private final By productsTitle = AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV");

  /**
   * Constructor.
   *
   * @param driverContext driver context
   * @param actions       MobileElementActions utility
   */
  public ProductsPage(DriverContext driverContext, MobileElementActions actions) {
    super(driverContext, actions);
  }

  /**
   * Verifies if Products page is displayed.
   *
   * @return true if page is visible, false otherwise
   */
  public boolean isDisplayed() {
    return actions.isDisplayed(productsTitle);
  }
}
