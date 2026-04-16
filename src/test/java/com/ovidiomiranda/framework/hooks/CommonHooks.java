package com.ovidiomiranda.framework.hooks;

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
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseId;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseTitle;
import static java.util.Locale.ENGLISH;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.driver.DriverFactory;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.enums.PlatformType;
import com.ovidiomiranda.framework.utils.BrowserStackUtils;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class containing Common Hooks.
 *
 * @author Ovidio Miranda
 */
public class CommonHooks {

  private static final Logger LOGGER = LoggerFactory.getLogger(CommonHooks.class);
  private long startTime;
  private static final String SEPARATOR =
      "============================================================";
  private static final AtomicBoolean executionInfoPrinted = new AtomicBoolean(false);

  private final DriverContext driverContext;
  private final DriverFactory driverFactory;
  private final ConfigValidator config;

  /**
   * Constructor.
   *
   * @param driverContext driver context
   * @param driverFactory driver factory
   * @param config config validator
   */
  public CommonHooks(
      final DriverContext driverContext,
      final DriverFactory driverFactory,
      final ConfigValidator config) {
    this.driverContext = driverContext;
    this.driverFactory = driverFactory;
    this.config = config;
  }

  /**
   * Initializes execution timing and logs scenario start.
   *
   * @param scenario the scenario to be executed
   */
  @Before(order = 1)
  public void beforeScenario(final Scenario scenario) {
    startTime = System.currentTimeMillis();
    LOGGER.info(SEPARATOR);
    LOGGER.info(">>> STARTING SCENARIO | {}", getTestCaseTitle(scenario));
    LOGGER.info(SEPARATOR);
  }

  /**
   * Initializes the driver based on configured platform.
   *
   * <p>Reads platform from configuration and starts Appium driver.
   *
   * @param scenario executed scenario
   */
  @Before(order = 0)
  public void setUp(final Scenario scenario) {

    final String platform = config.require(PLATFORM);
    final String device = config.require(DEVICE_NAME);
    final String platformVersion = config.require(PLATFORM_VERSION);
    final ExecutionType executionType = getExecutionType(config);
    final String automationName = config.require(AUTOMATION_NAME);

    if (executionInfoPrinted.compareAndSet(false, true)) {

      LOGGER.info(SEPARATOR);
      LOGGER.info("Execution Type: {}", executionType);
      LOGGER.info("Platform: {} | Version: {}", platform, platformVersion);
      LOGGER.info("Device: {}", device);
      LOGGER.info("Automation: {}", automationName);

      LOGGER.info("App: {}", config.optional(APP));
      LOGGER.info("Appium Server: {}", config.optional(APPIUM_SERVER_URL));
      LOGGER.info("Threads: {}", config.optionalInt(FRAMEWORK_THREADS, 1));

      LOGGER.info("OS: {}", System.getProperty("os.name"));
      LOGGER.info("Java: {}", System.getProperty("java.version"));

      LOGGER.info("Cucumber Tags: {}", System.getProperty("cucumber.filter.tags"));

      if (executionType == BROWSERSTACK) {
        LOGGER.info("BS Project: {}", config.optional(BROWSERSTACK_PROJECT_NAME));
        LOGGER.info("BS Build: {}", config.optional(BROWSERSTACK_BUILD_NAME));
      }

      LOGGER.info(SEPARATOR);
    }

    final AppiumDriver driver =
        driverFactory.createDriver(
            PlatformType.valueOf(platform.toUpperCase(ENGLISH)), getTestCaseTitle(scenario));

    driverContext.setDriver(driver);
  }

  /** Attaches the BrowserStack session link to the Allure report. */
  @After(order = 1)
  public void attachBrowserStackSessionLink() {
    final ExecutionType executionType = getExecutionType(config);

    if (executionType == BROWSERSTACK) {
      BrowserStackUtils.attachSession(driverContext.getDriver());
    }
  }

  /**
   * Logs result, attaches screenshot on failure, and closes driver.
   *
   * @param scenario executed scenario
   */
  @After(order = 0)
  public void afterScenario(final Scenario scenario) {
    final String testCaseId = getTestCaseId(scenario);

    final long durationMs = System.currentTimeMillis() - startTime;
    final double durationSec = durationMs / 1000.0;

    final String formattedDuration = String.format("%.2f", durationSec);

    try {
      if (scenario.isFailed()) {
        LOGGER.error("RESULT: FAILED | Duration: {} s", formattedDuration);
        attachScreenshot(testCaseId);
      } else {
        LOGGER.info("RESULT: PASSED | Duration: {} s", formattedDuration);
      }
    } finally {
      LOGGER.info(SEPARATOR);
      driverContext.quitDriver();
    }
  }

  /**
   * Takes screenshot and adds it to Allure report.
   *
   * @param name screenshot name
   */
  private void attachScreenshot(final String name) {
    try {
      final byte[] screenshot =
          ((TakesScreenshot) driverContext.getDriver()).getScreenshotAs(OutputType.BYTES);

      Allure.addAttachment(name, new ByteArrayInputStream(screenshot));

      LOGGER.info("Screenshot attached");

    } catch (Exception e) {
      LOGGER.error("Screenshot failed", e);
    }
  }
}
