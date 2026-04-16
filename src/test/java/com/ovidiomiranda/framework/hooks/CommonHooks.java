package com.ovidiomiranda.framework.hooks;

import static com.ovidiomiranda.framework.core.enums.ExecutionType.BROWSERSTACK;
import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static com.ovidiomiranda.framework.core.utils.ExecutionUtils.getExecutionType;
import static com.ovidiomiranda.framework.utils.ExecutionLogger.logScenarioStart;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseId;
import static java.util.Locale.ENGLISH;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.driver.DriverFactory;
import com.ovidiomiranda.framework.core.enums.ExecutionType;
import com.ovidiomiranda.framework.core.enums.PlatformType;
import com.ovidiomiranda.framework.utils.BrowserStackUtils;
import com.ovidiomiranda.framework.utils.ExecutionLogger;
import com.ovidiomiranda.framework.utils.ScenarioUtils;
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
  private static final String SEPARATOR =
      "============================================================";

  /** Ensures execution config is logged only once per run. */
  private static final AtomicBoolean executionInfoPrinted = new AtomicBoolean(false);

  private long startTime;

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
   * Initializes driver and logs execution configuration once.
   *
   * @param scenario current scenario
   */
  @Before(order = 0)
  public void setUp(final Scenario scenario) {
    if (executionInfoPrinted.compareAndSet(false, true)) {
      ExecutionLogger.logExecutionConfig(config);
    }

    final String platform = config.require(PLATFORM);

    final AppiumDriver driver =
        driverFactory.createDriver(
            PlatformType.valueOf(platform.toUpperCase(ENGLISH)),
            ScenarioUtils.getTestCaseTitle(scenario));

    driverContext.setDriver(driver);
  }

  /**
   * Logs scenario start and initializes execution timer.
   *
   * @param scenario current scenario
   */
  @Before(order = 1)
  public void beforeScenario(final Scenario scenario) {
    startTime = System.currentTimeMillis();

    logScenarioStart(scenario, driverContext.getDriver());
  }

  /** Attaches BrowserStack session link to Allure report if applicable. */
  @After(order = 1)
  public void attachBrowserStackSessionLink() {
    final ExecutionType executionType = getExecutionType(config);

    if (executionType == BROWSERSTACK) {
      BrowserStackUtils.attachSession(driverContext.getDriver());
    }
  }

  /**
   * Logs scenario result, attaches screenshot on failure, and closes driver.
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
   * Takes screenshot and attaches it to Allure report.
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
