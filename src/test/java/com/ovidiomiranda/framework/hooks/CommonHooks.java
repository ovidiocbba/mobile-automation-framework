package com.ovidiomiranda.framework.hooks;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseId;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseTitle;
import static java.util.Locale.ENGLISH;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.driver.DriverFactory;
import com.ovidiomiranda.framework.core.enums.PlatformType;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
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
  @Before(order = -1)
  public void beforeScenario(final Scenario scenario) {
    startTime = System.currentTimeMillis();
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(SEPARATOR);
      LOGGER.info(">>> STARTING SCENARIO | {}", getTestCaseTitle(scenario));
      LOGGER.info(SEPARATOR);
    }
  }

  /**
   * Initializes the driver based on configured platform.
   *
   * <p>Reads platform from configuration and starts Appium driver.
   */
  @Before(order = 0)
  public void setUp() {
    final String platform = config.require(PLATFORM);
    final AppiumDriver driver =
        driverFactory.createDriver(PlatformType.valueOf(platform.toUpperCase(ENGLISH)));
    driverContext.setDriver(driver);
  }

  /**
   * Logs result, attaches screenshot on failure, and closes driver.
   *
   * @param scenario executed scenario
   */
  @After
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
