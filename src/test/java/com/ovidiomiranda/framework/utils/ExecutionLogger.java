package com.ovidiomiranda.framework.utils;

import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APPIUM_SERVER_URL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.AUTOMATION_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_BUILD_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_PROJECT_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.FRAMEWORK_EXECUTION;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.FRAMEWORK_THREADS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM_VERSION;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseTitle;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class responsible for logging execution and scenario information.
 *
 * <p>This class centralizes all logging logic to keep hooks clean and maintainable.
 */
public final class ExecutionLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionLogger.class);

  private static final String SEPARATOR =
      "============================================================";

  private ExecutionLogger() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Logs execution configuration details.
   *
   * @param config configuration validator
   */
  public static void logExecutionConfig(final ConfigValidator config) {

    final String platform = config.require(PLATFORM);
    final String device = config.require(DEVICE_NAME);
    final String version = config.require(PLATFORM_VERSION);
    final String automation = config.require(AUTOMATION_NAME);
    final ExecutionType executionType = ExecutionType.from(config.optional(FRAMEWORK_EXECUTION));

    LOGGER.info(SEPARATOR);
    LOGGER.info("Execution ID: {}", System.currentTimeMillis());
    LOGGER.info("Execution Type: {}", executionType);
    LOGGER.info("Platform: {} | Version: {}", platform, version);
    LOGGER.info("Device: {}", device);
    LOGGER.info("Automation: {}", automation);

    LOGGER.info("App: {}", config.optional(PropertiesInput.APP));
    LOGGER.info("Appium Server: {}", config.optional(APPIUM_SERVER_URL));
    LOGGER.info("Threads: {}", config.optionalInt(FRAMEWORK_THREADS, 1));

    LOGGER.info("OS: {}", System.getProperty("os.name"));
    LOGGER.info("Java: {}", System.getProperty("java.version"));
    LOGGER.info("Environment: {}", System.getProperty("env", "local"));
    LOGGER.info("Cucumber Tags: {}", System.getProperty("cucumber.filter.tags"));

    if (executionType == BROWSERSTACK) {
      LOGGER.info("BS Project: {}", config.optional(BROWSERSTACK_PROJECT_NAME));
      LOGGER.info("BS Build: {}", config.optional(BROWSERSTACK_BUILD_NAME));
    }

    LOGGER.info(SEPARATOR);
  }

  /**
   * Logs scenario start information.
   *
   * @param scenario current scenario
   * @param driver active driver
   */
  public static void logScenarioStart(final Scenario scenario, final AppiumDriver driver) {

    LOGGER.info(SEPARATOR);
    LOGGER.info(">>> STARTING SCENARIO | {}", getTestCaseTitle(scenario));

    if (driver != null) {
      LOGGER.info("Session ID: {}", driver.getSessionId());
    }

    LOGGER.info(SEPARATOR);
  }
}
