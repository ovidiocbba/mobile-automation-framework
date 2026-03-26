package com.ovidiomiranda.framework.core.enums;

import static java.util.Locale.ENGLISH;

/**
 * Defines execution types supported by the framework.
 *
 * <p>Supported values:
 *
 * <ul>
 *   <li>LOCAL: run tests on local Appium server
 *   <li>BROWSERSTACK: run tests on BrowserStack cloud
 * </ul>
 *
 * <p>This enum replaces boolean flags and improves readability.
 *
 * @author Ovidio Miranda
 */
public enum ExecutionType {
  /** Local execution using Appium server. */
  LOCAL,
  /** Cloud execution using BrowserStack. */
  BROWSERSTACK;

  /**
   * Converts string value to ExecutionType.
   *
   * @param value execution type from config
   * @return ExecutionType
   */
  public static ExecutionType from(final String value) {
    if (value == null) {
      return LOCAL;
    }
    try {
      return ExecutionType.valueOf(value.trim().toUpperCase(ENGLISH));
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Invalid execution type: " + value, e);
    }
  }
}
