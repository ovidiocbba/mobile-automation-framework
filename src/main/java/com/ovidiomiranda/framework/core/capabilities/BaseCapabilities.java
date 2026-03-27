package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.AUTOMATION_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM_VERSION;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.remote.options.BaseOptions;
import java.time.Duration;

/**
 * Base capabilities for mobile platforms.
 *
 * <p>This class centralizes common configuration used by Android and iOS sessions.
 *
 * @author Ovidio Miranda
 */
public abstract class BaseCapabilities {

  /** Maximum time Appium waits for a new command before closing the session. */
  protected static final Duration NEW_COMMAND_TIMEOUT = Duration.ofSeconds(300);

  /**
   * Applies common capabilities to the given options object.
   *
   * @param options platform options
   * @param config configuration validator
   */
  protected static void setCommonCapabilities(
      final BaseOptions<?> options, final ConfigValidator config) {
    options.setCapability("deviceName", config.require(DEVICE_NAME));
    options.setCapability("platformVersion", config.require(PLATFORM_VERSION));
    options.setCapability("automationName", config.require(AUTOMATION_NAME));
    options.setCapability("noReset", false);
    options.setCapability("newCommandTimeout", NEW_COMMAND_TIMEOUT.getSeconds());
  }
}
