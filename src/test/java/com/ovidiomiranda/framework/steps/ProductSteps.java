package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.pages.ProductDetailPage;
import com.ovidiomiranda.framework.ui.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions related to actions performed on the 'Product' page.
 *
 * @author Ovidio Miranda
 */
public class ProductSteps {

  private final ProductsPage productsPage;
  private final ProductDetailPage productDetailPage;

  /**
   * Constructor.
   *
   * @param productsPage products page object
   * @param productDetailPage product detail page object
   */
  public ProductSteps(final ProductsPage productsPage, final ProductDetailPage productDetailPage) {
    this.productsPage = productsPage;
    this.productDetailPage = productDetailPage;
  }

  /**
   * Adds a product to the cart.
   *
   * <p>This step selects the first product from the product list and adds it to the cart from the
   * product detail screen.
   */
  @When("I add a product to the cart")
  public void addProductToCart() {
    productsPage.selectFirstProduct();
    productDetailPage.tapAddToCartButton();
  }

  /**
   * Opens a product by name.
   *
   * @param productName name of the product to open
   */
  @When("I open the product {string}")
  public void openProductByName(final String productName) {
    productsPage.selectProductByName(productName);
  }

  /** Verifies the 'Products' screen is displayed. */
  @Then("the 'Products' screen should be displayed")
  public void verifyProductsScreenIsDisplayed() {
    Assert.assertTrue(productsPage.isDisplayed(), "Products screen not displayed");
  }
}
