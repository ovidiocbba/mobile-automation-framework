package com.ovidiomiranda.framework.core.config;

import com.ovidiomiranda.framework.core.enums.PropertiesInput;

/**
 * Utility class used to validate configuration properties.
 *
 * <p>This class ensures required configuration values exist before they are used by the
 * framework.</p>
 *
 * @author Ovidio Miranda
 */
public final class ConfigValidator {

  /**
   * Private constructor to prevent instantiation.
   */
  private ConfigValidator() {
  }

  /**
   * Returns a required property value.
   *
   * @param key configuration key
   * @return property value
   * @throws IllegalArgumentException if the property is missing
   */
  public static String require(final PropertiesInput key) {
    String value = PropertiesManager.getInstance().getProperty(key);
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(
          "Required property is missing: " + key.getPropertiesName());
    }
    return value;
  }

  /**
   * Returns an optional property value.
   *
   * @param key configuration key
   * @return property value or null
   */
  public static String optional(final PropertiesInput key) {
    return PropertiesManager.getInstance().getProperty(key);
  }

  /**
   * Returns a required integer property.
   *
   * @param key configuration key
   * @return integer value
   */
  public static int requireInt(final PropertiesInput key) {
    String value = require(key);
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Property must be a valid integer: " + key.getPropertiesName(), e);
    }
  }

  /**
   * Returns an optional integer property.
   *
   * @param key configuration key
   * @param defaultValue value used if property is missing
   * @return integer value
   */
  public static int optionalInt(final PropertiesInput key, final int defaultValue) {
    String value = optional(key);
    if (value == null || value.isBlank()) {
      return defaultValue;
    }
    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Property must be a valid integer: " + key.getPropertiesName(), e);
    }
  }
}
