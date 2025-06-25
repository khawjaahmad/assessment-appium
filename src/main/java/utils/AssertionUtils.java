package utils;

import io.qameta.allure.Step;
import org.testng.Assert;

/**
 * Utility class for assertions with Allure reporting integration
 * This centralizes all assertion logic and provides better error reporting
 */
public class AssertionUtils {
    
    @Step("Assert that condition is true: {message}")
    public void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            LogUtils.info("Assertion passed: " + message);
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message);
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that condition is false: {message}")
    public void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            LogUtils.info("Assertion passed: " + message);
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message);
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that '{actual}' equals '{expected}': {message}")
    public void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            LogUtils.info("Assertion passed: " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that '{actual}' does not equal '{expected}': {message}")
    public void assertNotEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertNotEquals(actual, expected, message);
            LogUtils.info("Assertion passed: " + message + " (Expected not: " + expected + ", Actual: " + actual + ")");
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message + " (Expected not: " + expected + ", Actual: " + actual + ")");
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that object is not null: {message}")
    public void assertNotNull(Object object, String message) {
        try {
            Assert.assertNotNull(object, message);
            LogUtils.info("Assertion passed: " + message);
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message);
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that object is null: {message}")
    public void assertNull(Object object, String message) {
        try {
            Assert.assertNull(object, message);
            LogUtils.info("Assertion passed: " + message);
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message);
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Assert that text '{actual}' contains '{expected}': {message}")
    public void assertContains(String actual, String expected, String message) {
        try {
            Assert.assertTrue(actual.contains(expected), 
                message + " (Expected to contain: " + expected + ", Actual: " + actual + ")");
            LogUtils.info("Assertion passed: " + message + " (Text contains: " + expected + ")");
        } catch (AssertionError e) {
            LogUtils.error("Assertion failed: " + message + " (Expected to contain: " + expected + ", Actual: " + actual + ")");
            ScreenshotUtils.takeScreenshot("assertion_failure");
            throw e;
        }
    }
    
    @Step("Soft assert that condition is true: {message}")
    public void softAssertTrue(boolean condition, String message) {
        if (condition) {
            LogUtils.info("Soft assertion passed: " + message);
        } else {
            LogUtils.warn("Soft assertion failed: " + message);
        }
    }
    
    @Step("Soft assert that '{actual}' equals '{expected}': {message}")
    public void softAssertEquals(Object actual, Object expected, String message) {
        if (actual != null && actual.equals(expected)) {
            LogUtils.info("Soft assertion passed: " + message);
        } else {
            LogUtils.warn("Soft assertion failed: " + message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }
    
    /**
     * Performs a verification without stopping test execution
     * Returns true if verification passes, false otherwise
     */
    public boolean verify(boolean condition, String message) {
        if (condition) {
            LogUtils.info("Verification passed: " + message);
            return true;
        } else {
            LogUtils.warn("Verification failed: " + message);
            return false;
        }
    }
    
    /**
     * Performs element existence verification
     */
    public boolean verifyElementExists(boolean elementExists, String elementName) {
        return verify(elementExists, elementName + " should be displayed");
    }
    
    /**
     * Performs text verification
     */
    public boolean verifyText(String actual, String expected, String elementName) {
        boolean result = actual != null && actual.equals(expected);
        if (result) {
            LogUtils.info("Text verification passed for " + elementName + ": " + actual);
        } else {
            LogUtils.warn("Text verification failed for " + elementName + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
        return result;
    }
}