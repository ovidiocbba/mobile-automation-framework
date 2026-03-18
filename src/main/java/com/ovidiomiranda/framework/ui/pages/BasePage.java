package com.ovidiomiranda.framework.ui.pages;


import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import io.appium.java_client.AppiumDriver;

/**
 * Base class for all Page Objects.
 *
 * <p>Provides access to driver and shared interactions.</p>
 *
 * @author Ovidio Miranda
 */
public abstract class BasePage {

  protected final DriverContext driverContext;
  protected final MobileElementActions actions;

  /**
   * Constructor.
   *
   * @param driverContext driver context for current Appium session
   * @param actions       MobileElementActions utility
   */
  protected BasePage(DriverContext driverContext, MobileElementActions actions) {
    this.driverContext = driverContext;
    this.actions = actions;
  }

  /**
   * Gets the current AppiumDriver instance.
   *
   * @return active AppiumDriver
   */
  protected AppiumDriver getDriver() {
    return driverContext.getDriver();
  }
}
