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

| Name            | Example Value                  | Description                                |
|-----------------|--------------------------------|--------------------------------------------|
| `USERNAME`      | `standard_user`                | Username used during mobile test execution |
| `PASSWORD`      | `secret_sauce`                 | Password used during mobile test execution |
| `BS_USERNAME`   | `your_browserstack_username`   | BrowserStack account username              |
| `BS_ACCESS_KEY` | `your_browserstack_access_key` | BrowserStack access key                    |
| `BS_APP`        | `bs://xxxxxxxxxxxxxxxx`        | BrowserStack uploaded app ID               |

----------------------------------------------------------------------------------------------


### 📘 USERNAME and PASSWORD

The `USERNAME` and `PASSWORD` repository secrets are required for the
mobile test execution workflow.

These values are **not hardcoded in the framework**. Instead, they are
securely injected into the execution through GitHub Actions.

Example usage inside the workflow:

``` yaml
env:
  USERNAME: ${{ secrets.USERNAME }}
  PASSWORD: ${{ secrets.PASSWORD }}
```

These variables are then passed to Gradle as system properties:

``` bash
-Dusername=${USERNAME}
-Dpassword=${PASSWORD}
```
------------------------------------------------------------------------

# ☁️ BrowserStack Configuration

To execute tests on **real devices in BrowserStack**, the following
secrets must also be configured.

| Secret        | Description                                |
|---------------|--------------------------------------------|
| BS_USERNAME   | BrowserStack username                      |
| BS_ACCESS_KEY | BrowserStack access key                    |
| BS_APP        | App uploaded to BrowserStack (bs://app-id) |

Example configuration inside the workflow:

``` yaml
env:
  BS_USERNAME: ${{ secrets.BS_USERNAME }}
  BS_ACCESS_KEY: ${{ secrets.BS_ACCESS_KEY }}
  BS_APP: ${{ secrets.BS_APP }}
```

These values are passed to Gradle as system properties:

``` bash
-Dbs.username=${BS_USERNAME}
-Dbs.accessKey=${BS_ACCESS_KEY}
-Dbs.app=${BS_APP}
```

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

1.  The user triggers the workflow manually (`workflow_dispatch`).

2.  GitHub Actions loads the secrets:

    USERNAME\
    PASSWORD

3.  The workflow passes them to the mobile execution action.

4.  The framework receives them as system properties and uses them
    during the login step of the mobile test.

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

## 🔹 Repository Variables

No additional repository variables are required for mobile execution.

------------------------------------------------------------------------

## 🚀 Supported Execution Modes

This project supports the following execution strategies:

-   Manual execution (`workflow_dispatch`)
-   CI execution using Android Emulator in GitHub Actions
-   Allure report generation and deployment to `gh-pages`

------------------------------------------------------------------------

## 📦 Reports

After execution:

-   Mobile test logs are uploaded as workflow artifacts
-   Allure results are generated automatically
-   Reports are deployed to the `gh-pages` branch
