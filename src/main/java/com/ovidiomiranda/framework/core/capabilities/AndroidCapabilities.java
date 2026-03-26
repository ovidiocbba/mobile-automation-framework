package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP_ACTIVITY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP_PACKAGE;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.utils.ExecutionUtils;
import io.appium.java_client.android.options.UiAutomator2Options;

/**
 * Builds Android capabilities for Appium sessions.
 *
 * <p>The configuration values are loaded from {@code config.properties}. The app can be launched
 * using the APK path or using the installed app package and activity.
 *
 * @author Ovidio Miranda
 */
public class AndroidCapabilities {

  private final ConfigValidator config;
  private final BrowserStackCapabilitiesBuilder bsBuilder;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param bsBuilder BrowserStack capabilities builder
   */
  public AndroidCapabilities(ConfigValidator config, BrowserStackCapabilitiesBuilder bsBuilder) {

    this.config = config;
    this.bsBuilder = bsBuilder;
  }

  /**
   * Creates Android capabilities.
   *
   * <p>Supports two execution modes:
   *
   * <ul>
   *   <li>Local execution (APK or appPackage + appActivity)
   *   <li>BrowserStack execution (cloud app using bs://)
   * </ul>
   *
   * @return configured UiAutomator2Options
   */
  public UiAutomator2Options getCapabilities() {
    UiAutomator2Options options = new UiAutomator2Options();
    options.setPlatformName("Android");
    setCommonCapabilities(options, config);
    ExecutionType executionType = ExecutionUtils.getExecutionType(config);
    switch (executionType) {
      case BROWSERSTACK:
        bsBuilder.apply(options, "Android Test");
        break;

      case LOCAL:
      default:
        String app = config.optional(APP);
        if (app != null && !app.isBlank()) {
          options.setApp(app);
        } else {
          options.setCapability("appPackage", config.require(APP_PACKAGE));
          options.setCapability("appActivity", config.require(APP_ACTIVITY));
        }
        break;
    }
    return options;
  }
}
