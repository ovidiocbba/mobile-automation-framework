package com.ovidiomiranda.framework.core.waits;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Utility class for explicit waits on UI elements.
 *
 * <p>Provides common wait conditions for mobile.</p>
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

  /**
   * Waits until all elements are visible.
   *
   * @param locator elements locator
   * @return list of visible elements
   */
  public static List<WebElement> waitForAllVisible(By locator) {
    return WaitManager.getWait()
        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  /**
   * Waits until at least one element is present.
   *
   * @param locator elements locator
   * @return list of elements
   */
  public static List<WebElement> waitForAllPresence(By locator) {
    return WaitManager.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  /**
   * Waits until element contains specific text.
   *
   * @param locator element locator
   * @param text    expected text
   * @return true if text is present
   */
  public static boolean waitForText(By locator, String text) {
    return WaitManager.getWait()
        .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }
}
