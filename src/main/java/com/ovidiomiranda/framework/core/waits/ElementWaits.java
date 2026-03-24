package com.ovidiomiranda.framework.core.waits;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Utility class for common explicit waits on mobile elements.
 *
 * <p>Provides common wait conditions for mobile.
 *
 * @author Ovidio Miranda
 */
public class ElementWaits {

  private final WaitManager waitManager;

  /**
   * Constructor.
   *
   * @param waitManager wait manager providing WebDriverWait
   */
  public ElementWaits(WaitManager waitManager) {
    this.waitManager = waitManager;
  }

  /**
   * Waits until an element is visible.
   *
   * @param locator element locator
   * @return visible WebElement
   */
  public WebElement waitForVisible(By locator) {
    return waitManager.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  /**
   * Waits until an element is clickable.
   *
   * @param locator element locator
   * @return clickable WebElement
   */
  public WebElement waitForClickable(By locator) {
    return waitManager.getWait().until(ExpectedConditions.elementToBeClickable(locator));
  }

  /**
   * Waits until an element is invisible.
   *
   * @param locator element locator
   * @return true if element becomes invisible
   */
  public boolean waitForInvisible(By locator) {
    return waitManager.getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  /**
   * Waits until an element is present in DOM.
   *
   * @param locator element locator
   * @return located WebElement
   */
  public WebElement waitForPresence(By locator) {
    return waitManager.getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
  }

  /**
   * Waits until all elements are visible.
   *
   * @param locator elements locator
   * @return list of visible elements
   */
  public List<WebElement> waitForAllVisible(By locator) {
    return waitManager
        .getWait()
        .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
  }

  /**
   * Waits until at least one element is present.
   *
   * @param locator elements locator
   * @return list of elements
   */
  public List<WebElement> waitForAllPresence(By locator) {
    return waitManager.getWait().until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
  }

  /**
   * Waits until an element contains specific text.
   *
   * @param locator element locator
   * @param text expected text
   * @return true if text is present in element
   */
  public boolean waitForText(By locator, String text) {
    return waitManager
        .getWait()
        .until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
  }
}
