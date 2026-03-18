package com.ovidiomiranda.framework.core.driver;

import io.appium.java_client.AppiumDriver;

/**
 * Holds the AppiumDriver instance for the current scenario.
 *
 * <p>This class provides getter and setter to manage the driver across pages and steps.</p>
 *
 * @author Ovidio Miranda
 */
public class DriverContext {

  private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

  /**
   * Sets the driver instance for the current thread.
   *
   * @param driverInstance AppiumDriver instance
   */
  public void setDriver(AppiumDriver driverInstance) {
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
   * Removes the driver instance from the current thread.
   *
   * <p>This method should be called after test execution to prevent memory leaks
   * and ensure proper cleanup in parallel environments.</p>
   */
  public void removeDriver() {
    driver.remove();
  }
}
