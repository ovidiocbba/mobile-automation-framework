package com.ovidiomiranda.framework.core.interactions;

import com.ovidiomiranda.framework.core.waits.ElementWaits;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to perform common mobile element actions.
 *
 * <p>Provides reusable methods to interact with mobile elements</p>.
 *
 * @author Ovidio Miranda
 */
public class MobileElementActions {

  private static final Logger LOGGER = LoggerFactory.getLogger(MobileElementActions.class);
  private final ElementWaits waits;

  /**
   * Constructor.
   *
   * @param waits utility for explicit waits
   */
  public MobileElementActions(ElementWaits waits) {
    this.waits = waits;
  }

  /**
   * Taps on an element.
   *
   * @param locator element locator
   */
  public void tap(By locator) {
    waits.waitForClickable(locator).click();
    LOGGER.info("Tapped on element ({})", locator);
  }

  /**
   * Types text into an input field.
   *
   * @param locator element locator
   * @param text    value to enter
   */
  public void type(By locator, String text) {
    WebElement element = waits.waitForVisible(locator);
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
  public String getText(By locator) {
    return waits.waitForVisible(locator).getText();
  }

  /**
   * Checks if element is displayed.
   *
   * @param locator element locator
   * @return true if element is visible, false otherwise
   */
  public boolean isDisplayed(By locator) {
    try {
      return waits.waitForVisible(locator).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets text from multiple elements.
   *
   * @param locator locator for elements
   * @return list of text values for visible elements
   */
  public List<String> getElementsText(By locator) {
    return waits.waitForAllVisible(locator).stream().map(WebElement::getText)
        .filter(text -> !text.isBlank()).collect(Collectors.toList());
  }
}
