package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import data.TestDataManager;

import java.time.Duration;
import java.util.List;

public class WaitUtils {
    private static final TestDataManager testData = TestDataManager.getInstance();
    
    public static WebElement waitForElementToBeVisible(By locator) {
        return waitForElementToBeVisible(locator, testData.getTimeout("medium"));
    }
    
    public static WebElement waitForElementToBeVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public static WebElement waitForElementToBeClickable(By locator) {
        return waitForElementToBeClickable(locator, testData.getTimeout("medium"));
    }
    
    public static WebElement waitForElementToBeClickable(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public static boolean waitForElementToDisappear(By locator) {
        return waitForElementToDisappear(locator, testData.getTimeout("medium"));
    }
    
    public static boolean waitForElementToDisappear(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public static WebElement waitForElementWithText(By locator, String expectedText) {
        return waitForElementWithText(locator, expectedText, testData.getTimeout("medium"));
    }
    
    public static WebElement waitForElementWithText(By locator, String expectedText, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText)) ?
                DriverUtils.getDriver().findElement(locator) : null;
    }
    
    public static List<WebElement> waitForElementsToBeVisible(By locator) {
        return waitForElementsToBeVisible(locator, testData.getTimeout("medium"));
    }
    
    public static List<WebElement> waitForElementsToBeVisible(By locator, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    public static boolean waitForTextToBePresent(By locator, String text) {
        return waitForTextToBePresent(locator, text, testData.getTimeout("medium"));
    }
    
    public static boolean waitForTextToBePresent(By locator, String text, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public static boolean isElementPresent(By locator) {
        return isElementPresent(locator, testData.getTimeout("short"));
    }
    
    public static boolean isElementPresent(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverUtils.getDriver(), Duration.ofSeconds(timeoutInSeconds));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public static void waitForPageToLoad() {
        waitForPageToLoad(testData.getTimeout("pageLoad"));
    }
    
    public static void waitForPageToLoad(int timeoutInSeconds) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
} 