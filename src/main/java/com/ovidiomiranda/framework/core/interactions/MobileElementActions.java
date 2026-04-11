package com.ovidiomiranda.framework.core.interactions;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static java.util.Locale.ENGLISH;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
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
import org.openqa.selenium.remote.RemoteWebElement;
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
  private static final int MAX_SCROLL_ATTEMPTS = 6;

  private final ElementWaits waits;
  private final ConfigValidator config;
  private final DriverContext driverContext;

  public MobileElementActions(
      final ElementWaits waits, final ConfigValidator config, final DriverContext driverContext) {
    this.waits = waits;
    this.config = config;
    this.driverContext = driverContext;
  }

  /**
   * Taps on an element with stabilization.
   *
   * @param locator element locator
   */
  public void tap(final By locator) {
    waits.waitForClickable(locator).click();
    LOGGER.info("Tapped on element ({})", locator);
  }

  /**
   * Taps on an element using a specific position. Useful for iOS elements with "dead zones" where
   * center tap fails.
   *
   * @param locator the element locator
   * @param ypercent the height percentage (0.5 = center, 0.8 = bottom)
   */
  public void tapWithPrecision(final By locator, final double ypercent) {
    final WebElement element = waits.waitForClickable(locator);
    final String platform = config.require(PLATFORM).toUpperCase(ENGLISH);

    if ("IOS".equals(platform)) {
      // Native iOS tap at a specific point
      driverContext
          .getDriver()
          .executeScript(
              "mobile: tap",
              Map.of(
                  "elementId",
                  ((RemoteWebElement) element).getId(),
                  "x",
                  element.getSize().getWidth() / 2,
                  "y",
                  (int) (element.getSize().getHeight() * ypercent)));
      LOGGER.info("Tapped with precision at {}% height on element ({})", ypercent * 100, locator);
    } else {
      tap(locator);
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
    final String platform = config.require(PLATFORM).toUpperCase(ENGLISH);

    LOGGER.info("Attempting to scroll to element: {}", locator);

    if ("ANDROID".equals(platform)
        && driver instanceof AndroidDriver
        && locator.toString().contains("accessibilityId")) {

      try {
        final String value = locator.toString().split(":")[1].trim();
        driver.findElement(
            AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))"
                    + ".scrollIntoView(new UiSelector().description(\""
                    + value
                    + "\"));"));

        LOGGER.info("UiScrollable executed successfully");
        return;
      } catch (Exception e) {
        LOGGER.warn("UiScrollable failed, fallback to swipe: {}", e.getMessage());
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
      LOGGER.debug("Swipe attempt {} for element {}", attempts + 1, locator);
      swipeDown();
      attempts++;
    }

    throw new RuntimeException(
        "Element not found after " + MAX_SCROLL_ATTEMPTS + " scroll attempts: " + locator);
  }

  /** Performs platform-specific swipe gesture. */
  private void swipeDown() {
    final AppiumDriver driver = driverContext.getDriver();
    final String platform = config.require(PLATFORM).toUpperCase(ENGLISH);
    final Dimension size = driver.manage().window().getSize();

    try {

      if ("IOS".equals(platform)) {

        int startX = size.width / 2;
        int startY = (int) (size.height * 0.75);
        int endY = (int) (size.height * 0.30);

        driver.executeScript(
            "mobile: dragFromToForDuration",
            Map.of(
                "duration", 0.5,
                "fromX", startX,
                "fromY", startY,
                "toX", startX,
                "toY", endY));

        LOGGER.debug("iOS swipe DOWN performed");

      } else {

        driver.executeScript(
            "mobile: swipeGesture",
            Map.of(
                "left",
                0,
                "top",
                (int) (size.height * 0.30),
                "width",
                size.width,
                "height",
                (int) (size.height * 0.45),
                "direction",
                "up",
                "percent",
                0.7));

        LOGGER.debug("Android swipe performed");
      }

      Thread.sleep(400);

    } catch (Exception e) {
      LOGGER.warn("Swipe failed: {}", e.getMessage());
    }
  }

  /**
   * Fast visibility check.
   *
   * @param locator element locator
   */
  private boolean isVisibleFast(final By locator) {
    try {
      List<WebElement> elements = driverContext.getDriver().findElements(locator);
      return elements.stream().anyMatch(WebElement::isDisplayed);
    } catch (Exception e) {
      return false;
    }
  }
}
