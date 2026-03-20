package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
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

  private final By addToCartButton = AppiumBy.id("cartBt");
  private final By productName = AppiumBy.id("productTV");
  private final By productPrice = By.id("priceTV");

  /**
   * Constructor.
   *
   * @param config        config validator
   * @param driverContext driver context
   * @param actions       MobileElementActions utility
   */
  public ProductDetailPage(ConfigValidator config, DriverContext driverContext,
      MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Adds the current product to the cart.
   *
   * <p>This method taps on the 'Add to Cart' button.</p>
   */
  public void addToCart() {
    actions.tap(addToCartButton);
  }

  /**
   * Returns the product name.
   */
  public String getProductName() {
    return actions.getText(productName);
  }

  /**
   * Returns the product price.
   */
  public String getProductPrice() {
    return actions.getText(productPrice);
  }
}
