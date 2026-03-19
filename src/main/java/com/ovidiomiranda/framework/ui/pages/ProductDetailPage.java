package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Product Detail' page and its components.
 *
 * @author Ovidio Miranda
 */
public class ProductDetailPage extends BasePage {

  private final By addToCartButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt");

  /**
   * Constructor.
   *
   * @param driverContext driver context
   * @param actions       MobileElementActions utility
   */
  public ProductDetailPage(DriverContext driverContext, MobileElementActions actions) {
    super(driverContext, actions);
  }

  /**
   * Adds the current product to the cart.
   *
   * <p>This method taps on the 'Add to Cart' button.</p>
   */
  public void addToCart() {
    actions.tap(addToCartButton);
  }
}
