# Installation & Setup Guide - Trust Wallet Automation

Complete setup guide for the Trust Wallet mobile automation framework.

## Table of Contents

1. [System Requirements](#system-requirements)
2. [Prerequisites Installation](#prerequisites-installation)
3. [Project Setup](#project-setup)
4. [Device Configuration](#device-configuration)
5. [Verification](#verification)
6. [Troubleshooting](#troubleshooting)
7. [IDE Setup](#ide-setup)

---

## System Requirements

### Operating System
- **Windows 10/11** (64-bit)
- **macOS 10.15+** (Catalina or later)
- **Linux Ubuntu 18.04+** (or equivalent)

### Hardware Requirements
- **RAM**: Minimum 8GB, Recommended 16GB
- **Storage**: Minimum 10GB free space
- **CPU**: Multi-core processor recommended for parallel execution

---

## Prerequisites Installation

### 1. Java Development Kit (JDK)

**Required Version**: Java 17 or higher

#### Windows Installation
```bash
# Download from Oracle or use Chocolatey
choco install openjdk17

# Or download manually from:
# https://adoptium.net/temurin/releases/
```

#### macOS Installation
```bash
# Using Homebrew
brew install openjdk@17

# Add to PATH in ~/.zshrc or ~/.bash_profile
echo 'export PATH="/opt/homebrew/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
```

#### Linux Installation
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install openjdk-17-jdk

# CentOS/RHEL/Fedora
sudo yum install java-17-openjdk-devel
```

#### Verification
```bash
java -version
javac -version
```

Expected output:
```
openjdk version "17.x.x" 2023-xx-xx
OpenJDK Runtime Environment...
```

### 2. Apache Maven

**Required Version**: Maven 3.6+

#### Windows Installation
```bash
# Using Chocolatey
choco install maven

# Or download manually from:
# https://maven.apache.org/download.cgi
```

#### macOS Installation
```bash
# Using Homebrew
brew install maven
```

#### Linux Installation
```bash
# Ubuntu/Debian
sudo apt install maven

# CentOS/RHEL/Fedora  
sudo yum install maven
```

#### Verification
```bash
mvn -version
```

Expected output:
```
Apache Maven 3.x.x
Maven home: /path/to/maven
Java version: 17.x.x
```

### 3. Android SDK

#### Download Android Studio
- Download from: https://developer.android.com/studio
- Install with default settings
- Launch Android Studio and complete initial setup

#### Install SDK Components
1. Open Android Studio
2. Go to **Tools** → **SDK Manager**
3. Install the following:
   - **Android SDK Platform Tools**
   - **Android SDK Build Tools** (latest)
   - **Android API 29+** (Android 10+)
   - **Google USB Driver** (Windows only)

#### Set Environment Variables

**Windows:**
```bash
# Add to System Environment Variables
ANDROID_HOME=C:\Users\{username}\AppData\Local\Android\Sdk
ANDROID_SDK_ROOT=%ANDROID_HOME%

# Add to PATH
%ANDROID_HOME%\platform-tools
%ANDROID_HOME%\tools
```

**macOS/Linux:**
```bash
# Add to ~/.zshrc or ~/.bashrc
export ANDROID_HOME=$HOME/Library/Android/sdk  # macOS
export ANDROID_HOME=$HOME/Android/Sdk          # Linux
export ANDROID_SDK_ROOT=$ANDROID_HOME
export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
```

#### Verification
```bash
adb version
android list targets  # May not work on newer SDK versions
```

### 4. Node.js (for Appium)

**Required Version**: Node.js 16+

#### Installation
```bash
# Windows (using Chocolatey)
choco install nodejs

# macOS (using Homebrew)
brew install node

# Linux (Ubuntu/Debian)
curl -fsSL https://deb.nodesource.com/setup_18.x | sudo -E bash -
sudo apt-get install -y nodejs
```

#### Verification
```bash
node --version
npm --version
```

### 5. Appium Server

#### Installation
```bash
# Install globally using npm
npm install -g appium

# Install UiAutomator2 driver for Android
appium driver install uiautomator2
```

#### Verification
```bash
appium --version
appium driver list
```

Expected output should show UiAutomator2 driver installed.

### 6. Allure Command Line (Optional)

#### Installation
```bash
# Using npm
npm install -g allure-commandline

# Using Homebrew (macOS)
brew install allure

# Manual download from:
# https://github.com/allure-framework/allure2/releases
```

#### Verification
```bash
allure --version
```

---

## Device Configuration

### Android Emulator Setup

#### Create AVD (Android Virtual Device)
1. Open Android Studio
2. Go to **Tools** → **AVD Manager**
3. Click **Create Virtual Device**
4. Choose device definition (e.g., Pixel 4)
5. Select system image (API 29+ recommended)
6. Configure AVD settings:
   - **AVD Name**: `Test_Device_API_30`
   - **RAM**: 2GB minimum
   - **Internal Storage**: 4GB minimum
7. Click **Finish**

#### Launch Emulator
```bash
# List available AVDs
emulator -list-avds

# Launch specific AVD
emulator -avd Test_Device_API_30

# Or launch from Android Studio AVD Manager
```

### Physical Device Setup

#### Enable Developer Options
1. Go to **Settings** → **About Phone**
2. Tap **Build Number** 7 times
3. Return to **Settings** → **Developer Options**
4. Enable **USB Debugging**
5. Enable **Stay Awake** (recommended for testing)
6. Enable **Disable Permission Monitoring** (if available)

#### Connect Physical Device
```bash
# Connect device via USB
# Verify device connection
adb devices

# Expected output:
# List of devices attached
# ABC123XYZ    device
```

#### Device Authorization
- When connecting for the first time, authorize USB debugging on device
- Check "Always allow from this computer" checkbox

---

## Project Setup

### 1. Clone Repository
```bash
# Clone the project
git clone <your-repository-url>
cd trust-wallet-automation

# Or if starting from scratch
mkdir trust-wallet-automation
cd trust-wallet-automation
```

### 2. Download Trust Wallet APK
```bash
# Create directory structure
mkdir -p src/apps/android

# Download Trust Wallet APK from official source
# https://trustwallet.com/
# Save as: src/apps/android/androidAPP.apk
```

### 3. Install Project Dependencies
```bash
# Clean and install Maven dependencies
mvn clean install

# This will download all required dependencies:
# - Appium Java Client
# - TestNG
# - Selenium WebDriver
# - Allure TestNG
# - SnakeYAML
# - Jackson (JSON processing)
# - Log4j2
```

### 4. Configure Device Settings

#### Update Device Configuration
Edit `src/main/resources/device-config.yaml`:

**For Emulator:**
```yaml
android:
  capabilities:
    platformName: Android
    platformVersion: "13"          # Match your emulator API level
    deviceName: "emulator-5554"    # Default emulator port
    udid: "emulator-5554"          # Same as deviceName for emulator
    automationName: UiAutomator2
    noReset: false
    fullReset: false
    autoGrantPermissions: true
```

**For Physical Device:**
```yaml
android:
  capabilities:
    platformName: Android
    platformVersion: "13"          # Check: adb shell getprop ro.build.version.release
    deviceName: "MyPhysicalDevice" # Any descriptive name
    udid: "ABC123XYZ"              # From: adb devices
    automationName: UiAutomator2
    noReset: false
    fullReset: false
    autoGrantPermissions: true
```

#### Find Device Information
```bash
# Get device UDID
adb devices

# Get Android version
adb shell getprop ro.build.version.release

# Get device name
adb shell getprop ro.product.model
```

### 5. Verify APK Path
Ensure APK file exists and update path if needed:
```bash
# Check if APK exists
ls -la src/apps/android/androidAPP.apk

# If using different location, update device-config.yaml:
# app:
#   path: "your/custom/path/TrustWallet.apk"
```

---

## Verification

### 1. Environment Validation
```bash
# Run the built-in environment validator
chmod +x validate-env.sh
./validate-env.sh
```

Expected output:
```
java found
mvn found
appium found
adb found
Allure found (optional, for reports)
Maven POM: pom.xml
TestNG XML: src/testng.xml
Device Config: src/main/resources/device-config.yaml
Android APK: src/apps/android/androidAPP.apk

Environment validation PASSED
```

### 2. Start Appium Server
```bash
# Start Appium server (keep this terminal open)
appium server -p 4725 -a 127.0.0.1 -pa /wd/hub

# Expected output:
# [Appium] Welcome to Appium v2.x.x
# [Appium] Appium REST http interface listener started on 127.0.0.1:4725
# [Appium] Available drivers:
# [Appium]   - uiautomator2@x.x.x (automationName 'UiAutomator2')
```

### 3. Test Connection
```bash
# In a new terminal, test Appium connection
curl -s http://127.0.0.1:4725/wd/hub/status

# Expected JSON response with "ready": true
```

### 4. Run Test Suite
```bash
# Run quick verification test
mvn test -Dtest=SimpleWalletTest#testSplashScreenAppears

# If successful, run full test suite
./run-tests.sh
```

### 5. Verify Reports
```bash
# Check if reports are generated
ls -la target/allure-results/
ls -la target/screenshots/

# Generate and view Allure report
mvn allure:report
mvn allure:serve
```

---

## Troubleshooting

### Common Issues and Solutions

#### Issue: "ANDROID_HOME not set"
**Solution:**
```bash
# Verify environment variables
echo $ANDROID_HOME    # Should show SDK path
echo $PATH           # Should include platform-tools

# If not set, add to shell profile:
# ~/.bashrc, ~/.zshrc, or ~/.bash_profile
export ANDROID_HOME=/path/to/android/sdk
export PATH=$PATH:$ANDROID_HOME/platform-tools
```

#### Issue: "adb: command not found"
**Solution:**
```bash
# Find ADB location
find /path/to/android/sdk -name adb 2>/dev/null

# Add to PATH or create symlink
sudo ln -s /path/to/android/sdk/platform-tools/adb /usr/local/bin/adb
```

#### Issue: "Device unauthorized"
**Solution:**
```bash
# Reset ADB authorization
adb kill-server
adb start-server
adb devices

# On device: Accept USB debugging prompt
# Check "Always allow from this computer"
```

#### Issue: Appium server won't start
**Solution:**
```bash
# Check if port is in use
lsof -i :4725

# Kill existing process if needed
kill -9 <PID>

# Or use different port
appium server -p 4726 -a 127.0.0.1 -pa /wd/hub
# Update device-config.yaml server URL accordingly
```

#### Issue: "App installation failed"
**Solution:**
```bash
# Check APK file
file src/apps/android/androidAPP.apk

# Verify APK is valid
aapt dump badging src/apps/android/androidAPP.apk

# Check device storage
adb shell df

# Manual installation test
adb install src/apps/android/androidAPP.apk
```

#### Issue: Maven build failures
**Solution:**
```bash
# Clean Maven cache
mvn dependency:purge-local-repository

# Clean and reinstall
mvn clean install -U

# Check Java version compatibility
mvn -version
```

#### Issue: Tests fail to find elements
**Solution:**
```bash
# Run diagnostic test
mvn test -Dtest=DiagnosticTest

# Check device UI hierarchy
adb shell uiautomator dump
adb pull /sdcard/window_dump.xml

# Verify app is installed
adb shell pm list packages | grep trustwallet
```

### Performance Optimization

#### Improve Test Execution Speed
```yaml
# In device-config.yaml, add:
android:
  capabilities:
    skipServerInstallation: true      # Skip if UiAutomator2 already installed
    skipDeviceInitialization: true    # Skip device checks
    disableIdLocatorAutocompletion: true  # Faster element location
    ignoreUnimportantViews: true      # Reduce UI hierarchy complexity
```

#### Reduce Startup Time
```bash
# Pre-install UiAutomator2 server
appium driver install uiautomator2

# Keep emulator running between test sessions
# Use snapshot save/restore for faster emulator startup
```

### Debug Mode Setup

#### Enable Verbose Logging
```bash
# Run tests with debug output
mvn test -Dtestng.verbose=3 -Dtestng.show.stack.frames=true

# Enable Appium debug logs
appium server --log-level debug --log /tmp/appium.log
```

#### IDE Debug Configuration
- Set breakpoints in test code
- Run tests in debug mode from IDE
- Use step-through debugging for complex scenarios

---

## IDE Setup

### IntelliJ IDEA (Recommended)

#### Required Plugins
1. **Maven Integration** (usually pre-installed)
2. **TestNG** 
3. **Allure TestOps** (optional)

#### Project Import
1. Open IntelliJ IDEA
2. **File** → **Open** → Select project directory
3. Wait for Maven import to complete
4. **File** → **Project Structure** → **Project**
   - Set Project SDK: Java 17
   - Set Language Level: 17

#### Run Configuration
1. **Run** → **Edit Configurations**
2. **Add New** → **TestNG**
3. Configure:
   - **Test Kind**: Suite
   - **Suite**: `src/testng.xml`
   - **VM Options**: `-Dplatform=android`

### Visual Studio Code

#### Required Extensions
```bash
# Install extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension microsoft.vscode-maven
code --install-extension TestNG.vscode-testng
```

#### Configuration (.vscode/settings.json)
```json
{
    "java.home": "/path/to/java17",
    "maven.executable.path": "/path/to/maven/bin/mvn",
    "java.test.defaultConfig": "testng"
}
```

### Eclipse

#### Required Plugins
- **TestNG Plugin**: Install from Eclipse Marketplace
- **Maven Integration**: Usually pre-installed

#### Project Import
1. **File** → **Import** → **Existing Maven Projects**
2. Select project root directory
3. **Project** → **Properties** → **Java Build Path**
   - Set JRE to Java 17

---

## Post-Installation Checklist

### Verification Steps

- [ ] Java 17+ installed and configured
- [ ] Maven 3.6+ installed and working
- [ ] Android SDK installed with platform-tools
- [ ] ANDROID_HOME environment variable set
- [ ] ADB accessible from command line
- [ ] Node.js and Appium installed
- [ ] UiAutomator2 driver installed
- [ ] Trust Wallet APK downloaded
- [ ] Device/emulator connected and authorized
- [ ] Environment validation script passes
- [ ] Appium server starts successfully
- [ ] Sample test executes without errors
- [ ] Allure reports generate correctly

### Ready to Test!

If all checklist items are complete, you're ready to run the full test suite:

```bash
# Run complete test suite
./run-tests.sh --serve

# Expected: Browser opens with Allure report
# All tests should pass or have expected failures documented
```

### Support

If you encounter issues not covered in this guide:
1. Check the main README.md troubleshooting section
2. Review Appium documentation: https://appium.io/docs/
3. Verify Android SDK setup: https://developer.android.com/studio/
4. Check TestNG documentation: https://testng.org/doc/

---

**Installation complete! Ready to automate Trust Wallet testing.**