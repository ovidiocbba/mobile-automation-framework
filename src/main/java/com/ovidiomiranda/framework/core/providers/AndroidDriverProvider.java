package com.ovidiomiranda.framework.core.providers;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_ACCESS_KEY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_URL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_USERNAME;

import com.ovidiomiranda.framework.core.capabilities.AndroidCapabilities;
import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import com.ovidiomiranda.framework.core.utils.ExecutionUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;

/**
 * Creates Android driver instances.
 *
 * <p>Supports:
 *
 * <ul>
 *   <li>Local Appium execution
 *   <li>BrowserStack cloud execution
 * </ul>
 *
 * @author Ovidio Miranda
 */
public class AndroidDriverProvider implements DriverProvider {

  private final ConfigValidator config;
  private final AndroidCapabilities androidCapabilities;

  /**
   * Constructor.
   *
   * @param config configuration validator
   * @param androidCapabilities Android capabilities builder
   */
  public AndroidDriverProvider(ConfigValidator config, AndroidCapabilities androidCapabilities) {
    this.config = config;
    this.androidCapabilities = androidCapabilities;
  }

  /** {@inheritDoc} */
  @Override
  public AppiumDriver getDriver() {
    try {
      UiAutomator2Options capabilities = androidCapabilities.getCapabilities();
      ExecutionType executionType = ExecutionUtils.getExecutionType(config);
      String url;
      switch (executionType) {
        case BROWSERSTACK:
          String baseUrl = config.require(BS_URL);
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
      return new AndroidDriver(new URL(url), capabilities);
    } catch (Exception e) {
      throw new RuntimeException("Failed to create Android driver", e);
    }
  }
}
