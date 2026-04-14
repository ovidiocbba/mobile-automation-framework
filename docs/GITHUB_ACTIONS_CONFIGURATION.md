## 🔧 GitHub Actions Configuration

This project requires specific **GitHub Actions variables and secrets**
to run the mobile automation workflows correctly.

------------------------------------------------------------------------

### 📍 How to Configure

Navigate to:

Repository → Settings → Secrets and variables → Actions

------------------------------------------------------------------------

## 🔹 Repository Secrets

Click on **Secrets → New repository secret** and add the following:

| Name                       | Example Value                  | Description                                |
|----------------------------|--------------------------------|--------------------------------------------|
| `APP_USERNAME`             | `standard_user`                | Username used during mobile test execution |
| `APP_PASSWORD`             | `secret_sauce`                 | Password used during mobile test execution |
| `BROWSERSTACK_USERNAME`    | `your_browserstack_username`   | BrowserStack account username              |
| `BROWSERSTACK_ACCESS_KEY`  | `your_browserstack_access_key` | BrowserStack access key                    |
| `BROWSERSTACK_APP_ANDROID` | `bs://xxxxxxxxxxxxxxxx`        | Android app uploaded to BrowserStack       |
| `BROWSERSTACK_APP_IOS`     | `bs://xxxxxxxxxxxxxxxx`        | iOS app uploaded to BrowserStack           |

----------------------------------------------------------------------------------------------

### 📘 USERNAME and PASSWORD

The `APP_USERNAME` and `APP_PASSWORD` repository secrets are required for the
mobile test execution workflow.

These values are **not hardcoded in the framework**. Instead, they are
securely injected into the execution through GitHub Actions.

Example usage inside the workflow:

``` yaml
env:
  APP_USERNAME: ${{ secrets.APP_USERNAME }}
  APP_PASSWORD: ${{ secrets.APP_PASSWORD }}
```

These variables are then passed to Gradle as system properties:

``` bash
-Dapp.username=${APP_USERNAME}
-Dapp.password=${APP_PASSWORD}
```
------------------------------------------------------------------------

# ☁️ BrowserStack Configuration

> The workflow automatically selects the correct app based on the platform (ANDROID or IOS).

To execute tests on **real devices in BrowserStack**, the following
secrets must also be configured.

| Secret                   | Description                                        |
|--------------------------|----------------------------------------------------|
| BROWSERSTACK_USERNAME    | BrowserStack username                              |
| BROWSERSTACK_ACCESS_KEY  | BrowserStack access key                            |
| BROWSERSTACK_APP_ANDROID | Android app uploaded to BrowserStack (bs://app-id) |
| BROWSERSTACK_APP_IOS     | iOS app uploaded to BrowserStack (bs://app-id)     |

Example configuration inside the workflow:

``` yaml
env:
  BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
  BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
  BROWSERSTACK_APP_ANDROID: ${{ secrets.BROWSERSTACK_APP_ANDROID }}
  BROWSERSTACK_APP_IOS: ${{ secrets.BROWSERSTACK_APP_IOS }}
```

These values are passed to Gradle as system properties:

``` bash
-Dbrowserstack.username=${BROWSERSTACK_USERNAME}
-Dbrowserstack.accessKey=${BROWSERSTACK_ACCESS_KEY}
-Dbrowserstack.app=<automatically resolved based on platform (ANDROID / IOS)>
```

------------------------------------------------------------------------

## 🔹 Repository Variables

> `EXECUTION_DEVICES` defines the **actual devices used during execution**, not just supported ones.

The workflow now supports **dynamic device execution** using a
repository variable.

Go to:

Repository → Settings → Secrets and variables → Actions → Variables

Create a new variable:

    EXECUTION_DEVICES (JSON format)

### Example value

``` json
[
  {
    "deviceName": "Samsung Galaxy S23",
    "platformVersion": "13",
    "platform": "ANDROID",
    "automationName": "UiAutomator2"
  },
  {
    "deviceName": "Google Pixel 7",
    "platformVersion": "13",
    "platform": "ANDROID",
    "automationName": "UiAutomator2"
  },
  {
    "deviceName": "iPhone 15",
    "platformVersion": "17",
    "platform": "IOS",
    "automationName": "XCUITest"
  }
]
```

This allows the workflow to run:

-   All devices at the same time
-   One specific device
-   Multiple platforms (Android + IOS)

without modifying the workflow file.

------------------------------------------------------------------------

## 📱 How Device Selection Works

When running the workflow manually, you will see this input:

    device = ALL

### If you use

    device = ALL

the workflow executes **all devices defined in `EXECUTION_DEVICES`**.

> Device names must match exactly (case-sensitive) with the values defined in `EXECUTION_DEVICES`.

### If you want to run only one device

You must type the exact device name defined in the JSON.

Example:

**Device**

```
Samsung Galaxy S23
```

This will execute only that device.

------------------------------------------------------------------------

## Why This Is Required

The credentials are required because the mobile tests include an
authentication flow. Without these values:

-   The workflow will fail before executing the test scenarios
-   Login tests will not be able to run
-   CI execution will not match real user behavior

Using GitHub Secrets ensures the credentials remain secure while still
allowing the tests to execute correctly in CI.

------------------------------------------------------------------------

## How It Works

1.  The user triggers the workflow manually (`workflow_dispatch`)

2.  GitHub Actions loads the secrets:

    APP_USERNAME
    APP_PASSWORD
    BROWSERSTACK_USERNAME
    BROWSERSTACK_ACCESS_KEY
    BROWSERSTACK_APP_ANDROID
    BROWSERSTACK_APP_IOS

3.  The workflow reads the `EXECUTION_DEVICES` variable

4.  The framework receives all values as system properties and executes
    the tests

------------------------------------------------------------------------

## 🔹 GitHub Pages Configuration (Required for Allure Reports)

Before running the workflow for the first time, you must create an
**orphan branch** to store the generated reports.

The workflow deploys the reports to the `gh-pages` branch.

------------------------------------------------------------------------

### How to Create the `gh-pages` Branch

Run the following commands locally:

``` bash
# Create orphan branch
git checkout --orphan gh-pages

# Remove all files from the branch
git reset --hard

# Create an empty commit
git commit --allow-empty -m "Initial commit on gh-pages branch"

# Push the branch to GitHub
git push origin gh-pages
```

After this step, the workflow will be able to deploy Allure reports
automatically.

------------------------------------------------------------------------

## 🚀 Supported Execution Modes

This project supports the following execution strategies:

-   Manual execution (`workflow_dispatch`)
-   Execution on multiple real devices (BrowserStack)
-   Dynamic device selection (ALL or specific device)
-   Allure report generation and deployment to `gh-pages`


------------------------------------------------------------------------

## 📦 Reports

After execution:

-   Mobile test logs are uploaded as workflow artifacts
-   Allure results are generated automatically
-   Reports are deployed to the `gh-pages` branch
