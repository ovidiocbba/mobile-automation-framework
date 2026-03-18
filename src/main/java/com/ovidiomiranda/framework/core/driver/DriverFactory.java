package com.ovidiomiranda.framework.core.driver;

import static com.ovidiomiranda.framework.core.enums.PlatformType.ANDROID;
import static com.ovidiomiranda.framework.core.enums.PlatformType.IOS;

import com.ovidiomiranda.framework.core.enums.PlatformType;
import com.ovidiomiranda.framework.core.providers.AndroidDriverProvider;
import com.ovidiomiranda.framework.core.providers.DriverProvider;
import com.ovidiomiranda.framework.core.providers.iOSDriverProvider;
import io.appium.java_client.AppiumDriver;
import java.util.EnumMap;
import java.util.Map;

/**
 * Factory to create AppiumDriver instances for different platforms.
 *
 * <p>Selects the correct driver provider (Android or iOS) based on the platform type.</p>
 *
 * @author Ovidio Miranda
 */
public class DriverFactory {

  private final Map<PlatformType, DriverProvider> drivers;

  /**
   * Constructor to initialize driver providers.
   *
   * @param androidProvider provider for Android drivers
   * @param iosProvider     provider for iOS drivers
   */
  public DriverFactory(AndroidDriverProvider androidProvider, iOSDriverProvider iosProvider) {
    this.drivers = new EnumMap<>(PlatformType.class);
    drivers.put(ANDROID, androidProvider);
    drivers.put(IOS, iosProvider);
  }

  /**
   * Creates an AppiumDriver for the given platform.
   *
   * @param platformType platform to create driver for
   * @return AppiumDriver instance
   * @throws IllegalArgumentException if platform type is not supported
   */
  public AppiumDriver createDriver(final PlatformType platformType) {
    DriverProvider provider = drivers.get(platformType);
    if (provider == null) {
      throw new IllegalArgumentException("Unsupported platform type: " + platformType);
    }
    return provider.getDriver();
  }
}
