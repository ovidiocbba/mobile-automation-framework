package com.ovidiomiranda.framework.steps;

import com.ovidiomiranda.framework.ui.components.LogoutModalComponent;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;

/**
 * Step definitions related to the 'Log Out' modal.
 *
 * @author Ovidio Miranda
 */
public class LogoutModalSteps {

  private final LogoutModalComponent logoutModal;

  /**
   * Constructor.
   *
   * @param logoutModal logout modal component
   */
  public LogoutModalSteps(LogoutModalComponent logoutModal) {
    this.logoutModal = logoutModal;
  }

  /** Taps the 'LOGOUT' button in the modal. */
  @And("I tap the 'LOGOUT' button")
  public void tapTheLogoutButton() {
    logoutModal.tapLogoutButton();
  }

  /** Taps the 'CANCEL' button in the modal. */
  @And("I tap the 'CANCEL' button")
  public void tapTheCancelButton() {
    logoutModal.tapCancelButton();
  }

  /**
   * Verifies the title of the logout modal.
   *
   * @param expected expected modal title text
   */
  @Then("the logout modal title should be {string}")
  public void verifyModalTitle(String expected) {
    Assert.assertEquals(logoutModal.getModalTitle(), expected);
  }

  /** Verifies that the 'Log Out' modal. is displayed. */
  @Then("a 'Logout Confirmation' popup should be displayed")
  public void verifyLogoutConfirmationPopup() {
    Assert.assertTrue(logoutModal.isDisplayed(), "Logout confirmation modal was not displayed");
  }
}
