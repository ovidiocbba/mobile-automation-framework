package com.ovidiomiranda.framework.core.waits;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.config.PropertiesInput;
import com.ovidiomiranda.framework.core.driver.DriverManager;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Creates configured WebDriverWait instances.
 *
 * <p>The timeout value is read from the configuration.</p>
 *
 * @author Ovidio Miranda
 */
public final class WaitManager {

  /**
   * Private constructor to prevent instantiation.
   */
  private WaitManager() {
  }

  /**
   * Returns a configured WebDriverWait.
   *
   * @return WebDriverWait instance
   */
  public static WebDriverWait getWait() {
    int timeout = ConfigValidator.optionalInt(PropertiesInput.EXPLICIT_WAIT, 15);
    WebDriver driver = DriverManager.getDriver();
    return new WebDriverWait(driver, Duration.ofSeconds(timeout));
  }
}
