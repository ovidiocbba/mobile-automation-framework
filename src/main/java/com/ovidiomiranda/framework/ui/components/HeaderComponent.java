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

  private final MobileLocator hamburgerButton =
      new MobileLocator(AppiumBy.id("menuIV"), AppiumBy.accessibilityId("menu_button"));

  private final MobileLocator cartBadge =
      new MobileLocator(
          AppiumBy.id("cartTV"), AppiumBy.xpath("//XCUIElementTypeStaticText[@name='cart_badge']"));

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param driverContext driver context
   * @param actions mobile actions utility
   */
  public HeaderComponent(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /** Opens the side menu. */
  public void openMenu() {
    actions.tap(resolve(hamburgerButton));
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
