package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.AUTOMATION_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM_VERSION;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.UDID;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
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

    final String udid = config.optional(UDID);
    if (udid != null && !udid.isBlank()) {
      options.setCapability("udid", udid);
    }

    options.setCapability("noReset", false);
    options.setCapability("newCommandTimeout", NEW_COMMAND_TIMEOUT.getSeconds());
  }

  /**
   * Resolves and sets the application capability based on execution type.
   *
   * <p>Resolution priority:
   *
   * <ul>
   *   <li>For BrowserStack execution: uses {@code browserstack.app} (bs:// reference)
   *   <li>For local execution: uses {@code app}
   * </ul>
   *
   * <p>Make sure to use the correct app for the selected platform (Android or iOS).
   *
   * @param options Appium options to enrich
   * @param config configuration validator
   * @param executionType execution environment (LOCAL or BROWSERSTACK)
   */
  protected static void setAppCapability(
      final BaseOptions<?> options,
      final ConfigValidator config,
      final ExecutionType executionType) {

    String app = null;

    if (executionType == BROWSERSTACK) {
      app = config.optional(BROWSERSTACK_APP);
    } else {
      app = config.optional(APP);
    }

    if (app != null && !app.isBlank()) {
      options.setCapability("app", app);
    }
  }

  /**
   * Checks if the "app" capability is missing or blank.
   *
   * @param options Appium options
   * @return true if app is null or empty, false otherwise
   */
  protected static boolean isAppAbsent(final BaseOptions<?> options) {
    final Object app = options.getCapability("app");
    return app == null || app.toString().isBlank();
  }
}
