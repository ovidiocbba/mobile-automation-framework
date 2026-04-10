package com.ovidiomiranda.framework.core.enums;

/**
 * Enum with all configuration keys used in the framework.
 *
 * <p>Keys are loaded from layered configuration: base, platform, execution, and system properties
 * (-D).
 *
 * <p>Used to access properties in a consistent way.
 *
 * @author Ovidio Miranda
 */
public enum PropertiesInput {

  /** Username for login. */
  USERNAME("username"),

  /** Password for login. */
  PASSWORD("password"),

  /** Mobile platform where tests will run (ANDROID or IOS). */
  PLATFORM("platform"),

  /** Device name used for execution. */
  DEVICE_NAME("deviceName"),

  /** Mobile platform version. */
  PLATFORM_VERSION("platformVersion"),

  /** Unique Device Identifier for mobile execution. */
  UDID("udid"),

  /** Automation engine used by Appium. */
  AUTOMATION_NAME("automationName"),

  /** Path to the mobile application file. */
  APP("app"),

  /** Android app package name. */
  APP_PACKAGE("appPackage"),

  /** Android main activity used to launch the app. */
  APP_ACTIVITY("appActivity"),

  /** iOS application bundle identifier. */
  BUNDLE_ID("bundleId"),

  /** Appium server URL. */
  APPIUM_SERVER_URL("appiumServerUrl"),

  /** Explicit wait timeout in seconds. */
  EXPLICIT_WAIT("explicitWait"),

  /** Number of threads for parallel execution. */
  THREAD_COUNT("threads"),

  /** BrowserStack username. */
  BS_USERNAME("bs.username"),

  /** BrowserStack access key. */
  BS_ACCESS_KEY("bs.accessKey"),

  /** BrowserStack app id. */
  BS_APP("bs.app"),

  /** BrowserStack hub URL. */
  BS_URL("bs.url"),

  /** Execution type (local, browserstack). */
  EXECUTION("execution"),

  /** BrowserStack project name used for grouping test runs. */
  BS_PROJECT_NAME("bs.projectName"),

  /** BrowserStack build name associated with the execution. */
  BS_BUILD_NAME("bs.buildName"),

  /** Enables video recording for BrowserStack sessions. */
  BS_VIDEO("bs.video"),

  /** Enables device logs collection on BrowserStack. */
  BS_DEVICE_LOGS("bs.deviceLogs"),

  /** Enables Appium logs collection on BrowserStack. */
  BS_APPIUM_LOGS("bs.appiumLogs"),

  /** Enables network logs collection on BrowserStack. */
  BS_NETWORK_LOGS("bs.networkLogs"),

  /** Specifies the Appium version used in BrowserStack. */
  BS_APPIUM_VERSION("bs.appiumVersion");

  private final String propertiesName;

  PropertiesInput(final String propertiesName) {
    this.propertiesName = propertiesName;
  }

  /**
   * Returns the property name used in the configuration file.
   *
   * @return property name
   */
  public String getPropertiesName() {
    return propertiesName;
  }
}
