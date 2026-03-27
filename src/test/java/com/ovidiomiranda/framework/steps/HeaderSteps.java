package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.components.HeaderComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions for Header component.
 *
 * @author Ovidio Miranda
 */
public class HeaderSteps {

  private final HeaderComponent header;

  /**
   * Constructor.
   *
   * @param header header component instance
   */
  public HeaderSteps(final HeaderComponent header) {
    this.header = header;
  }

  /** Taps the 'Menu' button. */
  @When("I tap the 'Menu' button")
  public void tapMenuButton() {
    header.openMenu();
  }

  /** Taps the 'Cart' badge. */
  @When("I tap the 'Cart' badge")
  public void tapCartBadge() {
    header.tapCartBadge();
  }

  /**
   * Verifies the 'Cart' badge count.
   *
   * @param expectedCount expected number of items in the cart
   */
  @Then("the 'Cart' badge should show {int} item")
  public void verifyCartBadgeCount(final int expectedCount) {
    final String actual = header.getCartBadgeCount();
    Assert.assertEquals(actual, String.valueOf(expectedCount), "Cart count mismatch");
  }
}
