package com.ovidiomiranda.framework.core.config;

import com.ovidiomiranda.framework.core.enums.PropertiesInput;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * Loads and provides configuration values from multiple property files.
 *
 * <p>This class supports layered configuration used in automation frameworks. Properties are loaded
 * in the following order:
 *
 * <pre>
 * base.properties
 * platform specific (android.properties or ios.properties)
 * execution specific (local.properties or browserstack.properties)
 * system properties (-D) override all values
 * </pre>
 *
 * <p>This allows flexible configuration for different platforms, environments, and CI pipelines
 * without modifying source code.
 *
 * @author Ovidio Miranda
 */
public class PropertiesManager {

  // Default mobile platform when running on macOS
  // Used when executing scenarios directly from IntelliJ IDEA
  // Supported values: IOS or ANDROID
  private static final String DEFAULT_MOBILE_PLATFORM_ON_MAC = "IOS";

  private final Properties properties;

  /** Constructor for PropertiesManager. */
  public PropertiesManager() {
    this.properties = loadProperties();
  }

  /**
   * Determines default platform for execution based on OS.
   *
   * <p>Used when no -Dplatform is provided (e.g. running directly from IntelliJ by scenario).
   * Allows IDE execution without manual configuration. This behavior can be modified if needed.
   *
   * @return default platform for execution (IOS or ANDROID)
   */
  private String getDefaultPlatformForExecution() {
    final String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
    return os.contains("mac") ? DEFAULT_MOBILE_PLATFORM_ON_MAC : "ANDROID";
  }

  /**
   * Loads configuration properties from multiple configuration layers.
   *
   * <p>Loading order:
   *
   * <ul>
   *   <li>base configuration
   *   <li>platform configuration
   *   <li>execution environment configuration
   * </ul>
   *
   * @return loaded Properties object
   */
  private Properties loadProperties() {
    final Properties props = new Properties();

    // Load base configuration
    loadFile(props, "config/base.properties");

    // Determine platform (System property has priority)
    final String platform =
        System.getProperty("platform") != null
            ? System.getProperty("platform")
            : getDefaultPlatformForExecution();

    if ("ANDROID".equalsIgnoreCase(platform)) {
      loadFile(props, "config/android.properties");
    } else if ("IOS".equalsIgnoreCase(platform)) {
      loadFile(props, "config/ios.properties");
    }

    // Determine execution environment (default local)
    final String execution = System.getProperty("framework.execution", "local");

    if ("browserstack".equalsIgnoreCase(execution)) {
      loadFile(props, "config/browserstack.properties");
    } else {
      loadFile(props, "config/local.properties");
    }

    return props;
  }

  /**
   * Loads a property file into the given Properties object.
   *
   * @param props properties container
   * @param fileName file name located in resources
   */
  private void loadFile(final Properties props, final String fileName) {
    try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {

      if (input == null) {
        throw new RuntimeException("Configuration file not found: " + fileName);
      }

      props.load(input);

    } catch (IOException e) {

      throw new RuntimeException("Failed to load configuration file: " + fileName, e);
    }
  }

  /**
   * Gets property value based on enum key.
   *
   * <p>System properties override values from the config file.
   *
   * @param key configuration key
   * @return property value
   */
  public String getProperty(final PropertiesInput key) {
    final String systemValue = System.getProperty(key.getPropertiesName());
    return systemValue != null ? systemValue : properties.getProperty(key.getPropertiesName());
  }
}
