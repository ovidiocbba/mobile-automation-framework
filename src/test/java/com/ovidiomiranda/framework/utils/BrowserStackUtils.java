package com.ovidiomiranda.framework.utils;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for BrowserStack integration.
 *
 * <p>This class is responsible for:
 *
 * <ul>
 *   <li>Extracting session id from Appium driver
 *   <li>Building BrowserStack session URL
 *   <li>Attaching session link to Allure report
 *   <li>Printing session info in console logs
 * </ul>
 *
 * <p>This helps debugging faster by allowing direct access to BrowserStack session.
 *
 * @author Ovidio Miranda
 */
public final class BrowserStackUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(BrowserStackUtils.class);

  private static final String BROWSERSTACK_URL =
      "https://app-automate.browserstack.com/dashboard/v2/sessions/";

  private BrowserStackUtils() {
    // Private constructor to avoid instantiation
  }

  /**
   * Attaches BrowserStack session link to Allure report.
   *
   * <p>This method:
   *
   * <ul>
   *   <li>Gets sessionId from driver
   *   <li>Builds session URL
   *   <li>Adds clickable link in Allure
   *   <li>Adds readable attachment
   *   <li>Logs session URL in console
   * </ul>
   *
   * @param driver current Appium driver
   */
  public static void attachSession(final AppiumDriver driver) {
    try {
      if (driver == null || driver.getSessionId() == null) {
        LOGGER.warn("BrowserStack session not available (driver or sessionId is null)");
        return;
      }

      final String sessionId = driver.getSessionId().toString();
      final String sessionUrl = BROWSERSTACK_URL + sessionId;

      Allure.addAttachment(" BrowserStack Video", sessionUrl);
    } catch (Exception e) {
      LOGGER.error("Failed to attach BrowserStack session", e);
    }
  }
}
