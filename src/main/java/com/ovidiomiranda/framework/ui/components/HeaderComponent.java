package com.ovidiomiranda.framework.ui.components;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumBy;

/**
 * Represents the app header (top bar).
 *
 * <p>Contains global UI elements like menu and cart.
 */
public class HeaderComponent extends BaseComponent {

  private final MobileLocator menuButton =
      new MobileLocator(
          AppiumBy.xpath(
              "//android.view.ViewGroup[@content-desc='test-Menu']"
                  + "/android.view.ViewGroup"
                  + "/android.widget.ImageView"),
          AppiumBy.accessibilityId("test-Menu"));

  private final MobileLocator cartBadge =
      new MobileLocator(
          AppiumBy.xpath(
              "//android.view.ViewGroup[@content-desc='test-Cart']//android.widget.TextView"),
          AppiumBy.accessibilityId("test-Cart"));

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param driverContext driver context
   * @param actions mobile actions utility
   */
  public HeaderComponent(
      final ConfigValidator config,
      final DriverContext driverContext,
      final MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /**
   * Opens the side navigation menu.
   *
   * <p>Note: On iOS, we use a precision tap at 80% of the element's height. This is because the top
   * area of the menu button in this app is often unresponsive due to overlapping UI layers or Safe
   * Area restrictions.
   */
  public void openMenu() {
    // 0.8 (80%) ensures we hit the active area at the bottom of the button
    actions.tapWithPrecision(resolve(menuButton), 0.8);
  }

  /** Taps the 'Cart' badge. */
  public void tapCartBadge() {
    actions.tap(resolve(cartBadge));
  }

  /**
   * Gets the 'Cart' badge count.
   *
   * <p>This method retrieves the number of items currently displayed in the cart badge icon.
   *
   * @return number of items as text
   */
  public String getCartBadgeCount() {
    return actions.getText(resolve(cartBadge));
  }
}
