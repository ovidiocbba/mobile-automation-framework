package com.ovidiomiranda.framework.utils;

import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APP;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.APPIUM_SERVER_URL;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.AUTOMATION_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_BUILD_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.BROWSERSTACK_PROJECT_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.DEVICE_NAME;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.FRAMEWORK_THREADS;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM_VERSION;
import static com.ovidiomiranda.framework.core.utils.ExecutionUtils.getExecutionType;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseTitle;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class responsible for logging execution and scenario information.
 *
 * <p>This class centralizes all logging logic to keep hooks clean and maintainable.
 *
 * <p><b>Improvements introduced:</b>
 *
 * <ul>
 *   <li>Separates logs into clear sections: RUNNER, DEVICE and PROVIDER.
 *   <li>Avoids printing null or misleading values.
 *   <li>Automatically detects execution runner (Local, Jenkins, GitHub Actions).
 *   <li>Improves visual readability in CI logs (no broken lines).
 *   <li>Keeps the same public API to avoid changes in Hooks.
 * </ul>
 */
public final class ExecutionLogger {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionLogger.class);

  private static final String SEPARATOR =
      "============================================================";

  private ExecutionLogger() {
    throw new UnsupportedOperationException("Utility class");
  }

  /**
   * Logs execution configuration details in three clear sections.
   *
   * <ul>
   *   <li>RUNNER INFORMATION: Where the test runs
   *   <li>DEVICE CONFIGURATION: What is being automated
   *   <li>EXECUTION PROVIDER: Who executes the test
   * </ul>
   *
   * @param config configuration validator
   */
  public static void logExecutionConfig(final ConfigValidator config) {

    final ExecutionType executionType = getExecutionType(config);

    final String runner = detectRunner();

    final String platform = config.require(PLATFORM);
    final String device = config.require(DEVICE_NAME);
    final String version = config.require(PLATFORM_VERSION);
    final String automation = config.require(AUTOMATION_NAME);

    final String app = config.optional(APP);
    final String appiumServer = config.optional(APPIUM_SERVER_URL);
    final int threads = config.optionalInt(FRAMEWORK_THREADS, 1);
    final String tags = System.getProperty("cucumber.filter.tags");

    LOGGER.info(SEPARATOR);
    LOGGER.info("EXECUTION START");
    LOGGER.info(SEPARATOR);

    // ================= RUNNER =================
    LOGGER.info("RUNNER INFORMATION");
    LOGGER.info(SEPARATOR);
    LOGGER.info("Runner: {}", runner);
    LOGGER.info("OS: {}", System.getProperty("os.name"));
    LOGGER.info("Java: {}", System.getProperty("java.version"));
    LOGGER.info("Execution ID: {}", System.currentTimeMillis());
    LOGGER.info("Threads: {}", threads);

    if (tags != null) {
      LOGGER.info("Tags: {}", tags);
    }

    // ================= DEVICE =================
    LOGGER.info(SEPARATOR);
    LOGGER.info("DEVICE CONFIGURATION");
    LOGGER.info(SEPARATOR);
    LOGGER.info("Platform: {} {}", platform, version);
    LOGGER.info("Device: {}", device);
    LOGGER.info("Automation: {}", automation);

    if (app != null) {
      LOGGER.info("App: {}", app);
    }

    // ================= PROVIDER =================
    LOGGER.info(SEPARATOR);
    LOGGER.info("EXECUTION PROVIDER");
    LOGGER.info(SEPARATOR);
    LOGGER.info("Execution Type: {}", executionType);

    if (executionType == BROWSERSTACK) {
      LOGGER.info("Provider: BrowserStack");
      LOGGER.info("Project: {}", config.optional(BROWSERSTACK_PROJECT_NAME));
      LOGGER.info("Build: {}", config.optional(BROWSERSTACK_BUILD_NAME));
    } else {
      LOGGER.info("Provider: Local Appium");
      if (appiumServer != null) {
        LOGGER.info("Server: {}", appiumServer);
      }
    }

    LOGGER.info(SEPARATOR);
  }

  /**
   * Logs scenario start information including the session id.
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

  /**
   * Detects where the execution is running.
   *
   * @return runner name (Local, Jenkins, GitHub Actions)
   */
  private static String detectRunner() {

    if (System.getenv("JENKINS_URL") != null) {
      return "Jenkins";
    }

    if (System.getenv("GITHUB_ACTIONS") != null) {
      return "GitHub Actions";
    }

    return "Local";
  }
}
