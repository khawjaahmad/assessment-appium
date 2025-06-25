package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class DriverUtils {
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static final ConfigManager config = ConfigManager.getInstance();
    
    public static void initializeDriver(String platform) {
        try {
            AppiumDriver appiumDriver = createDriver(platform);
            setTimeouts(appiumDriver, platform);
            driver.set(appiumDriver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize driver for platform: " + platform, e);
        }
    }
    
    private static AppiumDriver createDriver(String platform) throws Exception {
        String serverUrl = config.getServerUrl(platform);
        Map<String, Object> capabilities = config.getDeviceCapabilities(platform);
        
        DesiredCapabilities caps = new DesiredCapabilities();
        capabilities.forEach(caps::setCapability);
        
        String appPath = config.getAppPath(platform);
        if (appPath != null && !appPath.isEmpty()) {
            caps.setCapability("app", System.getProperty("user.dir") + "/" + appPath);
        }
        
        switch (platform.toLowerCase()) {
            case "android":
                return new AndroidDriver(new URL(serverUrl), caps);
            case "ios":
                return new IOSDriver(new URL(serverUrl), caps);
            default:
                throw new IllegalArgumentException("Unsupported platform: " + platform);
        }
    }
    
    private static void setTimeouts(AppiumDriver driver, String platform) {
        Map<String, Object> timeouts = config.getTimeouts(platform);
        
        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(getTimeoutValue(timeouts, "implicit")));
    }
    
    private static int getTimeoutValue(Map<String, Object> timeouts, String key) {
        Object value = timeouts.get(key);
        return value instanceof Integer ? (Integer) value : Integer.parseInt(value.toString());
    }
    
    public static AppiumDriver getDriver() {
        AppiumDriver currentDriver = driver.get();
        if (currentDriver == null) {
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return currentDriver;
    }
    
    public static void quitDriver() {
        AppiumDriver currentDriver = driver.get();
        if (currentDriver != null) {
            currentDriver.quit();
            driver.remove();
        }
    }
    
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
    
    public static void resetApp() {
        AppiumDriver currentDriver = getDriver();
        String appPackage = config.getAppPackage("android");
        
        if (appPackage == null || appPackage.trim().isEmpty()) {
            throw new RuntimeException("App package not configured in device configuration");
        }
        
        if (currentDriver instanceof AndroidDriver) {
            try {
                ((AndroidDriver) currentDriver).terminateApp(appPackage);
                ((AndroidDriver) currentDriver).activateApp(appPackage);
            } catch (Exception e) {
                throw new RuntimeException("Failed to reset app: " + e.getMessage(), e);
            }
        } else if (currentDriver instanceof IOSDriver) {
            try {
                ((IOSDriver) currentDriver).terminateApp(appPackage);
                ((IOSDriver) currentDriver).activateApp(appPackage);
            } catch (Exception e) {
                throw new RuntimeException("Failed to reset app: " + e.getMessage(), e);
            }
        }
    }
}
