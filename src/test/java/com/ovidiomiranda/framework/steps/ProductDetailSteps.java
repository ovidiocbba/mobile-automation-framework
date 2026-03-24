package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.pages.ProductDetailPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * Step definitions related to actions performed on the 'Product Detail' page.
 *
 * @author Ovidio Miranda
 */
public class ProductDetailSteps {

  private final ProductDetailPage productDetailPage;

  /**
   * Constructor.
   *
   * @param productDetailPage product detail page object
   */
  public ProductDetailSteps(ProductDetailPage productDetailPage) {
    this.productDetailPage = productDetailPage;
  }

  /** Taps the 'Add to cart' button. */
  @When("I tap the 'Add to cart' button")
  public void tapAddToCart() {
    productDetailPage.tapAddToCartButton();
  }

  /**
   * Verifies the product name in the detail screen.
   *
   * @param expectedName expected product name
   */
  @Then("the product name should be {string}")
  public void verifyProductName(String expectedName) {
    String actual = productDetailPage.getProductNameLabel();
    Assert.assertEquals(actual, expectedName, "Product name mismatch");
  }

  /**
   * Verifies the product price in the detail screen.
   *
   * @param expectedPrice expected product price
   */
  @Then("the product price should be {string}")
  public void verifyProductPrice(String expectedPrice) {
    String actual = productDetailPage.getProductPriceLabel();
    Assert.assertEquals(actual, expectedPrice, "Product price mismatch");
  }
}
