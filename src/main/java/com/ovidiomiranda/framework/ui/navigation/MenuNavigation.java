package com.ovidiomiranda.framework.ui.navigation;

import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGIN;

import com.ovidiomiranda.framework.ui.components.SideMenuComponent;

/**
 * Handles navigation through the app menu.
 *
 * <p>Provides specific navigation methods to app screens using the side menu.
 *
 * @author Ovidio Miranda
 */
public class MenuNavigation {

  private final SideMenuComponent sideMenu;

  /**
   * Constructor.
   *
   * @param sideMenu side menu component
   */
  public MenuNavigation(SideMenuComponent sideMenu) {
    this.sideMenu = sideMenu;
  }

  /** Navigates to 'Login' screen using the side menu. */
  public void goToLogin() {
    sideMenu.navigateTo(LOGIN);
  }
}
