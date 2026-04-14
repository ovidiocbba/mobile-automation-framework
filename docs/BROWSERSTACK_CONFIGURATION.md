# :cloud: BrowserStack Setup Guide

Step-by-step guide to run mobile automation tests using BrowserStack.

---

<h1>Table of contents</h1>

* [:bust_in_silhouette: 1. Create BrowserStack Account](#bust_in_silhouette-1-create-browserstack-account)
* [:key: 2. Get Credentials](#key-2-get-credentials)
* [:arrow_up: 3. Upload App](#arrow_up-3-upload-app)
* [:gear: 4. Configure Project](#gear-4-configure-project)
* [:iphone: 4.1 Validate Available Devices](#iphone-41-validate-available-devices)
* [:rocket: 5. Run Tests](#rocket-5-run-tests)
* [:mag: 6. Verify Execution](#mag-6-verify-execution)

---

## :bust_in_silhouette: 1. Create BrowserStack Account

1. Go to:

[https://www.browserstack.com/users/sign_up](https://www.browserstack.com/users/sign_up)

2. Create a free account
3. Verify your email

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :key: 2. Get Credentials

1. Go to:

[https://www.browserstack.com/accounts/profile/details](https://www.browserstack.com/accounts/profile/details)

2. Under **Authentication & Security**

3. Copy:

```
Username
Access Key
```

4. Use them in your configuration:

```properties
bs.username=YOUR_USERNAME
bs.accessKey=YOUR_ACCESS_KEY
```

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :arrow_up: 3. Upload App
### Step 1: Open App Automate

Go to:

https://app-automate.browserstack.com/

### Step 2: Activate App Automate (new accounts only)

If you see a welcome screen:

1. Click **Get Started**

---

### Step 3: Quickstart setup (first time only)

You will be redirected to a **Quickstart page**

1. Under framework selection choose:

- Automation: **Appium**
- Language: **Java**

### Step 4: Skip Quickstart

1. Click:
`Skip to dashboard`

### Step 5: Go to Dashboard

After skipping:

1. Navigate to **App Automate Dashboard**

---

### Step 6: Upload your app

Look for a button like:
```
Upload
```

---

### Step 7: Select your APK and iOS Apps

**Android App (.apk)**

Upload your Android app:
`
apps/android/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk
`
**iOS App (.ipa)**

Upload your iOS app:
`
apps/ios/iOS.RealDevice.SauceLabs.Mobile.Sample.app.2.7.1.ipa
`
---

### Step 8: Wait for upload

- The upload may take a few seconds
- BrowserStack will process the app automatically

---

### Step 9: Copy app URL

After upload, you will see something like:

```text
bs://123abc456
```
---

### Step 10: Use app URL

Add it to your configuration:
```text
bs.app=bs://123abc45
```

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :gear: 4. Configure Project

Open `config.properties` and update:

```properties
# Execution
execution=browserstack

# Device
deviceName=Google Pixel 7
platformVersion=13.0

# BrowserStack
bs.username=YOUR_USERNAME
bs.accessKey=YOUR_ACCESS_KEY
bs.app=bs://123abc456
bs.url=https://hub-cloud.browserstack.com/wd/hub
```

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :iphone: 4.1 Validate Available Devices

Run:

curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
https://api.browserstack.com/app-automate/devices.json

### Filter iPhone

curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
https://api.browserstack.com/app-automate/devices.json | grep -i iphone

### Filter Samsung

curl -u "YOUR_USERNAME:YOUR_ACCESS_KEY" \
https://api.browserstack.com/app-automate/devices.json | grep -i samsung

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :rocket: 5. Run Tests

Run the following command:

```bash
./gradlew clean executeFeatures -Dexecution=browserstack
```

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :mag: 6. Verify Execution

1. Go to:

[https://app-automate.browserstack.com/dashboard](https://app-automate.browserstack.com/dashboard)

2. Check your test execution

You will see:

* Test status
* Video recording
* Logs
* Screenshots

<div align="right">
  <strong>
    <a href="#table-of-contents" style="text-decoration: none;">↥ Back to top</a>
  </strong>
</div>

---

## :warning: Common Issues

### Invalid credentials

* Check username and access key

### App not found

* Make sure `bs.app` starts with `bs://`

### Session not created

* Verify deviceName and platformVersion

---

## :white_check_mark: Summary

| Step | Action            |
| ---- | ----------------- |
| 1    | Create account    |
| 2    | Get credentials   |
| 3    | Upload app        |
| 4    | Configure project |
| 5    | Run tests         |
| 6    | Verify execution  |

---
