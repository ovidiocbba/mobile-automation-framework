package com.ovidiomiranda.framework.core.config;

import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads and provides configuration values from config.properties.
 *
 * <p>This class uses a singleton pattern to ensure properties are loaded only once.</p>
 */
public final class PropertiesManager {

  private static PropertiesManager propertiesManager;
  private final Properties properties;

  /**
   * Constructor for PropertiesManager.
   */
  private PropertiesManager() {
    properties = loadProperties();
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
   * Gets the singleton instance of PropertiesManager.
   *
   * @return PropertiesManager instance
   */
  public static synchronized PropertiesManager getInstance() {
    if (propertiesManager == null) {
      propertiesManager = new PropertiesManager();
    }
    return propertiesManager;
  }

  /**
   * Gets property value based on enum key.
   *
   * <p>System properties override values from the config file.</p>
   *
   * @param key configuration key
   * @return property value
   */
  public String getProperty(PropertiesInput key) {
    String systemValue = System.getProperty(key.getPropertiesName());
    return systemValue != null ? systemValue : properties.getProperty(key.getPropertiesName());
  }
}
