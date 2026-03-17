package com.ovidiomiranda.framework.core.providers;

import com.ovidiomiranda.framework.core.capabilities.AndroidCapabilities;
import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.config.PropertiesInput;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;

/**
 * Creates Android driver instances.
 *
 * <p>This implementation connects to the Appium server and starts a session using Android
 * capabilities.</p>
 */
public class AndroidDriverProvider implements DriverProvider {

  /**
   * {@inheritDoc}
   */
  @Override
  public AppiumDriver getDriver() {
    try {
      UiAutomator2Options capabilities = AndroidCapabilities.getCapabilities();
      String appiumServerUrl = ConfigValidator.require(PropertiesInput.APPIUM_SERVER_URL);
      return new AndroidDriver(new URL(appiumServerUrl), capabilities);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create Android driver", e);
    }
  }
}
