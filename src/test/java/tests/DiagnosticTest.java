package tests;

import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import pages.WalletReadyPage;
import pages.DashboardPage;
import utils.LogUtils;

import java.util.List;

public class DiagnosticTest extends BaseTest {
    
    @Test
    @Description("Diagnose wallet ready page elements")
    public void diagnoseWalletReadyPage() {
        LogUtils.testStart("diagnoseWalletReadyPage");
        
        navigateToWalletReadyPage();
        
        WalletReadyPage walletReadyPage = getWalletReadyPage();
        
        performWalletReadyPageDiagnostics(walletReadyPage);
        
        LogUtils.testEnd("diagnoseWalletReadyPage", true);
    }

    @Test
    @Description("Diagnose dashboard element IDs and structure")
    public void testDashboardElementIds() {
        LogUtils.testStart("testDashboardElementIds");
        
        navigateToDashboard();
        
        DashboardPage dashboardPage = getDashboardPage();
        dashboardPage.verifyPageIsLoaded();
        
        performDashboardDiagnostics();
        
        LogUtils.testEnd("testDashboardElementIds", true);
    }

    @Test
    @Description("Diagnose page source and element structure")
    public void diagnosePageStructure() {
        LogUtils.testStart("diagnosePageStructure");
        
        navigateToDashboard();
        
        performPageSourceAnalysis();
        
        LogUtils.testEnd("diagnosePageStructure", true);
    }

    private void performWalletReadyPageDiagnostics(WalletReadyPage walletReadyPage) {
        LogUtils.step("=== DIAGNOSTIC: Checking wallet ready page elements ===");
        
        try {
            Thread.sleep(5000);
            
            By[] possibleTitleLocators = {
                By.xpath("//android.widget.TextView[@text='Brilliant, your wallet is ready!']"),
                By.xpath("//*[contains(@text, 'Brilliant')]"),
                By.xpath("//*[contains(@text, 'wallet is ready')]"),
                By.xpath("//android.widget.TextView[contains(@text, 'ready')]")
            };
            
            LogUtils.info("Testing different title locators:");
            for (int i = 0; i < possibleTitleLocators.length; i++) {
                try {
                    boolean found = getDriver().findElement(possibleTitleLocators[i]).isDisplayed();
                    LogUtils.info("Title locator " + (i+1) + " found: " + found);
                    if (found) {
                        String text = getDriver().findElement(possibleTitleLocators[i]).getText();
                        LogUtils.info("Text found: " + text);
                    }
                } catch (Exception e) {
                    LogUtils.debug("Title locator " + (i+1) + " not found: " + e.getMessage());
                }
            }
            
            analyzeTextViewElements();
            
            analyzePageSource();
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtils.error("Diagnostic interrupted", e);
        }
    }

    private void performDashboardDiagnostics() {
        LogUtils.step("=== Dashboard Element IDs Diagnostic ===");
        
        try {
            Thread.sleep(3000); 
            
            List<WebElement> elementsWithId = getDriver().findElements(By.xpath("//*[@resource-id]"));
            
            LogUtils.info("Found " + elementsWithId.size() + " elements with resource IDs:");
            for (WebElement element : elementsWithId) {
                logElementDetails(element);
            }
            
            analyzeTextViewElements();
            
        } catch (Exception e) {
            LogUtils.error("Error during dashboard diagnostic", e);
        }
    }

    private void performPageSourceAnalysis() {
        LogUtils.step("=== Page Source Analysis ===");
        
        try {
            String pageSource = getDriver().getPageSource();
            
            if (pageSource.contains("Brilliant") || pageSource.contains("ready")) {
                LogUtils.info("Page source contains 'Brilliant' or 'ready' - wallet ready page elements present");
            } else {
                LogUtils.warn("Page source does not contain expected wallet ready text");
            }
            
            if (pageSource.contains("resource-id")) {
                LogUtils.info("Page source contains resource-id attributes");
            } else {
                LogUtils.warn("Page source may not contain resource-id attributes");
            }
            
            LogUtils.info("Page source length: " + pageSource.length() + " characters");
            
        } catch (Exception e) {
            LogUtils.error("Error analyzing page source", e);
        }
    }

    private void analyzePageSource() {
        try {
            String pageSource = getDriver().getPageSource();
            
            LogUtils.info("=== Page Source Analysis ===");
            
            if (pageSource.contains("Brilliant") || pageSource.contains("ready")) {
                LogUtils.info("Page source contains 'Brilliant' or 'ready' - wallet ready page elements present");
            } else {
                LogUtils.warn("Page source does not contain expected wallet ready text");
            }
            
            if (pageSource.contains("resource-id")) {
                LogUtils.info("Page source contains resource-id attributes");
            } else {
                LogUtils.warn("Page source may not contain resource-id attributes");
            }
            
            
            LogUtils.info("Page source length: " + pageSource.length() + " characters");
            
        } catch (Exception e) {
            LogUtils.error("Error analyzing page source", e);
        }
    }

    private void analyzeTextViewElements() {
        try {
            List<WebElement> textViews = getDriver().findElements(By.className("android.widget.TextView"));
            LogUtils.info("Total TextView elements found: " + textViews.size());
            
            int displayCount = 0;
            for (int i = 0; i < Math.min(textViews.size(), 10); i++) {
                try {
                    String text = textViews.get(i).getText();
                    if (text != null && !text.trim().isEmpty()) {
                        LogUtils.info("TextView " + i + ": " + text);
                        displayCount++;
                    }
                } catch (Exception e) {
                    LogUtils.debug("Could not get text from TextView " + i);
                }
            }
            
            if (displayCount == 0) {
                LogUtils.warn("No TextView elements with visible text found");
            }
            
        } catch (Exception e) {
            LogUtils.error("Error analyzing TextView elements", e);
        }
    }

    private void logElementDetails(WebElement element) {
        try {
            String resourceId = element.getAttribute("resource-id");
            String className = element.getAttribute("className");
            String text = element.getText();
            String bounds = element.getAttribute("bounds");
            
            LogUtils.debug("Resource ID: " + resourceId);
            LogUtils.debug("  Class: " + className);
            LogUtils.debug("  Text: " + text);
            LogUtils.debug("  Bounds: " + bounds);
            LogUtils.debug("---");
            
        } catch (Exception e) {
            LogUtils.debug("Could not get details for element: " + e.getMessage());
        }
    }
}