package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;

/**
 * Represents the 'Product' page and its components.
 *
 * @author Ovidio Miranda
 */
public class ProductsPage extends BasePage {

  private final MobileLocator productsTitle =
      new MobileLocator(
          AppiumBy.xpath("//android.widget.TextView[@text='PRODUCTS']"),
          AppiumBy.xpath("//XCUIElementTypeStaticText[@name='PRODUCTS']"));

  private final MobileLocator firstProduct =
      new MobileLocator(
          AppiumBy.xpath("(//android.view.ViewGroup[@content-desc='test-Item'])[1]"),
          AppiumBy.xpath("(//XCUIElementTypeOther[@name='test-Item'])[1]"));

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public ProductsPage(
      final ConfigValidator config,
      final DriverContext driverContext,
      final MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Selects the first product from the product list.
   *
   * <p>This method opens the product detail screen for the first available product.
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

    final MobileLocator product =
        new MobileLocator(
            AppiumBy.xpath(
                "//android.widget.TextView[@content-desc='test-Item title' and @text='"
                    + productName
                    + "']"),
            AppiumBy.iOSClassChain(
                "**/XCUIElementTypeStaticText[`label == '" + productName + "'`]"));
    actions.tap(resolve(product));
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
