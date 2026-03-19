package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.pages.ProductDetailPage;
import com.ovidiomiranda.framework.ui.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions related to actions performed on the 'Product' page.
 *
 * <p>This class handles actions such as selecting products and adding them to the cart.</p>
 *
 * @author Ovidio Miranda
 */
public class ProductSteps {

  private final ProductsPage productsPage;
  private final ProductDetailPage productDetailPage;

  /**
   * Constructor.
   *
   * @param productsPage      products page object
   * @param productDetailPage product detail page object
   */
  public ProductSteps(ProductsPage productsPage, ProductDetailPage productDetailPage) {
    this.productsPage = productsPage;
    this.productDetailPage = productDetailPage;
  }

  /**
   * Adds a product to the cart.
   *
   * <p>This step selects the first product from the product list
   * and adds it to the cart from the product detail screen.</p>
   */
  @When("the user adds a product to the cart")
  public void addProductToCart() {
    productsPage.selectFirstProduct();
    productDetailPage.addToCart();
  }

  /**
   * Verifies that the cart badge shows the expected number of items.
   *
   * <p>This step validates that the cart badge is updated correctly
   * after adding a product.</p>
   *
   * @param expectedCount expected number of items in the cart
   */
  @Then("the cart badge should show {int} item")
  public void validateCartBadge(int expectedCount) {
    String actual = productsPage.getCartBadgeCount();
    Assert.assertEquals(actual, String.valueOf(expectedCount), "Cart count mismatch");
  }
}
