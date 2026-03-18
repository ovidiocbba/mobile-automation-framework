package com.ovidiomiranda.framework.ui.components;

import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGIN;
import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGOUT;

import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.ui.enums.MenuOption;
import io.appium.java_client.AppiumBy;
import java.util.EnumMap;
import java.util.Map;
import org.openqa.selenium.By;

/**
 * Represents the side navigation menu (hamburger menu).
 *
 * <p>Provides methods to open the menu and navigate using menu options. Reusable across pages.</p>
 *
 * @author Ovidio Miranda
 */
public class SideMenuComponent {

  private final MobileElementActions actions;
  private final By hamburgerButton = AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV");
  private static final Map<MenuOption, By> MENU_OPTIONS = new EnumMap<>(MenuOption.class);

  static {
    MENU_OPTIONS.put(LOGIN, AppiumBy.accessibilityId("Login Menu Item"));
    MENU_OPTIONS.put(LOGOUT, AppiumBy.accessibilityId("Logout Menu Item"));
  }

  /**
   * Constructor.
   *
   * @param actions MobileElementActions instance for element interaction
   */
  public SideMenuComponent(MobileElementActions actions) {
    this.actions = actions;
  }

  /**
   * Opens the side menu by tapping the hamburger button.
   */
  public void openMenu() {
    actions.tap(hamburgerButton);
  }

  /**
   * Navigates to a menu option.
   *
   * @param option MenuOption to navigate to
   * @throws IllegalArgumentException if menu option is invalid
   */
  public void navigateTo(MenuOption option) {
    openMenu();
    By locator = MENU_OPTIONS.get(option);
    if (locator == null) {
      throw new IllegalArgumentException("Invalid menu option: " + option);
    }
    actions.tap(locator);
  }
}
