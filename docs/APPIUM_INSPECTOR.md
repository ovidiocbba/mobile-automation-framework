# :book:   Appium Inspector

Tool used to inspect mobile elements and obtain locators.

### Step 1: Download Appium Inspector

https://github.com/appium/appium-inspector/releases

**Windows**
```
Appium-Inspector-2026.2.1-win-x64.exe
```
**Mac - Apple Silicon (M1 / M2 / M3)**
```
Appium-Inspector-2026.2.1-mac-arm64.dmg
```
**Mac - Intel)**
```
Appium-Inspector-2026.2.1-mac-x64.dmg
```
### Step 2: Install

1. Open downloaded file
2. Follow installation wizard
3. Launch Appium Inspector

![1-follow-installation-wizard.png](images/appium-inspector/1-follow-installation-wizard.png)

![2-launch-appium-inspector.png](images/appium-inspector/2-launch-appium-inspector.png)

### Step 3: Configure session

```json
{
  "platformName": "Android",
  "deviceName": "Pixel_7",
  "automationName": "UiAutomator2",
  "app": "C:/Users/ovidio.miranda/Documents/Projects/mobile-automation-framework/apps/android/mda-2.2.0-25.apk"
}
```

### Step 4: Start session

Ensure emulator and Appium server are running:

```bash
appium
```

Then click **Start Session**

![3-configure-session.png](images/appium-inspector/3-configure-session.png)

![4-start-session.png](images/appium-inspector/4-start-session.png)

### Step 5: Inspect elements
Use locators following the same priority applied in large-scale mobile automation projects.

Priority order (recommended)

1. **resource-id (PRIMARY – default choice)**
    - Most stable and fastest locator in Android
    - Does not depend on UI text or language
    - Preferred for long-term maintainability

```
AppiumBy.id("com.saucelabs.mydemoapp.android:id/menuIV")
```
2. accessibilityId (SECONDARY)

   - Based on content-desc
   - Used when resource-id is not unique or not available
   - Good for semantic and readable locators

```
AppiumBy.accessibilityId("Login Menu Item")
```
3. xpath (FALLBACK ONLY)
   - Use only when no stable locator exists
   - Slower and more fragile
   - Can combine attributes when necessary

```
AppiumBy.xpath("//*[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='Log In']")
```

**Rules used**

- Always try resource-id first
- Use accessibilityId only if it adds uniqueness or clarity
- Use xpath as last resort
- Never use:
  - index
  - elementId
  - only class

**Summary**

| Priority | Locator Type    | When to use                          |
| -------- | --------------- | ------------------------------------ |
| 1        | resource-id     | Default for Android elements         |
| 2        | accessibilityId | When ID is not unique or unavailable |
| 3        | xpath           | Only if no other option exists       |

