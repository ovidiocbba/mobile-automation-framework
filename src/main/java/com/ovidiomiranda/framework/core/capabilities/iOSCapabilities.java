package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.config.PropertiesInput.BUNDLE_ID;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.ios.options.XCUITestOptions;

/**
 * Builds iOS capabilities used to start a mobile session.
 *
 * <p>The app can be launched using the app file path or the installed app bundle identifier.</p>
 *
 * @author Ovidio Miranda
 */
public final class iOSCapabilities {

  /**
   * Private constructor to prevent instantiation.
   */
  private iOSCapabilities() {
  }

  /**
   * Creates iOS capabilities.
   *
   * <p>If the {@code app} property is provided,
   * the framework installs the application. Otherwise, the app is launched using the bundle
   * id.</p>
   *
   * @return configured XCUITestOptions
   */
  public static XCUITestOptions getCapabilities() {
    XCUITestOptions options = new XCUITestOptions();
    options.setPlatformName("iOS");
    options.setAutoAcceptAlerts(true);
    setCommonCapabilities(options);
    String app = ConfigValidator.optional(APP);
    if (app != null && !app.isBlank()) {
      options.setApp(app);
    } else {
      options.setBundleId(ConfigValidator.require(BUNDLE_ID));
    }
    return options;
  }
}
