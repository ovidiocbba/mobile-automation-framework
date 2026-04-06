# 🚀 Jenkins Setup with Docker

This guide explains how to build and run a fully configured **Jenkins Mobile Automation Server** using Docker with persistent storage and browser support.

---

<h1>Table of contents</h1>

* [:whale: 1. Build Docker Image](#whale-1-build-docker-image)
* [:floppy_disk: 2. Create Persistent Volume](#floppy_disk-2-create-persistent-volume)
* [:rocket: 3. Run Jenkins Container](#rocket-3-run-jenkins-container)
* [:gear: 4. Initial Jenkins Setup](#gear-4-initial-jenkins-setup)
* [:wastebasket: 5. Clean Setup (Optional)](#wastebasket-5-clean-setup-optional)
* [:memo: 6. Notes](#memo-6-notes)

---

## :whale: 1. Build Docker Image

The `Dockerfile` is located at:

```
mobile-automation-framework/Dockerfile
```

If you are inside the `mobile-automation-framework` folder, you can run

```bash
docker build --no-cache -t jenkins-mobile-ci:1.0.0 .
```

Builds a custom Jenkins image with the required plugins.

### 🔧 This image includes:

* Java 17
* Allure CLI
* Node.js (optional tools)
* Jenkins plugins (Pipeline, Allure, HTML Publisher, Blue Ocean, etc.)
* UTF-8 configuration (important for logs and reports)

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---

## :floppy_disk: 2. Create Persistent Volume

```bash
docker volume create jenkins_mobile_ci
```

This volume stores:

* Jenkins configuration
* Jobs
* Plugins
* Allure history
* Credentials

⚠ Data persists even if the container is removed.

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---

## :rocket: 3. Run Jenkins Container

You can run the Jenkins container using **two different approaches** depending on your needs.

### Option 1: Inline environment variables (Quick Start)
```bash
docker run -d \
  --name jenkins-mobile-automation \
  --restart unless-stopped \
  -p 8081:8080 \
  -p 50001:50000 \
  -v jenkins_mobile_ci:/var/jenkins_home \
  -e JENKINS_ADMIN_ID=admin \
  -e JENKINS_ADMIN_PASSWORD=SuperSecurePass2026! \
  -e GIT_REPO=https://github.com/ovidiocbba/mobile-automation-framework \
  -e GIT_BRANCH=main \
  -e APP_USERNAME=standard_user \
  -e APP_PASSWORD=secret_sauce \
  -e BS_USERNAME=your_browserstack_username \
  -e BS_ACCESS_KEY=your_browserstack_access_key \
  -e BS_APP=bs://your_uploaded_app_id \
  -e SUPPORTED_DEVICES='[
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
    }
  ]' \
  jenkins-mobile-ci:1.0.0
```

**Note**:

Be sure to change the default password `SuperSecurePass2026!` to a password you choose. This will help keep your Jenkins secure before using it.
The password you set here will be used for the Jenkins admin login during the first setup.

### Option 2: Using `.env` file (Recommended)

Instead of passing all environment variables manually, you can use an .env file.

File location:
```
jenkins/env/.env.qa
```

```bash
docker run -d \
  --name jenkins-mobile-automation \
  --restart unless-stopped \
  -p 8081:8080 \
  -p 50001:50000 \
  -v jenkins_mobile_ci:/var/jenkins_home \
  --env-file jenkins/env/.env.qa \
  jenkins-mobile-ci:1.0.0
```

### 🔎 What each option does:

| Option                                   | Description                                      |
|------------------------------------------|--------------------------------------------------|
| `--restart unless-stopped`               | Automatically restarts the container if it stops |
| `-p 8081:8080`                           | Exposes Jenkins web interface (UI)               |
| `-p 50001:50000`                         | Agent communication port for Jenkins nodes       |
| `-v jenkins_mobile_ci:/var/jenkins_home` | Persists Jenkins data between container restarts |
| `--env-file .env`                        | Load variables from file                         |

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

### :key: 3.1 Accessing the Jenkins Container

To access the Jenkins container's shell for troubleshooting or configuration, run this command:

```
docker exec -it jenkins-mobile-automation bash
```

This will open an interactive shell inside the running container. From here, you can:

- Check logs
- Change Jenkins settings
- Troubleshoot problems

To exit the shell, type:
```
exit
```

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---

## :gear: 4. Initial Jenkins Setup

### 4.1 Plugin Installation

Open the default URL:

```
http://localhost:8081/
```

When you first access Jenkins, you will be prompted to log in.
Use the credentials specified in the environment variables (`JENKINS_ADMIN_ID` and `JENKINS_ADMIN_PASSWORD`) to access Jenkins.

![00-login.png](images/jenkins/00-login.png)

Since plugins are already installed via Dockerfile:

Select:

```
Select plugins to install
```

![01-select-plugins-to-install.png](images/jenkins/01-select-plugins-to-install.png)

Then choose:

```
None
```

Click **Install**.

![02-no-need-to-install-suggested-plugins.png](images/jenkins/02-no-need-to-install-suggested-plugins.png)

No need to install suggested plugins again.

---

### 4.2 Instance Configuration

Click:

```
Save and Finish
```

![03-instance-configuration.png](images/jenkins/03-instance-configuration.png)

Then click:

```
Start using Jenkins
```

![04-start-using-jenkins.png](images/jenkins/04-start-using-jenkins.png)

---

✅ Jenkins is now ready to use.

![05-jenkins-is-now-ready-to-use.png](images/jenkins/05-jenkins-is-now-ready-to-use.png)

![06-jenkins-is-now-ready-to-use.png](images/jenkins/06-jenkins-is-now-ready-to-use.png)

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---

## :wastebasket: 5. Clean Setup (Optional)

Remove container:

```bash
docker rm -f jenkins-mobile-automation
```

Remove volume:

```bash
docker volume rm jenkins_mobile_ci
```

⚠ This will permanently delete all Jenkins data.

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---

## :memo: 6. Notes

* This setup is optimized for Mobile Automation using BrowserStack
* UTF-8 is enabled to avoid encoding issues
* Devices are configurable via environment variables
* Supports:
  - Parallel execution
  - Allure reports
  - Dynamic device selection

<div align="right">
  <strong>
    <a href="#table-of-contents">↥ Back to top</a>
  </strong>
</div>

---
