package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_ACCESS_KEY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_APPIUM_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_APPIUM_VERSION;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_BUILD_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_DEVICE_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_NETWORK_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_PROJECT_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_USERNAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_VIDEO;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM_VERSION;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.remote.options.BaseOptions;
import java.util.HashMap;
import java.util.Map;

/**
 * Builds BrowserStack-specific capabilities.
 *
 * @author Ovidio Miranda
 */
public class BrowserStackCapabilitiesBuilder {

  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param config configuration validator
   */
  public BrowserStackCapabilitiesBuilder(final ConfigValidator config) {
    this.config = config;
  }

  /**
   * Applies BrowserStack capabilities to the given options object.
   *
   * <p>This method injects all required BrowserStack configuration, including authentication,
   * application reference, and session metadata.
   *
   * @param sessionName session name used to identify the test execution in BrowserStack
   * @param options Appium options to enrich
   */
  public void apply(final BaseOptions<?> options, final String sessionName) {

    // Build BrowserStack options map
    final Map<String, Object> browserstackOptions = new HashMap<>();

    // Authentication
    browserstackOptions.put("userName", config.require(BROWSERSTACK_USERNAME));
    browserstackOptions.put("accessKey", config.require(BROWSERSTACK_ACCESS_KEY));

    // Project metadata (Dashboard)
    browserstackOptions.put("projectName", config.require(BROWSERSTACK_PROJECT_NAME));
    browserstackOptions.put("buildName", config.require(BROWSERSTACK_BUILD_NAME));
    browserstackOptions.put("sessionName", sessionName);

    // Device
    browserstackOptions.put("deviceName", config.require(DEVICE_NAME));
    browserstackOptions.put("osVersion", config.require(PLATFORM_VERSION));

    // Appium version used in BrowserStack
    browserstackOptions.put("appiumVersion", config.require(BROWSERSTACK_APPIUM_VERSION));

    // Logs & debugging
    browserstackOptions.put("video", config.requireBoolean(BROWSERSTACK_VIDEO));
    browserstackOptions.put("deviceLogs", config.requireBoolean(BROWSERSTACK_DEVICE_LOGS));
    browserstackOptions.put("appiumLogs", config.requireBoolean(BROWSERSTACK_APPIUM_LOGS));
    browserstackOptions.put("networkLogs", config.requireBoolean(BROWSERSTACK_NETWORK_LOGS));

    // Attach to capabilities
    options.setCapability("bstack:options", browserstackOptions);
  }
}
