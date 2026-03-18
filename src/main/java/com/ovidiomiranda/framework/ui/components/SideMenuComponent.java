package com.ovidiomiranda.framework.ui.components;

import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.ui.enums.MenuOption;
import io.appium.java_client.AppiumBy;
import java.util.EnumMap;
import java.util.Map;
import org.openqa.selenium.By;

/**
 * Represents the side navigation menu (hamburger menu).
 *
 * <p>This component is reusable across different pages</p>.
 *
 * @author Ovidio Miranda
 */
public class SideMenuComponent {

  private final By hamburgerButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV");
  private static final Map<MenuOption, By> MENU_OPTIONS = new EnumMap<>(MenuOption.class);

  static {
    MENU_OPTIONS.put(MenuOption.LOGIN, AppiumBy.accessibilityId("Login Menu Item"));
    MENU_OPTIONS.put(MenuOption.LOGOUT, AppiumBy.accessibilityId("Logout Menu Item"));
  }

  /**
   * Opens the side menu.
   */
  public void openMenu() {
    MobileElementActions.tap(hamburgerButton);
  }

  /**
   * Navigates using menu option.
   */
  public void navigateTo(MenuOption option) {
    openMenu();
    By locator = MENU_OPTIONS.get(option);
    if (locator == null) {
      throw new IllegalArgumentException("Invalid menu option: " + option);
    }
    MobileElementActions.tap(locator);
  }
}
