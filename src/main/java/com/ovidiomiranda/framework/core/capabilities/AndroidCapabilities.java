package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP_ACTIVITY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP_PACKAGE;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.utils.ExecutionUtils;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.time.Duration;

/**
 * Builds Android capabilities for Appium sessions.
 *
 * <p>Configuration is loaded using layered files: base, android, local/browserstack, and system
 * properties (-D).
 *
 * <p>Supports local execution (APK or appPackage/appActivity) and BrowserStack execution.
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
  public AndroidCapabilities(
      final ConfigValidator config, final BrowserStackCapabilitiesBuilder bsBuilder) {
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
   * @param sessionName session name used to identify the test execution in BrowserStack
   * @return configured UiAutomator2Options
   */
  public UiAutomator2Options getCapabilities(final String sessionName) {
    final UiAutomator2Options options = new UiAutomator2Options();
    options.setPlatformName("Android");
    setCommonCapabilities(options, config);
    final ExecutionType executionType = ExecutionUtils.getExecutionType(config);
    switch (executionType) {
      case BROWSERSTACK:
        bsBuilder.apply(options, sessionName);
        break;

      case LOCAL:
      default:
        final String app = config.optional(APP);
        if (app != null && !app.isBlank()) {
          options.setApp(app);
        } else {
          options.setAppPackage(config.require(APP_PACKAGE));
          options.setAppActivity(config.require(APP_ACTIVITY));
        }
        options.setAppWaitActivity("*");
        options.setAppWaitDuration(Duration.ofSeconds(30));
        options.setAutoGrantPermissions(true);
        break;
    }
    return options;
  }
}
