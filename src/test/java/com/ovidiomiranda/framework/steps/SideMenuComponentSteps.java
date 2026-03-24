package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.components.SideMenuComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for the side menu component.
 *
 * @author Ovidio Miranda
 */
public class SideMenuComponentSteps {

  private final SideMenuComponent sideMenu;

  /**
   * Constructor.
   *
   * @param sideMenu side menu component
   */
  public SideMenuComponentSteps(SideMenuComponent sideMenu) {
    this.sideMenu = sideMenu;
  }

  /** Taps the 'Cart' badge. */
  @When("I tap the 'Cart' badge")
  public void tapCartBadge() {
    sideMenu.tapCartBadge();
  }

  /**
   * Verifies the 'Cart' badge count.
   *
   * @param expectedCount expected number of items in the cart
   */
  @Then("the 'Cart' badge should show {int} item")
  public void verifyCartBadgeCount(final int expectedCount) {
    final String actual = sideMenu.getCartBadgeCount();
    Assert.assertEquals(actual, String.valueOf(expectedCount), "Cart count mismatch");
  }
}
