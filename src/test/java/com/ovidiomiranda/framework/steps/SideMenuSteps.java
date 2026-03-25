package com.ovidiomiranda.framework.steps;

import static com.ovidiomiranda.framework.ui.enums.MenuOption.LOGOUT;

import com.ovidiomiranda.framework.ui.components.SideMenuComponent;
import io.cucumber.java.en.And;

/**
 * Step definitions for the Side Menu component.
 *
 * @author Ovidio Miranda
 */
public class SideMenuSteps {
  private final SideMenuComponent sideMenu;

  /**
   * Constructor.
   *
   * @param sideMenu side menu component instance
   */
  public SideMenuSteps(SideMenuComponent sideMenu) {
    this.sideMenu = sideMenu;
  }

  /** Taps the 'Logout' option from the side menu. */
  @And("I tap the 'Logout' option")
  public void tapTheLogoutOption() {
    sideMenu.navigateTo(LOGOUT);
  }
}
