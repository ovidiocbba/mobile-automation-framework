package com.ovidiomiranda.framework.core.driver;

import com.ovidiomiranda.framework.core.providers.PlatformType;
import io.appium.java_client.AppiumDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages AppiumDriver instances using ThreadLocal.
 *
 * <p>This allows parallel execution where each thread has its own driver instance.</p>
 *
 * @author Ovidio Miranda
 */
public final class DriverManager {

  private static final Logger LOGGER = LoggerFactory.getLogger(DriverManager.class);

  /**
   * ThreadLocal instance of AppiumDriver to support parallel execution.
   */
  private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();

  /**
   * Private constructor to prevent instantiation.
   */
  private DriverManager() {
  }

  /**
   * Initializes the driver for the current thread.
   *
   * @param platformType mobile platform
   */
  public static void initDriver(final PlatformType platformType) {
    if (DRIVER.get() == null) {
      LOGGER.info("Initializing AppiumDriver for platform: {}", platformType);
      AppiumDriver driver = DriverFactory.createDriver(platformType);
      DRIVER.set(driver);
    } else {
      LOGGER.warn("Driver is already initialized for this thread.");
    }
  }

  /**
   * Returns the driver for the current thread.
   *
   * @return AppiumDriver instance
   */
  public static AppiumDriver getDriver() {
    AppiumDriver driver = DRIVER.get();
    if (driver == null) {
      throw new IllegalStateException("Driver has not been initialized. Call initDriver() first.");
    }
    return driver;
  }

  /**
   * Checks if the driver is already initialized.
   *
   * @return true if driver exists
   */
  public static boolean isDriverInitialized() {
    return DRIVER.get() != null;
  }

  /**
   * Closes the driver and removes it from ThreadLocal storage.
   */
  public static void quitDriver() {
    try {
      AppiumDriver driver = DRIVER.get();
      if (driver != null) {
        LOGGER.info("Closing AppiumDriver session.");
        driver.quit();
      }
    } finally {
      DRIVER.remove();
    }
  }
}
