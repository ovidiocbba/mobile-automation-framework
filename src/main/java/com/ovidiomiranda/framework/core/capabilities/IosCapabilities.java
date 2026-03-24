package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.capabilities.BaseCapabilities.setCommonCapabilities;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BUNDLE_ID;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import io.appium.java_client.ios.options.XCUITestOptions;

/**
 * Builds iOS capabilities for Appium sessions.
 *
 * <p>The app can be launched using the app file path or the installed app bundle identifier.
 *
 * @author Ovidio Miranda
 */
public class IosCapabilities {

  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param config configuration validator
   */
  public IosCapabilities(ConfigValidator config) {
    this.config = config;
  }

  /**
   * Creates iOS capabilities.
   *
   * <p>If the {@code app} property is provided, the framework installs the application. Otherwise,
   * the app is launched using the bundle id.
   *
   * @return configured XCUITestOptions
   */
  public XCUITestOptions getCapabilities() {
    XCUITestOptions options = new XCUITestOptions();
    options.setPlatformName("iOS");
    options.setAutoAcceptAlerts(true);
    setCommonCapabilities(options, config);
    String app = config.optional(APP);
    if (app != null && !app.isBlank()) {
      options.setApp(app);
    } else {
      options.setBundleId(config.require(BUNDLE_ID));
    }
    return options;
  }
}
