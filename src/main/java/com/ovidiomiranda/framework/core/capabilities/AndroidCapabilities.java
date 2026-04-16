package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.isAppAbsent;
import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setAppCapability;
import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.ExecutionType.LOCAL;
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
  private final BrowserStackCapabilitiesBuilder browserStackBuilder;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param browserStackBuilder BrowserStack capabilities builder
   */
  public AndroidCapabilities(
      final ConfigValidator config, final BrowserStackCapabilitiesBuilder browserStackBuilder) {
    this.config = config;
    this.browserStackBuilder = browserStackBuilder;
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

    if (executionType == BROWSERSTACK) {
      browserStackBuilder.apply(options, sessionName);
    }

    setAppCapability(options, config, executionType);

    if (executionType == LOCAL) {
      if (isAppAbsent(options)) {
        options.setAppPackage(config.require(APP_PACKAGE));
        options.setAppActivity(config.require(APP_ACTIVITY));
      }

      // Waits for any activity to avoid launch timing issues
      options.setAppWaitActivity("*");
      options.setAppWaitDuration(Duration.ofSeconds(30));
      options.setAutoGrantPermissions(true);
    }

    return options;
  }
}
