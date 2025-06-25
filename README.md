# Trust Wallet Mobile Automation Framework

A comprehensive mobile automation framework for Trust Wallet using Appium, TestNG, and Allure reporting with industry best practices.

## Quick Start

```bash
# Clone and setup
git clone https://github.com/khawjaahmad/assessment-appium
cd assessment-appium

# Validate environment
./validate-env.sh

# Run all tests with report
./run-tests.sh --serve
```

## Table of Contents

- [Features](#features)
- [Framework Architecture](#framework-architecture)
- [Prerequisites](#prerequisites)
- [Quick Setup](#quick-setup)
- [Running Tests](#running-tests)
- [Test Structure](#test-structure)
- [Configuration](#configuration)
- [Reporting](#reporting)
- [Contributing](#contributing)

## Features

### Core Capabilities
- **Cross-platform Support**: Android automation (iOS ready)
- **Page Object Model**: Clean, maintainable page objects
- **Smart Configuration**: YAML and JSON-based configuration management
- **Comprehensive Reporting**: Allure reports with screenshots and steps
- **Parallel Execution**: Support for parallel test execution
- **Error Handling**: Robust error handling with automatic screenshots

### Advanced Features
- **Dynamic Element Location**: Smart locator strategies for challenging elements
- **Test Data Management**: External JSON-based test data
- **Performance Monitoring**: Built-in performance metrics tracking
- **CI/CD Ready**: Maven-based execution for pipeline integration
- **Environment Validation**: Automated environment setup verification

## Framework Architecture

```
src/
├── main/java/
│   ├── data/                    # Test data management
│   │   └── TestDataManager.java
│   ├── pages/                   # Page Object Model
│   │   ├── BasePage.java
│   │   ├── SplashPage.java
│   │   ├── PasscodePage.java
│   │   ├── NotificationPage.java
│   │   ├── WalletReadyPage.java
│   │   └── DashboardPage.java
│   └── utils/                   # Utility classes
│       ├── DriverUtils.java
│       ├── WaitUtils.java
│       ├── ScreenshotUtils.java
│       ├── LogUtils.java
│       ├── AssertionUtils.java
│       └── ConfigManager.java
├── test/java/
│   └── tests/                   # Test implementation
│       ├── BaseTest.java
│       ├── SimpleWalletTest.java
│       ├── EdgeCaseTest.java
│       └── DiagnosticTest.java
├── main/resources/
│   ├── device-config.yaml       # Device configurations
│   └── test-data.json          # Test data
├── test/resources/
│   └── apps/android/           # APK storage
└── testng.xml                  # TestNG configuration
```

### Design Patterns Used
- **Singleton Pattern**: ConfigManager, TestDataManager
- **Factory Pattern**: Driver creation for different platforms  
- **Page Object Pattern**: Clean separation of page logic and test logic
- **Builder Pattern**: Fluent assertion interfaces

## Test Coverage

### Automated Test Suites

#### Simple Wallet Flow (`SimpleWalletTest.java`)
- **Splash Screen Verification**: App launch and initial UI
- **Passcode Creation Flow**: Complete passcode setup
- **Notification Handling**: Permission management
- **Wallet Ready Page**: Success confirmation
- **Dashboard Verification**: Main interface validation

#### Edge Cases (`EdgeCaseTest.java`)  
- **Offline Functionality**: No internet connectivity scenarios
- **Invalid Input Handling**: Short passcodes, validation bypass
- **Performance Testing**: Rapid input, timeout scenarios
- **Error Recovery**: Passcode mismatch, navigation edge cases

#### Diagnostics (`DiagnosticTest.java`)
- **Element Discovery**: Dynamic element identification
- **Page Source Analysis**: Framework debugging tools
- **Performance Monitoring**: Execution time tracking

### Manual Test Cases
Comprehensive manual testing documentation covering:
- Happy path scenarios
- Negative test cases  
- Edge cases and error conditions
- Business impact assessment
- Detailed step-by-step execution guides

## Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Language** | Java | 17+ |
| **Build Tool** | Maven | 3.6+ |
| **Automation** | Appium | 2.0+ |
| **Test Framework** | TestNG | 7.8.0 |
| **WebDriver** | Selenium | 4.15.0 |
| **Reporting** | Allure | 2.24.0 |
| **Configuration** | SnakeYAML | 2.2 |
| **Logging** | Log4j2 | 2.20.0 |

## Key Capabilities

### Smart Element Location
Handles challenging scenarios like PIN/passcode screens:
```java
// Dynamic digit location using text content
By digitLocator = By.xpath("//android.widget.TextView[@text='" + digit + "']");
```

### Comprehensive Assertions
Automatic screenshot capture on assertion failures:
```java
@Step("Assert that condition is true: {message}")
public void assertTrue(boolean condition, String message) {
    try {
        Assert.assertTrue(condition, message);
        LogUtils.info("Assertion passed: " + message);
    } catch (AssertionError e) {
        ScreenshotUtils.takeScreenshot("assertion_failure");
        throw e;
    }
}
```

### External Test Data Management
JSON-based configuration for maintainable tests:
```json
{
  "passcodes": {
    "valid": "123456",
    "short": "123"
  },
  "timeouts": {
    "short": 5,
    "medium": 10,
    "long": 30
  }
}
```

## Reporting

### Allure Reports
- **Step-by-step execution**: Detailed test flow documentation
- **Screenshot attachments**: Visual verification of failures
- **Performance metrics**: Execution time tracking
- **Test categorization**: Feature-based organization

### Console Logging  
- **Color-coded output**: Easy visual scanning
- **Performance tracking**: Real-time execution metrics
- **Debug information**: Comprehensive troubleshooting data

## Configuration Management

### Device Configuration (`device-config.yaml`)
```yaml
android:
  capabilities:
    platformName: Android
    platformVersion: "13"
    deviceName: "emulator-5554"
    automationName: UiAutomator2
  server:
    url: "http://127.0.0.1:4725/wd/hub"
  timeouts:
    implicit: 15
    explicit: 30
```

### Test Data (`test-data.json`)
```json
{
  "passcodes": {
    "valid": "123456",
    "short": "123"
  },
  "timeouts": {
    "pageLoad": 60,
    "appLaunch": 3
  }
}
```

## Troubleshooting

### Common Issues

**Appium Connection**
```bash
# Check Appium server status
curl -s http://127.0.0.1:4725/wd/hub/status

# Restart Appium server
appium server -p 4725 -a 127.0.0.1 -pa /wd/hub
```

**Device Connection**
```bash
# List connected devices
adb devices

# Check device authorization
adb shell getprop ro.debuggable
```

**APK Installation**
- Verify APK exists at `src/apps/android/androidAPP.apk`
- Check APK compatibility with target device
- Ensure sufficient device storage space

### Debug Mode
```bash
# Run with verbose logging
mvn clean test -Dtestng.show.stack.frames=true

# Run specific test for debugging
mvn test -Dtest=DiagnosticTest#diagnosePageStructure
```

## Contributing

### Code Standards
- Follow Page Object Model patterns
- Add comprehensive assertions to all tests
- Include Allure annotations for reporting
- Update test data files for new scenarios
- Maintain consistent naming conventions

### Pull Request Process
1. Create feature branch from main
2. Implement changes with tests
3. Run full test suite locally
4. Update documentation as needed
5. Submit PR with clear description

### Testing Guidelines
- All new features require corresponding test coverage
- Edge cases and error scenarios must be included
- Performance impact should be considered
- Manual test cases should be updated for UI changes

## Performance Metrics

### Execution Times (Typical)
- **Full Test Suite**: 15-20 minutes
- **Smoke Tests**: 5-8 minutes  
- **Single Test**: 2-3 minutes
- **Framework Initialization**: 10-15 seconds

### Scalability
- **Parallel Execution**: Up to 3 concurrent threads
- **Device Support**: Multiple Android versions
- **CI/CD Integration**: Maven-based pipeline ready
- **Report Generation**: Under 30 seconds

## License

This project is created for assessment purposes and demonstrates production-ready mobile automation capabilities.

## Support

For issues and questions:
1. Check [Troubleshooting](#troubleshooting) section
2. Review test execution logs
3. Validate environment setup
4. Check Appium server connectivity

---

**Made with dedication for comprehensive mobile testing**
