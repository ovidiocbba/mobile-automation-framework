package com.ovidiomiranda.framework.core.locators;

import com.ovidiomiranda.framework.core.enums.PlatformType;
import org.openqa.selenium.By;

/**
 * Represents a locator that supports both Android and iOS.
 *
 * <p>This class allows defining one locator per platform and resolving it at runtime based on the
 * current platform.
 *
 * @author Ovidio Miranda
 */
public class MobileLocator {

  private final By android;
  private final By ios;

  /**
   * Constructor for MobileLocator.
   *
   * @param android locator for Android platform
   * @param ios locator for iOS platform
   */
  public MobileLocator(By android, By ios) {
    this.android = android;
    this.ios = ios;
  }

  /**
   * Returns the correct locator based on platform.
   *
   * @param platform platform type (ANDROID or IOS)
   * @return locator for the given platform
   */
  public By get(PlatformType platform) {
    if (platform == PlatformType.ANDROID) {
      return android;
    }
    return ios;
  }
}
