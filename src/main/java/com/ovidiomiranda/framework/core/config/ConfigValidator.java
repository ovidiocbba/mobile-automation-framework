package com.ovidiomiranda.framework.core.config;

import com.ovidiomiranda.framework.core.enums.PropertiesInput;

/**
 * Utility class used to validate configuration properties.
 *
 * <p>Ensures required properties exist before use.</p>
 *
 * @author Ovidio Miranda
 */
public class ConfigValidator {

  private final PropertiesManager propertiesManager;

  public ConfigValidator(PropertiesManager propertiesManager) {
    this.propertiesManager = propertiesManager;
  }

  /**
   * Returns a required property value.
   *
   * @param key configuration key
   * @return property value
   * @throws IllegalArgumentException if the property is missing
   */
  public String require(final PropertiesInput key) {
    String value = propertiesManager.getProperty(key);
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
  public String optional(final PropertiesInput key) {
    return propertiesManager.getProperty(key);
  }

  /**
   * Returns a required integer property.
   *
   * @param key configuration key
   * @return integer value
   */
  public int requireInt(final PropertiesInput key) {
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
   * @param key          configuration key
   * @param defaultValue value used if property is missing
   * @return integer value
   */
  public int optionalInt(final PropertiesInput key, final int defaultValue) {
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
