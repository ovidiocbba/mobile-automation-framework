package com.ovidiomiranda.framework.core.driver;

import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds the AppiumDriver instance for the current scenario.
 *
 * <p>This class provides getter and setter to manage the driver across pages and steps.
 *
 * @author Ovidio Miranda
 */
public class DriverContext {

  private static final Logger LOGGER = LoggerFactory.getLogger(DriverContext.class);
  private final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

  /**
   * Sets the driver instance for the current thread.
   *
   * @param driverInstance AppiumDriver instance
   */
  public void setDriver(final AppiumDriver driverInstance) {
    driver.set(driverInstance);
  }

  /**
   * Returns the driver instance associated with the current thread.
   *
   * @return AppiumDriver instance
   */
  public AppiumDriver getDriver() {
    return driver.get();
  }

  /**
   * Properly quits the driver session and cleans ThreadLocal storage.
   */
  public void quitDriver() {
    final AppiumDriver currentDriver = driver.get();

    if (currentDriver != null) {
      try {
        currentDriver.quit();
      } catch (Exception e) {
        LOGGER.error("Error while closing driver", e);
      } finally {
        driver.remove();
      }
    }
  }
}
