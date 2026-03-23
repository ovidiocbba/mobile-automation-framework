package com.ovidiomiranda.framework.ui.components;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static java.util.Locale.ENGLISH;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.enums.PlatformType;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.core.locators.MobileLocator;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

/**
 * Base class for all UI elements (pages and components).
 *
 * <p>Provides common functionality like locator resolution and element interactions.</p>
 *
 * @author Ovidio Miranda
 */
public abstract class BaseComponent {

  /**
   * Configuration validator used to retrieve required properties.
   */
  protected final ConfigValidator config;
  /**
   * Provides access to the current Appium driver instance.
   */
  protected final DriverContext driverContext;
  /**
   * Utility class for performing mobile element interactions.
   */
  protected final MobileElementActions actions;

  /**
   * Constructor.
   *
   * @param config        configuration validator
   * @param driverContext driver context
   * @param actions       mobile actions utility
   */
  protected BaseComponent(ConfigValidator config, DriverContext driverContext,
      MobileElementActions actions) {
    this.config = config;
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

  /**
   * Resolves a MobileLocator into a platform-specific locator.
   *
   * @param locator mobile locator
   * @return resolved locator for current platform
   */
  protected By resolve(MobileLocator locator) {
    String platform = config.require(PLATFORM);
    PlatformType platformType = PlatformType.valueOf(platform.toUpperCase(ENGLISH));
    return locator.get(platformType);
  }
}
