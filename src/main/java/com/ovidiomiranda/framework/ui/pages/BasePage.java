package com.ovidiomiranda.framework.ui.pages;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.driver.DriverContext;
import com.ovidiomiranda.framework.core.interactions.MobileElementActions;
import com.ovidiomiranda.framework.ui.components.BaseComponent;

/**
 * Base class for all Page Objects.
 *
 * <p>Provides access to driver and shared interactions.
 *
 * @author Ovidio Miranda
 */
public abstract class BasePage extends BaseComponent {

  /**
   * Constructor.
   *
   * @param config config validator
   * @param driverContext driver context
   * @param actions mobile actions utility
   */
  protected BasePage(
      ConfigValidator config, DriverContext driverContext, MobileElementActions actions) {
    super(config, driverContext, actions);
  }
}
