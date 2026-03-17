package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.config.PropertiesInput.AUTOMATION_NAME;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.PLATFORM_VERSION;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.remote.options.BaseOptions;
import java.time.Duration;

/**
 * Defines capabilities shared by all mobile platforms.
 *
 * <p>This class centralizes common configuration used by Android and iOS sessions.</p>
 */
public abstract class BaseCapabilities {

  /**
   * Maximum time Appium waits for a new command before closing the session.
   */
  protected static final Duration NEW_COMMAND_TIMEOUT = Duration.ofSeconds(300);

  /**
   * Applies common capabilities to the given options object.
   *
   * @param options platform-specific options
   */
  protected static void setCommonCapabilities(BaseOptions<?> options) {
    options.setCapability("deviceName", ConfigValidator.require(DEVICE_NAME));
    options.setCapability("platformVersion", ConfigValidator.require(PLATFORM_VERSION));
    options.setCapability("automationName", ConfigValidator.require(AUTOMATION_NAME));
    options.setCapability("noReset", true);
    options.setCapability("newCommandTimeout", NEW_COMMAND_TIMEOUT.getSeconds());
  }
}
