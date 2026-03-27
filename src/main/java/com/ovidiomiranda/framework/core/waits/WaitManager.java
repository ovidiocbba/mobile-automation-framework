package com.ovidiomiranda.framework.core.waits;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Creates configured WebDriverWait instances for explicit waits.
 *
 * <p>Timeout is read from configuration.
 *
 * @author Ovidio Miranda
 */
public class WaitManager {

  private final DriverContext driverContext;
  private final ConfigValidator config;
  private WebDriverWait wait;

  /**
   * Constructor.
   *
   * @param driverContext driver context holding AppiumDriver
   * @param config configuration validator
   */
  public WaitManager(final DriverContext driverContext, final ConfigValidator config) {
    this.driverContext = driverContext;
    this.config = config;
  }

  /**
   * Returns a configured WebDriverWait instance.
   *
   * @return WebDriverWait instance
   */
  public WebDriverWait getWait() {
    if (wait == null) {
      final int timeout = config.optionalInt(PropertiesInput.EXPLICIT_WAIT, 15);
      wait = new WebDriverWait(driverContext.getDriver(), Duration.ofSeconds(timeout));
    }
    return wait;
  }
}
