package com.ovidiomiranda.framework.core.enums;

/**
 * Enum containing all configuration keys supported by the framework.
 *
 * <p>Each value maps to a property name in {@code config.properties}.
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
  THREAD_COUNT("threads");

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
