package com.ovidiomiranda.framework.ui.components;

import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGIN;
import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGOUT;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import com.ovidiomiranda.framework.ui.enums.MenuOption;
import io.appium.java_client.AppiumBy;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the side navigation menu (hamburger menu).
 *
 * <p>Provides methods to open the menu and navigate using menu options. Reusable across pages.
 *
 * @author Ovidio Miranda
 */
public class SideMenuComponent extends BaseComponent {

  /** Hamburger menu button locator for both platforms. */
  private final MobileLocator hamburgerButton =
      new MobileLocator(AppiumBy.id("menuIV"), AppiumBy.accessibilityId("menu_button"));

  private static final Map<MenuOption, MobileLocator> MENU_OPTIONS =
      new EnumMap<>(MenuOption.class);

  static {
    MENU_OPTIONS.put(
        LOGIN,
        new MobileLocator(
            AppiumBy.accessibilityId("Login Menu Item"),
            AppiumBy.accessibilityId("login_menu_item")));

    MENU_OPTIONS.put(
        LOGOUT,
        new MobileLocator(
            AppiumBy.accessibilityId("Logout Menu Item"),
            AppiumBy.accessibilityId("logout_menu_item")));
  }

  private final MobileLocator cartBadge =
      new MobileLocator(
          AppiumBy.id("cartTV"), AppiumBy.xpath("//XCUIElementTypeStaticText[@name='cart_badge']"));

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions MobileElementActions utility
   */
  public SideMenuComponent(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }

  /** Opens the side menu by tapping the hamburger button. */
  public void openMenu() {
    actions.tap(resolve(hamburgerButton));
  }

  /**
   * Navigates to a menu option.
   *
   * @param option MenuOption to navigate to
   * @throws IllegalArgumentException if menu option is invalid
   */
  public void navigateTo(MenuOption option) {
    openMenu();
    MobileLocator locator = MENU_OPTIONS.get(option);
    if (locator == null) {
      throw new IllegalArgumentException("Invalid menu option: " + option);
    }
    actions.tap(resolve(locator));
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
   * @return cart item count as text
   */
  public String getCartBadgeCount() {
    return actions.getText(resolve(cartBadge));
  }
}
