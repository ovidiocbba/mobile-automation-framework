package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.APP_ACTIVITY;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.APP_PACKAGE;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.android.options.UiAutomator2Options;

/**
 * Builds Android capabilities used to start a mobile session.
 *
 * <p>The configuration values are loaded from {@code config.properties}.
 * The app can be launched using the APK path or using the installed app package and activity.</p>
 */
public final class AndroidCapabilities {

  /**
   * Private constructor to prevent instantiation.
   */
  private AndroidCapabilities() {
  }

  /**
   * Creates Android capabilities.
   *
   * <p>If the {@code app} property is provided, the framework installs
   * the application from the APK file. Otherwise, it launches the already installed app using
   * {@code appPackage} and {@code appActivity}.</p>
   *
   * @return configured UiAutomator2Options
   */
  public static UiAutomator2Options getCapabilities() {
    UiAutomator2Options options = new UiAutomator2Options();
    options.setPlatformName("Android");
    setCommonCapabilities(options);
    String app = ConfigValidator.optional(APP);
    if (app != null && !app.isBlank()) {
      options.setApp(app);
    } else {
      options.setCapability("appPackage", ConfigValidator.require(APP_PACKAGE));
      options.setCapability("appActivity", ConfigValidator.require(APP_ACTIVITY));
    }
    return options;
  }
}
