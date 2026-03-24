package com.ovidiomiranda.framework.core.config;

import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads and provides configuration values from config.properties.
 *
 * @author Ovidio Miranda
 */
public class PropertiesManager {

  private final Properties properties;

  /** Constructor for PropertiesManager. */
  public PropertiesManager() {
    this.properties = loadProperties();
  }

  /**
   * Loads configuration properties from the classpath.
   *
   * @return loaded Properties object
   */
  private Properties loadProperties() {
    Properties props = new Properties();
    try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
      if (input == null) {
        throw new RuntimeException("config.properties not found");
      }
      props.load(input);
    } catch (IOException e) {
      throw new RuntimeException("Failed to load properties", e);
    }
    return props;
  }

  /**
   * Gets property value based on enum key.
   *
   * <p>System properties override values from the config file.
   *
   * @param key configuration key
   * @return property value
   */
  public String getProperty(PropertiesInput key) {
    String systemValue = System.getProperty(key.getPropertiesName());
    return systemValue != null ? systemValue : properties.getProperty(key.getPropertiesName());
  }
}
