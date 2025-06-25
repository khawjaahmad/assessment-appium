package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Enhanced screenshot utility with Allure integration and intelligent capture
 */
public class ScreenshotUtils {
    private static final ConfigManager configManager = ConfigManager.getInstance();
    private static final String SCREENSHOT_DIR;
    
    static {
        SCREENSHOT_DIR = (String) configManager.getScreenshotConfig("android").get("path");
        createScreenshotDirectory();
    }

    /**
     * Take screenshot with test name (called automatically on failures)
     */
    public static void takeScreenshot(String testName) {
        if (!isScreenshotEnabled()) {
            LogUtils.debug("Screenshots disabled, skipping capture");
            return;
        }

        try {
            long startTime = System.currentTimeMillis();
            
            WebDriver driver = DriverUtils.getDriver();
            if (!(driver instanceof TakesScreenshot)) {
                LogUtils.warn("Driver does not support screenshots");
                return;
            }

            byte[] screenshot = captureScreenshotAsBytes(driver);
            String filename = generateFilename(testName);
            Path screenshotPath = saveScreenshotToFile(screenshot, filename);
            
            attachToAllure(screenshot, testName);
            
            long duration = System.currentTimeMillis() - startTime;
            LogUtils.performance("Screenshot capture", duration);
            LogUtils.info("Screenshot saved: " + screenshotPath.toAbsolutePath());
            
        } catch (Exception e) {
            LogUtils.error("Failed to take screenshot for test: " + testName, e);
        }
    }

    /**
     * Take screenshot with custom name and description
     */
    public static void takeScreenshot(String name, String description) {
        if (!isScreenshotEnabled()) {
            return;
        }

        try {
            WebDriver driver = DriverUtils.getDriver();
            if (!(driver instanceof TakesScreenshot)) {
                LogUtils.warn("Driver does not support screenshots");
                return;
            }

            byte[] screenshot = captureScreenshotAsBytes(driver);
            String filename = generateFilename(name);
            saveScreenshotToFile(screenshot, filename);
            
            attachToAllure(screenshot, description);
            
            LogUtils.info("Custom screenshot taken: " + description);
            
        } catch (Exception e) {
            LogUtils.error("Failed to take custom screenshot: " + name, e);
        }
    }

    /**
     * Take screenshot for debugging purposes
     */
    public static void takeDebugScreenshot(String debugContext) {
        String name = "debug_" + debugContext.toLowerCase().replace(" ", "_");
        String description = "Debug Screenshot: " + debugContext;
        takeScreenshot(name, description);
    }

    /**
     * Capture screenshot as byte array
     */
    @Attachment(value = "Screenshot: {testName}", type = "image/png")
    public static byte[] captureScreenshotAsBytes(String testName) {
        try {
            WebDriver driver = DriverUtils.getDriver();
            if (driver instanceof TakesScreenshot) {
                return captureScreenshotAsBytes(driver);
            }
        } catch (Exception e) {
            LogUtils.error("Failed to capture screenshot bytes for: " + testName, e);
        }
        return new byte[0];
    }

    private static byte[] captureScreenshotAsBytes(WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        return ts.getScreenshotAs(OutputType.BYTES);
    }

    private static String generateFilename(String baseName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        String sanitizedName = baseName.replaceAll("[^a-zA-Z0-9_-]", "_");
        return sanitizedName + "_" + timestamp + ".png";
    }

    private static Path saveScreenshotToFile(byte[] screenshot, String filename) throws IOException {
        Path screenshotPath = Paths.get(SCREENSHOT_DIR, filename);
        Files.write(screenshotPath, screenshot);
        return screenshotPath;
    }

    private static void attachToAllure(byte[] screenshot, String description) {
        try {
            Allure.addAttachment(description, "image/png", 
                new java.io.ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            LogUtils.warn("Failed to attach screenshot to Allure: " + e.getMessage());
        }
    }

    private static boolean isScreenshotEnabled() {
        try {
            return (Boolean) configManager.getScreenshotConfig("android").get("onFailure");
        } catch (Exception e) {
            LogUtils.warn("Failed to read screenshot configuration, defaulting to enabled");
            return true;
        }
    }

    private static void createScreenshotDirectory() {
        try {
            Path directory = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
                LogUtils.info("Created screenshot directory: " + directory.toAbsolutePath());
            }
        } catch (IOException e) {
            LogUtils.error("Failed to create screenshot directory: " + SCREENSHOT_DIR, e);
        }
    }

    /**
     * Clean up old screenshots (keep only last N screenshots)
     */
    public static void cleanupOldScreenshots(int keepLastN) {
        try {
            Path directory = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(directory)) {
                return;
            }

            Files.list(directory)
                .filter(path -> path.toString().endsWith(".png"))
                .sorted((p1, p2) -> {
                    try {
                        return Files.getLastModifiedTime(p2).compareTo(Files.getLastModifiedTime(p1));
                    } catch (IOException e) {
                        return 0;
                    }
                })
                .skip(keepLastN)
                .forEach(path -> {
                    try {
                        Files.delete(path);
                        LogUtils.debug("Deleted old screenshot: " + path.getFileName());
                    } catch (IOException e) {
                        LogUtils.warn("Failed to delete old screenshot: " + path.getFileName());
                    }
                });

        } catch (IOException e) {
            LogUtils.error("Failed to cleanup old screenshots", e);
        }
    }

    /**
     * Get screenshot directory path
     */
    public static String getScreenshotDirectory() {
        return SCREENSHOT_DIR;
    }

    /**
     * Check if screenshots are configured and working
     */
    public static boolean isScreenshotCapable() {
        try {
            WebDriver driver = DriverUtils.getDriver();
            return driver instanceof TakesScreenshot;
        } catch (Exception e) {
            return false;
        }
    }
}