package com.ovidiomiranda.framework.core.interactions;

import com.ovidiomiranda.framework.core.waits.ElementWaits;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for common mobile actions.
 *
 * <p>Provides reusable methods to interact with mobile elements</p>.
 *
 * @author Ovidio Miranda
 */
public final class MobileElementActions {

  private static final Logger LOGGER = LoggerFactory.getLogger(MobileElementActions.class);

  private MobileElementActions() {
  }

  /**
   * Taps on an element.
   *
   * @param locator element locator
   */
  public static void tap(By locator) {
    ElementWaits.waitForClickable(locator).click();
    LOGGER.info("Tapped on element ({})", locator);
  }

  /**
   * Types text into an input field.
   *
   * @param locator element locator
   * @param text    value to enter
   */
  public static void type(By locator, String text) {
    WebElement element = ElementWaits.waitForVisible(locator);
    element.clear();
    element.sendKeys(text);
    LOGGER.info("Entered text '{}' into element ({})", text, locator);
  }

  /**
   * Gets text from an element.
   *
   * @param locator element locator
   * @return text value
   */
  public static String getText(By locator) {
    return ElementWaits.waitForVisible(locator).getText();
  }

  /**
   * Checks if element is displayed.
   *
   * @param locator element locator
   * @return true if visible
   */
  public static boolean isDisplayed(By locator) {
    try {
      return ElementWaits.waitForVisible(locator).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets text from multiple elements.
   *
   * @param locator elements locator
   * @return list of text values
   */
  public static List<String> getElementsText(By locator) {
    return ElementWaits.waitForAllVisible(locator).stream().map(WebElement::getText)
        .filter(text -> !text.isBlank()).collect(Collectors.toList());
  }
}
