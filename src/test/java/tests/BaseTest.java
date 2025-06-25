package tests;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.ConfigManager;
import utils.DriverUtils;
import utils.LogUtils;
import utils.ScreenshotUtils;
import data.TestDataManager;
import pages.SplashPage;
import pages.PasscodePage;
import pages.NotificationPage;
import pages.WalletReadyPage;
import pages.DashboardPage;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    protected ConfigManager config;
    protected TestDataManager testData;
    
    @BeforeSuite
    public void setUpSuite() {
        LogUtils.info("=== TEST SUITE SETUP STARTED ===");
        
        config = ConfigManager.getInstance();
        testData = TestDataManager.getInstance();
        
        logTestConfiguration();
        
        ScreenshotUtils.cleanupOldScreenshots(50);
        
        LogUtils.info("=== TEST SUITE SETUP COMPLETED ===");
    }
    
    @BeforeMethod
    public void setUp(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        LogUtils.config("Starting test setup for", testName);
        
        if (config == null) {
            config = ConfigManager.getInstance();
        }
        if (testData == null) {
            testData = TestDataManager.getInstance();
        }
        
        String platform = System.getProperty("platform", "android");
        LogUtils.config("Platform", platform);
        
        long setupStart = System.currentTimeMillis();
        DriverUtils.initializeDriver(platform);
        waitForAppToLoad();
        long setupTime = System.currentTimeMillis() - setupStart;
        
        LogUtils.performance("Test setup", setupTime);
        LogUtils.info("Test environment ready for: " + testName);
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        boolean testPassed = result.isSuccess();
        
        LogUtils.config("Starting test teardown for", testName);
        
        if (!testPassed) {
            handleTestFailure(result);
        }
        
        if (DriverUtils.isDriverInitialized()) {
            DriverUtils.quitDriver();
            LogUtils.info("Driver cleaned up successfully");
        }
        
        LogUtils.config("Test teardown completed for", testName);
    }
    
    @AfterSuite
    public void tearDownSuite() {
        LogUtils.info("=== TEST SUITE TEARDOWN STARTED ===");
        
        cleanupResources();
        
        LogUtils.info("=== TEST SUITE TEARDOWN COMPLETED ===");
    }
    
    protected AppiumDriver getDriver() {
        return DriverUtils.getDriver();
    }
    
    protected SplashPage getSplashPage() {
        return new SplashPage(getDriver());
    }
    
    protected PasscodePage getPasscodePage() {
        return new PasscodePage(getDriver());
    }
    
    protected NotificationPage getNotificationPage() {
        return new NotificationPage(getDriver());
    }
    
    protected WalletReadyPage getWalletReadyPage() {
        return new WalletReadyPage(getDriver());
    }
    
    protected DashboardPage getDashboardPage() {
        return new DashboardPage(getDriver());
    }
    
    protected void navigateToPasscodePage() {
        LogUtils.step("Navigating to Passcode page");
        SplashPage splashPage = getSplashPage();
        splashPage.verifyPageIsLoaded().clickCreateNewWallet();
        LogUtils.pageNavigation("Splash Page", "Passcode Page");
    }
    
    protected void createPasscode(String passcode) {
        LogUtils.step("Creating passcode");
        PasscodePage passcodePage = getPasscodePage();
        passcodePage.createPasscodeFlow(passcode);
        LogUtils.action("Passcode creation completed");
    }
    
    protected void navigateToNotificationPage() {
        LogUtils.step("Navigating to Notification page");
        navigateToPasscodePage();
        createPasscode(testData.getValidPasscode());
        LogUtils.pageNavigation("Passcode Page", "Notification Page");
    }
    
    protected void enableNotifications() {
        LogUtils.step("Enabling notifications");
        NotificationPage notificationPage = getNotificationPage();
        notificationPage.verifyPageIsLoaded().clickEnableNotifications();
        LogUtils.action("Notifications enabled");
    }
    
    protected void navigateToWalletReadyPage() {
        LogUtils.step("Navigating to Wallet Ready page");
        navigateToNotificationPage();
        enableNotifications();
        LogUtils.pageNavigation("Notification Page", "Wallet Ready Page");
    }
    
    protected void navigateToDashboard() {
        LogUtils.step("Navigating to Dashboard");
        navigateToWalletReadyPage();
        WalletReadyPage walletReadyPage = getWalletReadyPage();
        walletReadyPage.verifyPageIsLoaded().clickSkip();
        LogUtils.pageNavigation("Wallet Ready Page", "Dashboard");
    }
    
    protected void restartApp() {
        LogUtils.action("Restarting application");
        long restartStart = System.currentTimeMillis();
        
        DriverUtils.resetApp();
        waitForAppToLoad();
        
        long restartTime = System.currentTimeMillis() - restartStart;
        LogUtils.performance("App restart", restartTime);
    }
    
    protected void navigateBack() {
        LogUtils.action("Navigating back using device back button");
        DriverUtils.getDriver().navigate().back();
    }
    
    private void waitForAppToLoad() {
        LogUtils.info("Waiting for application to load");
        long appLoadTimeout = testData.getTimeout("appLaunch") * 1000L;
        
        try {
            Thread.sleep(appLoadTimeout);
            LogUtils.info("Application load wait completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtils.error("Application load wait interrupted", e);
        }
    }
    
    private void handleTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        LogUtils.error("Test failed: " + testName, throwable);
        
        if (config.getScreenshotConfig("android").get("onFailure").equals(true)) {
            try {
                ScreenshotUtils.takeScreenshot(testName + "_failure");
                LogUtils.info("Failure screenshot captured for: " + testName);
            } catch (Exception e) {
                LogUtils.warn("Failed to capture screenshot for test failure: " + e.getMessage());
            }
        }
        
        logTestFailureDetails(result);
    }
    
    private void logTestFailureDetails(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable throwable = result.getThrowable();
        
        LogUtils.error("=== TEST FAILURE DETAILS ===");
        LogUtils.error("Test: " + testName);
        LogUtils.error("Exception: " + (throwable != null ? throwable.getClass().getSimpleName() : "Unknown"));
        LogUtils.error("Message: " + (throwable != null ? throwable.getMessage() : "No message"));
        
        if (throwable != null && throwable.getCause() != null) {
            LogUtils.error("Root cause: " + throwable.getCause().getMessage());
        }
        
        try {
            if (DriverUtils.isDriverInitialized()) {
                LogUtils.debug("Driver is active, app state available for debugging");
            }
        } catch (Exception e) {
            LogUtils.debug("Could not determine current app state: " + e.getMessage());
        }
    }
    
    private void logTestConfiguration() {
        LogUtils.config("Test Data Manager initialized", "✓");
        LogUtils.config("Config Manager initialized", "✓");
        LogUtils.config("Screenshot directory", ScreenshotUtils.getScreenshotDirectory());
        LogUtils.config("Screenshot on failure", config.getScreenshotConfig("android").get("onFailure").toString());
        LogUtils.config("App launch timeout", testData.getTimeout("appLaunch") + " seconds");
        LogUtils.config("Default platform", System.getProperty("platform", "android"));
    }
    
    private void cleanupResources() {
        try {
            if (DriverUtils.isDriverInitialized()) {
                DriverUtils.quitDriver();
                LogUtils.info("Final driver cleanup completed");
            }
        
            LogUtils.info("All test resources cleaned up");
            
        } catch (Exception e) {
            LogUtils.warn("Error during resource cleanup: " + e.getMessage());
        }
    }
    
    @Attachment(value = "Test Failure Screenshot", type = "image/png")
    public byte[] captureFailureScreenshot() {
        try {
            if (DriverUtils.isDriverInitialized()) {
                File screenshot = DriverUtils.getDriver().getScreenshotAs(OutputType.FILE);
                return FileUtils.readFileToByteArray(screenshot);
            }
        } catch (IOException e) {
            LogUtils.error("Failed to capture failure screenshot", e);
        }
        return new byte[0];
    }
}