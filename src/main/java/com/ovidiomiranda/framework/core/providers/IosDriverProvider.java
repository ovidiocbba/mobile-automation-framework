package com.ovidiomiranda.framework.core.providers;

import com.ovidiomiranda.framework.core.capabilities.iOSCapabilities;
import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.URL;

/**
 * Creates iOS driver instances.
 *
 * <p>This implementation connects to the Appium server and starts a session using iOS
 * capabilities.</p>
 *
 * @author Ovidio Miranda
 */
public class iOSDriverProvider implements DriverProvider {

  private final ConfigValidator config;
  private final iOSCapabilities iosCapabilities;

  /**
   * Constructor.
   *
   * @param config          configuration validator
   * @param iosCapabilities iOS capabilities builder
   */
  public iOSDriverProvider(ConfigValidator config, iOSCapabilities iosCapabilities) {
    this.config = config;
    this.iosCapabilities = iosCapabilities;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AppiumDriver getDriver() {
    try {
      XCUITestOptions capabilities = iosCapabilities.getCapabilities();
      String appiumServerUrl = config.require(PropertiesInput.APPIUM_SERVER_URL);
      return new IOSDriver(new URL(appiumServerUrl), capabilities);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create IOS driver", e);
    }
  }
}
