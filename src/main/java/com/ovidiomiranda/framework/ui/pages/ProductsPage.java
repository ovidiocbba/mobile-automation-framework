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

  private final By productsTitle = AppiumBy.id("productTV");

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
   * Selects the first product from the product list.
   *
   * <p>This method opens the product detail screen for the first available product.</p>
   */
  public void selectFirstProduct() {
    By firstProduct = AppiumBy.xpath(
        "(//android.widget.ImageView[@content-desc='Product Image'])[1]");
    actions.tap(firstProduct);
  }

  /**
   * Gets the cart badge count.
   *
   * <p>This method retrieves the number of items currently displayed
   * in the cart badge icon.</p>
   *
   * @return cart item count as text
   */
  public String getCartBadgeCount() {
    By cartBadge = AppiumBy.id("cartTV");
    return actions.getText(cartBadge);
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
