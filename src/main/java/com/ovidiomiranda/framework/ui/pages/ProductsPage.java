package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

/**
 * Represents the 'Product' page and its components.
 *
 * @author Ovidio Miranda
 */
public class ProductsPage extends BasePage {

  private final MobileLocator productsTitle = new MobileLocator(AppiumBy.id("productTV"),
      AppiumBy.accessibilityId("products_title"));

  private final MobileLocator firstProduct = new MobileLocator(
      AppiumBy.xpath("(//android.widget.ImageView[@content-desc='Product Image'])[1]"),
      AppiumBy.xpath("(//XCUIElementTypeImage[@name='Product Image'])[1]"));

  /**
   * Constructor.
   *
   * @param config        config validator
   * @param driverContext driver context
   * @param actions       MobileElementActions utility
   */
  public ProductsPage(ConfigValidator config, DriverContext driverContext,
      MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Selects the first product from the product list.
   *
   * <p>This method opens the product detail screen for the first available product.</p>
   */
  public void selectFirstProduct() {
    actions.tap(resolve(firstProduct));
  }

  /**
   * Selects a product by name.
   *
   * @param productName name of the product to select
   */
  public void selectProductByName(final String productName) {
    final By product = By.xpath("//android.widget.TextView[@text='" + productName
        + "']/preceding-sibling::android.widget.ImageView");
    actions.tap(product);
  }

  /**
   * Verifies if Products page is displayed.
   *
   * @return true if page is visible, false otherwise
   */
  public boolean isDisplayed() {
    return actions.isDisplayed(resolve(productsTitle));
  }
}
