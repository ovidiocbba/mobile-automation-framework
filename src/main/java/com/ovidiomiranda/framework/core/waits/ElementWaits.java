package com.ovidiomiranda.framework.core.waits;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Provides wait utilities for UI elements.
 *
 * @author Ovidio Miranda
 */
public final class ElementWaits {

  /**
   * Private constructor to prevent instantiation.
   */
  private ElementWaits() {
  }

  /**
   * Waits until an element is visible.
   *
   * @param locator element locator
   * @return visible WebElement
   */
  public static WebElement waitForVisible(By locator) {
    return WaitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Waits until the element is clickable.
   *
   * @param locator element locator
   * @return clickable WebElement
   */
  public static WebElement waitForClickable(By locator) {
    return WaitManager.getWait().until(ExpectedConditions.elementToBeClickable(locator));
  }

  /**
   * Waits until the element becomes invisible.
   *
   * @param locator element locator
   * @return true if element becomes invisible
   */
  public static boolean waitForInvisible(By locator) {
    return WaitManager.getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  /**
   * Waits until the element exists in the DOM.
   *
   * @param locator element locator
   * @return located WebElement
   */
  public static WebElement waitForPresence(By locator) {
    return WaitManager.getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
  }
}
