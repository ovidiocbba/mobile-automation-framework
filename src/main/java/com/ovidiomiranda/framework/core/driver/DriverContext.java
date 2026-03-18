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

  private AppiumDriver driver;

  /**
   * Sets the driver instance.
   *
   * @param driver AppiumDriver instance
   */
  public void setDriver(AppiumDriver driver) {
    this.driver = driver;
  }

  /**
   * Returns the current driver.
   *
   * @return AppiumDriver instance
   */
  public AppiumDriver getDriver() {
    return driver;
  }
}
