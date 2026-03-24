package com.ovidiomiranda.framework.ui.navigation;

import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGIN;
import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGOUT;

import com.ovidiomiranda.framework.ui.components.HeaderComponent;
import com.ovidiomiranda.framework.ui.components.SideMenuComponent;

/**
 * Handles app navigation.
 *
 * <p>Provides specific navigation methods to app screens using the side menu.
 *
 * @author Ovidio Miranda
 */
public class MenuNavigation {

  private final HeaderComponent header;
  private final SideMenuComponent sideMenu;

  /**
   * Constructor.
   *
   * @param sideMenu side menu component
   */
  public MenuNavigation(HeaderComponent header, SideMenuComponent sideMenu) {
    this.header = header;
    this.sideMenu = sideMenu;
  }

  /** Navigates to 'Login' screen using the side menu. */
  public void goToLogin() {
    header.openMenu();
    sideMenu.navigateTo(LOGIN);
  }

  /** Tap to 'Logout'. */
  public void logout() {
    header.openMenu();
    sideMenu.navigateTo(LOGOUT);
  }
}
