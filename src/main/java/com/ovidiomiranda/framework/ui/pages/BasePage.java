package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.driver.DriverManager;
import io.appium.java_client.AppiumDriver;

/**
 * Base class for all Page Objects.
 *
 * <p>Provides access to driver and shared components.</p>
 *
 * @author Ovidio Miranda
 */
public abstract class BasePage {

  /**
   * Gets the current AppiumDriver instance.
   *
   * @return active AppiumDriver
   */
  protected AppiumDriver getDriver() {
    return DriverManager.getDriver();
  }
}
