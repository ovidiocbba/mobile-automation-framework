package com.ovidiomiranda.framework.core.driver;

import com.ovidiomiranda.framework.core.providers.AndroidDriverProvider;
import com.ovidiomiranda.framework.core.providers.DriverProvider;
import com.ovidiomiranda.framework.core.enums.PlatformType;
import com.ovidiomiranda.framework.core.providers.iOSDriverProvider;
import io.appium.java_client.AppiumDriver;
import java.util.EnumMap;
import java.util.Map;

/**
 * Factory responsible for creating Appium drivers.
 *
 * <p>The correct driver provider is selected based on the platform type.</p>
 *
 * @author Ovidio Miranda
 */
public final class DriverFactory {

  /**
   * Map that associates a platform type with its corresponding driver provider.
   */
  private static final Map<PlatformType, DriverProvider> DRIVERS = new EnumMap<>(
      PlatformType.class);

  static {
    DRIVERS.put(PlatformType.ANDROID, new AndroidDriverProvider());
    DRIVERS.put(PlatformType.IOS, new iOSDriverProvider());
  }

  /**
   * Private constructor to prevent instantiation.
   */
  private DriverFactory() {
  }

  /**
   * Creates a driver for the specified platform.
   *
   * @param platformType mobile platform
   * @return AppiumDriver instance
   */
  public static AppiumDriver createDriver(final PlatformType platformType) {
    DriverProvider provider = DRIVERS.get(platformType);
    if (provider == null) {
      throw new IllegalArgumentException("Unsupported platform type: " + platformType);
    }
    return provider.getDriver();
  }
}
