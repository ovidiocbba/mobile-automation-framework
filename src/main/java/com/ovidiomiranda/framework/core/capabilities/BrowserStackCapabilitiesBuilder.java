package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_ACCESS_KEY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_APPIUM_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_APPIUM_VERSION;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_BUILD_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_DEVICE_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_NETWORK_LOGS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_PROJECT_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_USERNAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_VIDEO;
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

    // Set app uploaded to BrowserStack
    options.setCapability("app", config.require(BS_APP));

    // Build BrowserStack options map
    final Map<String, Object> bstackOptions = new HashMap<>();
    bstackOptions.put("userName", config.require(BS_USERNAME));
    bstackOptions.put("accessKey", config.require(BS_ACCESS_KEY));
    bstackOptions.put("projectName", config.require(BS_PROJECT_NAME));
    bstackOptions.put("buildName", config.require(BS_BUILD_NAME));
    bstackOptions.put("sessionName", sessionName);

    bstackOptions.put("deviceName", config.require(DEVICE_NAME));
    bstackOptions.put("osVersion", config.require(PLATFORM_VERSION));

    bstackOptions.put("video", Boolean.parseBoolean(config.require(BS_VIDEO)));
    bstackOptions.put("deviceLogs", Boolean.parseBoolean(config.require(BS_DEVICE_LOGS)));
    bstackOptions.put("appiumLogs", Boolean.parseBoolean(config.require(BS_APPIUM_LOGS)));
    bstackOptions.put("networkLogs", Boolean.parseBoolean(config.require(BS_NETWORK_LOGS)));
    bstackOptions.put("appiumVersion", config.require(BS_APPIUM_VERSION));
    // Attach to capabilities
    options.setCapability("bstack:options", bstackOptions);
  }
}
