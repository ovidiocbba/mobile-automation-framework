package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.isAppAbsent;
import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setAppCapability;
import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.ExecutionType.LOCAL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BUNDLE_ID;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.utils.ExecutionUtils;
import io.appium.java_client.ios.options.XCUITestOptions;

/**
 * Builds iOS capabilities for Appium sessions.
 *
 * <p>This class supports both execution modes:
 *
 * <ul>
 *   <li>Local execution (using .app file or bundleId)
 *   <li>BrowserStack execution (using uploaded app via bs:// reference)
 * </ul>
 *
 * <p>The app can be launched using the app file path or the installed app bundle identifier.
 *
 * @author Ovidio Miranda
 */
public class IosCapabilities {

  private final ConfigValidator config;
  private final BrowserStackCapabilitiesBuilder browserStackBuilder;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param browserStackBuilder BrowserStack capabilities builder
   */
  public IosCapabilities(
      final ConfigValidator config, final BrowserStackCapabilitiesBuilder browserStackBuilder) {
    this.config = config;
    this.browserStackBuilder = browserStackBuilder;
  }

  /**
   * Creates iOS capabilities.
   *
   * <p>Supports two execution modes:
   *
   * <ul>
   *   <li>Local execution:
   *       <ul>
   *         <li>Uses {@code app} if provided
   *         <li>Otherwise uses {@code bundleId}
   *       </ul>
   *   <li>BrowserStack execution:
   *       <ul>
   *         <li>Applies BrowserStack configuration (credentials, device, app, etc.)
   *         <li>Uses {@code browserstack.app} (bs:// reference)
   *       </ul>
   * </ul>
   *
   * @param sessionName session name used to identify the test execution
   * @return configured XCUITestOptions
   */
  public XCUITestOptions getCapabilities(final String sessionName) {

    final XCUITestOptions options = new XCUITestOptions();

    options.setPlatformName("iOS");
    options.setAutoAcceptAlerts(true);
    options.setCapability("autoDismissAlerts", true);
    options.setCapability("shouldTerminateApp", true);
    options.setCapability("connectHardwareKeyboard", false);
    options.setCapability("usePrebuiltWDA", true);
    options.setCapability("appium:maxTypingFrequency", 15);
    options.setCapability("appium:sendKeyStrategy", "oneByOne");

    setCommonCapabilities(options, config);

    final ExecutionType executionType = ExecutionUtils.getExecutionType(config);

    if (executionType == BROWSERSTACK) {
      browserStackBuilder.apply(options, sessionName);
    }

    setAppCapability(options, config, executionType);

    if (executionType == LOCAL) {
      if (isAppAbsent(options)) {
        options.setBundleId(config.require(BUNDLE_ID));
      }
    }

    return options;
  }
}
