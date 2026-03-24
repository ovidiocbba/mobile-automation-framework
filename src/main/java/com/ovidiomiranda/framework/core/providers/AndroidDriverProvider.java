package com.ovidiomiranda.framework.core.providers;

import com.ovidiomiranda.framework.core.capabilities.AndroidCapabilities;
import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;

/**
 * Creates Android driver instances.
 *
 * <p>Connects to Appium server and starts a session with Android capabilities.
 *
 * @author Ovidio Miranda
 */
public class AndroidDriverProvider implements DriverProvider {

  private final ConfigValidator config;
  private final AndroidCapabilities androidCapabilities;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param androidCapabilities Android capabilities builder
   */
  public AndroidDriverProvider(ConfigValidator config, AndroidCapabilities androidCapabilities) {
    this.config = config;
    this.androidCapabilities = androidCapabilities;
  }

  /** {@inheritDoc} */
  @Override
  public AppiumDriver getDriver() {
    try {
      UiAutomator2Options capabilities = androidCapabilities.getCapabilities();
      String appiumServerUrl = config.require(PropertiesInput.APPIUM_SERVER_URL);
      return new AndroidDriver(new URL(appiumServerUrl), capabilities);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create Android driver", e);
    }
  }
}
