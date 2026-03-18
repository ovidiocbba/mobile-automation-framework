package com.ovidiomiranda.framework.ui.navigation;

import com.ovidiomiranda.framework.ui.components.SideMenuComponent;
import com.ovidiomiranda.framework.ui.enums.MenuOption;

/**
 * Handles app Menu navigation flows.
 *
 * @author Ovidio Miranda
 */
public class MenuNavigation {

  private final SideMenuComponent sideMenu = new SideMenuComponent();

  /**
   * Navigates to 'Login' screen.
   */
  public void goToLogin() {
    sideMenu.navigateTo(MenuOption.LOGIN);
  }
}
