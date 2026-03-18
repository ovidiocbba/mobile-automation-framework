package com.ovidiomiranda.framework.hooks;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.PLATFORM;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseId;
import static com.ovidiomiranda.framework.utils.ScenarioUtils.getTestCaseTitle;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverManager;
import com.ovidiomiranda.framework.core.enums.PlatformType;
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
  private static final String SEPARATOR = "============================================================";

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
   * <p>Reads platform from configuration and starts Appium driver.</p>
   */
  @Before(order = 0)
  public void setUp() {
    String platform = ConfigValidator.require(PLATFORM);
    DriverManager.initDriver(PlatformType.valueOf(platform.toUpperCase()));
  }

  /**
   * Logs result, attaches screenshot on failure, and closes driver.
   *
   * @param scenario executed scenario
   */
  @After
  public void afterScenario(Scenario scenario) {
    String testCaseId = getTestCaseId(scenario);

    long durationMs = System.currentTimeMillis() - startTime;
    double durationSec = durationMs / 1000.0;

    String formattedDuration = String.format("%.2f", durationSec);

    if (scenario.isFailed()) {
      LOGGER.error("RESULT: FAILED | Duration: {} s", formattedDuration);
      attachScreenshot(testCaseId);
    } else {
      LOGGER.info("RESULT: PASSED | Duration: {} s", formattedDuration);
    }

    LOGGER.info(SEPARATOR);
    DriverManager.quitDriver();
  }

  /**
   * Takes screenshot and adds it to Allure report.
   *
   * @param name screenshot name
   */
  private void attachScreenshot(String name) {
    try {
      byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
          .getScreenshotAs(OutputType.BYTES);

      Allure.addAttachment(name, new ByteArrayInputStream(screenshot));

      LOGGER.info("Screenshot attached");

    } catch (Exception e) {
      LOGGER.error("Screenshot failed", e);
    }
  }
}
