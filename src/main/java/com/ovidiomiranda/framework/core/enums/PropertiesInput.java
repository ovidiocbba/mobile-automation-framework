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

  // ========================================
  // BASE CONFIG
  // ========================================

  /** Username for login. */
  APP_USERNAME("app.username"),

  /** Password for login. */
  APP_PASSWORD("app.password"),

  /** Explicit wait timeout in seconds. */
  FRAMEWORK_EXPLICIT_WAIT("framework.explicitWait"),

  /** Number of threads for parallel execution. */
  FRAMEWORK_THREADS("framework.threads"),

  // ========================================
  // PLATFORM CONFIG
  // ========================================

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

  // ========================================
  // EXECUTION CONFIG
  // ========================================

  /** Execution type (local, browserstack). */
  EXECUTION("execution"),

  /** Appium server URL. */
  APPIUM_SERVER_URL("appiumServerUrl"),

  // ========================================
  // BROWSERSTACK CONFIG
  // ========================================

  /** BrowserStack username. */
  BROWSERSTACK_USERNAME("browserstack.username"),

  /** BrowserStack access key. */
  BROWSERSTACK_ACCESS_KEY("browserstack.accessKey"),

  /** BrowserStack hub URL. */
  BROWSERSTACK_URL("browserstack.url"),

  /** BrowserStack app id. */
  BROWSERSTACK_APP("browserstack.app"),

  /** BrowserStack project name used for grouping test runs. */
  BROWSERSTACK_PROJECT_NAME("browserstack.projectName"),

  /** BrowserStack build name associated with the execution. */
  BROWSERSTACK_BUILD_NAME("browserstack.buildName"),

  /** Enables video recording for BrowserStack sessions. */
  BROWSERSTACK_VIDEO("browserstack.video"),

  /** Enables device logs collection on BrowserStack. */
  BROWSERSTACK_DEVICE_LOGS("browserstack.deviceLogs"),

  /** Enables Appium logs collection on BrowserStack. */
  BROWSERSTACK_APPIUM_LOGS("browserstack.appiumLogs"),

  /** Enables network logs collection on BrowserStack. */
  BROWSERSTACK_NETWORK_LOGS("browserstack.networkLogs"),

  /** Specifies the Appium version used in BrowserStack. */
  BROWSERSTACK_APPIUM_VERSION("browserstack.appiumVersion");

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
