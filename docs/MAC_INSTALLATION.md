# :computer: Mac Setup for Mobile Automation (Appium + iOS + Android)

---

# 1 Install Homebrew

### Install Homebrew
Installs package manager
```bash
/ bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Verify
Confirms installation
```bash
brew --version
```

**Example**

```bash
ovidio@192 ~ % brew --version
Homebrew 5.1.5
```

---

# 2 Install Java 17

### Install Java
Installs Java
```bash
brew install openjdk@17
```


### Link Java
Makes Java available system-wide
```bash
sudo ln -sfn /opt/homebrew/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
```

### Add to PATH
Adds Java to terminal
```bash
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
```

### Reload config
Applies changes
```bash
source ~/.zshrc
```

### Verify Java
Confirms Java works
```bash
java -version
```

**Example**
```
ovidio@192 ~ % java -version
openjdk version "17.0.18" 2026-01-20
OpenJDK Runtime Environment Homebrew (build 17.0.18+0)
OpenJDK 64-Bit Server VM Homebrew (build 17.0.18+0, mixed mode, sharing)
```

---

# 3 Install IntelliJ IDEA

Installs IDE

```bash
brew install --cask intellij-idea-ce
```

---

# 4 Install Node.js

### Install Node

Installs Node.js
```bash
brew install node
```

### Check Node

Shows version

```bash
node -v
```

### Check npm
Shows version

```bash
npm -v
```

---

# 5 Install Appium

### Install Appium

```bash
npm install -g appium
```

### Verify Appium

Confirms installation

```bash
appium -v
```

```bash
ovidio@192 ~ % appium -v
3.2.2
```

---

## Install Drivers

### iOS driver

Enables iOS automation

```bash
appium driver install xcuitest
```

### Android driver

Enables Android automation

```bash
appium driver install uiautomator2
```

### Verify drivers

Shows installed drivers

```bash
appium driver list
```

**Example**

```
ovidio@192 ~ % appium driver list
✔ Listing available drivers (rerun with --verbose for more info)
- xcuitest@10.43.0 [installed (npm)]
- uiautomator2@7.1.0 [installed (npm)]
```

---

# 6 Install Xcode

### Select Xcode

```bash
sudo xcode-select --switch /Applications/Xcode.app/Contents/Developer
```


### Accept license
```bash
sudo xcodebuild -license accept
```

### Verify path
Confirms path
```bash
xcode-select -p
```

**Example**
```
ovidio@192 ~ % xcode-select -p
/Applications/Xcode.app/Contents/Developer
```

---

# 7️⃣ iOS Simulator

### List devices

Shows simulators

```bash
xcrun simctl list devices
```

**Example**

```
ovidio@192 ~ % xcrun simctl list devices
== Devices ==
-- iOS 26.4 --
    iPhone 17 Pro (8E0F9553-DBF9-4755-A9E2-71D600BF674C) (Shutdown)
    iPhone 17 Pro Max (3C7484CE-8899-4BAD-8DD8-431F6A86B598) (Shutdown)
    iPhone 17e (1FDEA890-DB25-4607-888A-FCB1C14A6EAB) (Shutdown)
    iPhone Air (4A616FFC-30AB-4C60-BBC8-EA52359D0846) (Shutdown)
    iPhone 17 (DE1A7F71-4647-4DED-BFC1-2E35E53A7FED) (Shutdown)
    iPad Pro 13-inch (M5) (8CF5D6A0-0757-4275-B766-418A8D4F3CE0) (Shutdown)
    iPad Pro 11-inch (M5) (166D4486-CE00-4AA8-BA0A-F8E2CD8607EF) (Shutdown)
    iPad mini (A17 Pro) (324F64D4-459B-4A8E-A52A-C24546B10E59) (Shutdown)
    iPad Air 13-inch (M4) (15BE1352-6736-4A83-8AD7-8826897CE3C5) (Shutdown)
    iPad Air 11-inch (M4) (0506695E-687F-4B47-8300-CE9EC757D5C0) (Shutdown)
    iPad (A16) (96A541B6-07BD-435B-931E-156903F2F1E8) (Shutdown)
```


### Open simulator

Launches simulator
```bash
open -a Simulator
```

---

# 8 Appium Doctor

### Install tool
```bash
npm install -g appium-doctor
```

### Run check
Validates setup
```bash
appium-doctor --ios
```

---

# 9 Fix Dependencies

### Install Carthage
Required dependency
```bash
brew install carthage
```

### Verify Carthage
Confirms installation
```bash
carthage version
```

**Example**
```
ovidio@192 ~ % carthage version
0.40.0
```

### Install applesimutils
Improves simulator control
```bash
brew install applesimutils
```

---

# 10 SSH Setup

### Generate key
```bash
ssh-keygen -t ed25519 -C "your_email@example.com"
```

### Start agent
```bash
eval "$(ssh-agent -s)"
```

### Add key
Registers key
```bash
ssh-add --apple-use-keychain ~/.ssh/id_ed25519
```

### Copy key
```bash
pbcopy < ~/.ssh/id_ed25519.pub
```

### GitHub
Paste the key
https://github.com/settings/keys

**Test Connection**
```
ssh -T git@github.com
```
Yes

---

# 12 Appium Inspector

### Install
```bash
brew install --cask appium-inspector
```

### Open
Launches tool

```bash
open -a "Appium Inspector"
```
