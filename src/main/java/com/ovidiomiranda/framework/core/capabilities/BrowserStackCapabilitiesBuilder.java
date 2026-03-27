package com.ovidiomiranda.framework.core.capabilities;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_ACCESS_KEY;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BS_USERNAME;

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

  private static final String PROJECT_NAME = "Mobile Automation Framework";
  private static final String BUILD_NAME = "Build-1";
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
   * @param options Appium options to enrich
   * @param sessionName name of the test session
   */
  public void apply(final BaseOptions<?> options, final String sessionName) {

    // Set app uploaded to BrowserStack
    options.setCapability("appium:app", config.require(BS_APP));

    // Build BrowserStack options map
    final Map<String, Object> bstackOptions = new HashMap<>();
    bstackOptions.put("userName", config.require(BS_USERNAME));
    bstackOptions.put("accessKey", config.require(BS_ACCESS_KEY));
    bstackOptions.put("projectName", PROJECT_NAME);
    bstackOptions.put("buildName", BUILD_NAME);
    bstackOptions.put("sessionName", sessionName);

    // Attach to capabilities
    options.setCapability("bstack:options", bstackOptions);
  }
}
