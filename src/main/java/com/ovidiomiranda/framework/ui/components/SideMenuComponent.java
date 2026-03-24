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
 * Represents the side menu panel.
 *
 * <p>Provides methods to open the menu and navigate using menu options. Reusable across pages.
 *
 * @author Ovidio Miranda
 */
public class SideMenuComponent extends BaseComponent {

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

  /**
   * Selects a menu option.
   *
   * @param option menu option
   * @throws IllegalArgumentException if menu option is invalid
   */
  public void navigateTo(MenuOption option) {
    MobileLocator locator = MENU_OPTIONS.get(option);
    if (locator == null) {
      throw new IllegalArgumentException("Invalid menu option: " + option);
    }
    actions.tap(resolve(locator));
  }
}
