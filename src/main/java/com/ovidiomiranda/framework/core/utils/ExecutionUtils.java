package com.ovidiomiranda.framework.core.utils;

import static com.ovidiomiranda.framework.core.enums.PropertiesInput.EXECUTION;

import com.ovidiomiranda.framework.core.config.ConfigValidator;
import com.ovidiomiranda.framework.core.enums.ExecutionType;

/**
 * Utility class for execution type handling.
 *
 * <p>Reads execution configuration and determines where tests will run.
 *
 * @author Ovidio Miranda
 */
public final class ExecutionUtils {

  private ExecutionUtils() {}

  /**
   * Gets execution type from configuration.
   *
   * @param config configuration validator
   * @return ExecutionType
   */
  public static ExecutionType getExecutionType(final ConfigValidator config) {
    return ExecutionType.from(config.optional(EXECUTION));
  }

  /**
   * Checks if execution is BrowserStack.
   *
   * @param config configuration validator
   * @return true if BrowserStack
   */
  public static boolean isBrowserStack(final ConfigValidator config) {
    return getExecutionType(config) == ExecutionType.BROWSERSTACK;
  }
}
