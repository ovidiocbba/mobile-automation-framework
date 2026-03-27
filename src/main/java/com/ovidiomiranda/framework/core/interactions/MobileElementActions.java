package com.ovidiomiranda.framework.core.interactions;

import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.waits.ElementWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to perform common mobile element actions.
 *
 * <p>Provides reusable methods to interact with mobile elements.
 *
 * @author Ovidio Miranda
 */
public class MobileElementActions {

  private static final Logger LOGGER = LoggerFactory.getLogger(MobileElementActions.class);
  private final ElementWaits waits;
  private final DriverContext driverContext;
  private static final int MAX_SCROLL_ATTEMPTS = 6;

  /**
   * Constructor.
   *
   * @param waits utility for explicit waits
   * @param driverContext driver provider
   */
  public MobileElementActions(final ElementWaits waits, final DriverContext driverContext) {
    this.waits = waits;
    this.driverContext = driverContext;
  }

  /**
   * Taps on an element with stabilization.
   *
   * @param locator element locator
   */
  public void tap(final By locator) {
    waits.waitForClickable(locator).click();
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("Tapped on element ({})", locator);
    }
  }

  /**
   * Scrolls to element and taps it.
   *
   * @param locator element locator
   */
  public void scrollAndTap(final By locator) {
    scrollToElement(locator);
    waits.waitForVisible(locator);
    tap(locator);
  }

  /**
   * Types text into an input field.
   *
   * @param locator the locator of the input field
   * @param text the text to enter into the input field
   */
  public void type(final By locator, final String text) {
    final WebElement element = waits.waitForVisible(locator);
    element.clear();
    element.sendKeys(text);
    LOGGER.info("Entered text '{}' into element ({})", text, locator);
  }

  /**
   * Gets the text from an element.
   *
   * @param locator the element locator
   * @return the text of the element
   */
  public String getText(final By locator) {
    return waits.waitForVisible(locator).getText();
  }

  /**
   * Checks if element is displayed.
   *
   * @param locator element locator
   * @return true if element is visible, false otherwise
   */
  public boolean isDisplayed(final By locator) {
    try {
      return waits.waitForVisible(locator).isDisplayed();
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Gets the text from all visible mobile elements matching the given locator.
   *
   * @param locator the elements locator
   * @return a list of text from all matching elements; empty strings are filtered out
   */
  public List<String> getElementsText(final By locator) {
    return waits.waitForAllVisible(locator).stream()
        .map(WebElement::getText)
        .filter(text -> !text.isBlank())
        .collect(Collectors.toList());
  }

  /**
   * Scrolls until element is visible.
   *
   * @param locator element locator
   */
  public void scrollToElement(final By locator) {
    final AppiumDriver driver = driverContext.getDriver();
    LOGGER.info("Attempting to scroll to element: {}", locator);
    if (driver instanceof AndroidDriver && locator.toString().contains("accessibilityId")) {
      try {
        final String value = locator.toString().split(":")[1].trim();
        driver.findElement(
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                    + ".scrollIntoView(new UiSelector().description(\""
                    + value
                    + "\"));"));

        LOGGER.info("UiScrollable executed successfully");

      } catch (Exception e) {
        LOGGER.warn("UiScrollable failed: {}", e.getMessage());
      }
    }
    swipeUntilVisible(locator);
  }

  /**
   * Swipe until element is visible.
   *
   * @param locator element locator
   */
  private void swipeUntilVisible(final By locator) {
    int attempts = 0;

    while (attempts < MAX_SCROLL_ATTEMPTS) {

      if (isVisibleFast(locator)) {
        LOGGER.info("Element visible after {} swipes: {}", attempts, locator);
        return;
      }

      swipeDown();
      attempts++;
    }

    throw new RuntimeException("Element not found after scrolling: " + locator);
  }

  /** Performs realistic swipe gesture. */
  private void swipeDown() {
    final AppiumDriver driver = driverContext.getDriver();
    final Dimension size = driver.manage().window().getSize();

    final int startY = (int) (size.height * 0.75);
    final int endY = (int) (size.height * 0.30);

    driver.executeScript(
        "mobile: swipeGesture",
        Map.of(
            "left",
            0,
            "top",
            endY,
            "width",
            size.width,
            "height",
            startY - endY,
            "direction",
            "up",
            "percent",
            0.7));

    LOGGER.debug("Swipe performed");
  }

  /**
   * Fast visibility check
   *
   * @param locator element locator
   */
  private boolean isVisibleFast(final By locator) {
    try {
      return !driverContext.getDriver().findElements(locator).isEmpty();
    } catch (Exception e) {
      return false;
    }
  }
}
