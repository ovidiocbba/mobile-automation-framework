package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.pages.CartPage;
import io.cucumber.java.en.Then;
import org.testng.Assert;

/**
 * Step definitions related to actions performed on the 'Cart' page.
 *
 * @author Ovidio Miranda
 */
public class CartSteps {

  private final CartPage cartPage;

  /**
   * Constructor.
   *
   * @param cartPage cart page object
   */
  public CartSteps(final CartPage cartPage) {
    this.cartPage = cartPage;
  }

  /**
   * Verifies a product is displayed in the cart.
   *
   * @param expectedProduct expected product name displayed in the cart
   */
  @Then("the product {string} should be displayed in the Cart")
  public void verifyProductInCart(final String expectedProduct) {
    final boolean actual = cartPage.isProductDisplayed(expectedProduct);
    Assert.assertTrue(actual, "Expected product not found in cart: " + expectedProduct);
  }
}
