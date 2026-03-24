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
  private final By productNameLabel = AppiumBy.id("productTV");
  private final By productPriceLabel = AppiumBy.id("priceTV");

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public ProductDetailPage(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /** Taps the 'Add to Cart' button. */
  public void tapAddToCartButton() {
    actions.tap(addToCartButton);
  }

  /**
   * Returns the product name.
   *
   * @return product name displayed on the product detail page
   */
  public String getProductNameLabel() {
    return actions.getText(productNameLabel);
  }

  /**
   * Returns the product price.
   *
   * @return product price displayed on the product detail page
   */
  public String getProductPriceLabel() {
    return actions.getText(productPriceLabel);
  }
}
