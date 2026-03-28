package com.ovidiomiranda.framework.core.providers;

import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.ExecutionType.LOCAL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_ACCESS_KEY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_URL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_USERNAME;

import com.ovidiomiranda.framework.core.capabilities.IosCapabilities;
import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import com.ovidiomiranda.framework.core.utils.ExecutionUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import java.net.URL;

/**
 * Creates iOS driver instances.
 *
 * <p>This implementation connects to the Appium server and starts a session using iOS capabilities.
 *
 * @author Ovidio Miranda
 */
public class IosDriverProvider implements DriverProvider {

  private final ConfigValidator config;
  private final IosCapabilities iosCapabilities;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param iosCapabilities iOS capabilities builder
   */
  public IosDriverProvider(final ConfigValidator config, final IosCapabilities iosCapabilities) {
    this.config = config;
    this.iosCapabilities = iosCapabilities;
  }

  /** {@inheritDoc} */
  @Override
  public AppiumDriver getDriver(final String sessionName) {
    try {
      final XCUITestOptions capabilities = iosCapabilities.getCapabilities(sessionName);
      final ExecutionType executionType = ExecutionUtils.getExecutionType(config);
      final String url;
      switch (executionType) {
        case BROWSERSTACK:
          final String baseUrl = config.require(BS_URL);
          url =
              baseUrl.replace(
                  "https://",
                  "https://"
                      + config.require(BS_USERNAME)
                      + ":"
                      + config.require(BS_ACCESS_KEY)
                      + "@");
          break;
        case LOCAL:
        default:
          url = config.require(PropertiesInput.APPIUM_SERVER_URL);
          break;
      }
      return new IOSDriver(new URL(url), capabilities);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create IOS driver", e);
    }
  }
}
