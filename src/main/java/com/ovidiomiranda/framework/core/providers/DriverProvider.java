package com.ovidiomiranda.framework.core.providers;

import io.appium.java_client.AppiumDriver;

/**
 * Defines how a mobile driver should be created.
 *
 * <p>Each platform must implement this interface.
 *
 * @author Ovidio Miranda
 */
public interface DriverProvider {

  /**
   * Creates and returns a mobile driver.
   *
   * @return AppiumDriver instance
   */
  AppiumDriver getDriver();
}
