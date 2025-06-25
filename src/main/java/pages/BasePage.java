package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.DriverUtils;
import utils.WaitUtils;
import utils.AssertionUtils;
import data.TestDataManager;

import java.time.Duration;

public abstract class BasePage {
    protected AppiumDriver driver;
    protected TestDataManager testData;
    protected AssertionUtils assertions;
    
    protected BasePage() {
        this.driver = DriverUtils.getDriver();
        this.testData = TestDataManager.getInstance();
        this.assertions = new AssertionUtils();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }
    
    protected BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.testData = TestDataManager.getInstance();
        this.assertions = new AssertionUtils();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }
    
    protected void clickElement(WebElement element) {
        try {
            WaitUtils.waitForElementToBeClickable(getLocator(element)).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + element.toString(), e);
        }
    }
    
    protected void clickElement(By locator) {
        try {
            WaitUtils.waitForElementToBeClickable(locator).click();
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element with locator: " + locator.toString(), e);
        }
    }
    
    protected void enterText(WebElement element, String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        try {
            WebElement visibleElement = WaitUtils.waitForElementToBeVisible(getLocator(element));
            visibleElement.clear();
            visibleElement.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text '" + text + "' in element: " + element.toString(), e);
        }
    }
    
    protected void enterText(By locator, String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be null or empty");
        }
        
        try {
            WebElement element = WaitUtils.waitForElementToBeVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to enter text '" + text + "' with locator: " + locator.toString(), e);
        }
    }
    
    protected String getText(WebElement element) {
        try {
            return WaitUtils.waitForElementToBeVisible(getLocator(element)).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text from element: " + element.toString(), e);
        }
    }
    
    protected String getText(By locator) {
        try {
            return WaitUtils.waitForElementToBeVisible(locator).getText();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get text with locator: " + locator.toString(), e);
        }
    }
    
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return WaitUtils.isElementPresent(getLocator(element));
        } catch (Exception e) {
            return false;
        }
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            return WaitUtils.isElementPresent(locator);
        } catch (Exception e) {
            return false;
        }
    }
    
    protected void waitForElementToLoad(WebElement element) {
        WaitUtils.waitForElementToBeVisible(getLocator(element));
    }
    
    protected void waitForElementToLoad(By locator) {
        WaitUtils.waitForElementToBeVisible(locator);
    }
    
    protected boolean waitForTextToAppear(WebElement element, String expectedText) {
        return WaitUtils.waitForTextToBePresent(getLocator(element), expectedText);
    }
    
    protected boolean waitForTextToAppear(By locator, String expectedText) {
        return WaitUtils.waitForTextToBePresent(locator, expectedText);
    }
    
    public BasePage verifyPageIsLoaded() {
        assertions.assertTrue(isPageLoaded(), getPageName() + " page should be loaded");
        return this;
    }
    
    public BasePage verifyPageIsLoaded(String customMessage) {
        assertions.assertTrue(isPageLoaded(), customMessage);
        return this;
    }
    
    protected void verifyElementIsDisplayed(By locator, String elementName) {
        assertions.assertTrue(isElementDisplayed(locator), elementName + " should be displayed");
    }
    
    protected void verifyElementIsDisplayed(WebElement element, String elementName) {
        assertions.assertTrue(isElementDisplayed(element), elementName + " should be displayed");
    }
    
    protected void verifyElementText(By locator, String expectedText, String elementName) {
        String actualText = getText(locator);
        assertions.assertEquals(actualText, expectedText, elementName + " text should match expected value");
    }
    
    protected void verifyElementText(WebElement element, String expectedText, String elementName) {
        String actualText = getText(element);
        assertions.assertEquals(actualText, expectedText, elementName + " text should match expected value");
    }
    
    protected void verifyElementContainsText(By locator, String expectedText, String elementName) {
        String actualText = getText(locator);
        assertions.assertTrue(actualText.contains(expectedText), 
            elementName + " should contain '" + expectedText + "' but was '" + actualText + "'");
    }
    
    private By getLocator(WebElement element) {
        String elementString = element.toString();
        
        if (elementString.contains("By.id:")) {
            String id = elementString.replaceAll(".*By\\.id:\"([^\"]+)\".*", "$1");
            if (!id.equals(elementString)) {
                return By.id(id);
            }
        } else if (elementString.contains("By.xpath:")) {
            String xpath = elementString.replaceAll(".*By\\.xpath:\"([^\"]+)\".*", "$1");
            if (!xpath.equals(elementString)) {
                return By.xpath(xpath);
            }
        } else if (elementString.contains("By.className:")) {
            String className = elementString.replaceAll(".*By\\.className:\"([^\"]+)\".*", "$1");
            if (!className.equals(elementString)) {
                return By.className(className);
            }
        } else if (elementString.contains("By.name:")) {
            String name = elementString.replaceAll(".*By\\.name:\"([^\"]+)\".*", "$1");
            if (!name.equals(elementString)) {
                return By.name(name);
            }
        }
        
        try {
            if (elementString.contains("id:")) {
                String[] parts = elementString.split("id:");
                if (parts.length > 1) {
                    String idPart = parts[1].trim();
                    if (idPart.startsWith("\"") && idPart.contains("\"")) {
                        String id = idPart.substring(1, idPart.indexOf("\"", 1));
                        return By.id(id);
                    }
                }
            } else if (elementString.contains("xpath:")) {
                String[] parts = elementString.split("xpath:");
                if (parts.length > 1) {
                    String xpathPart = parts[1].trim();
                    if (xpathPart.startsWith("\"") && xpathPart.contains("\"")) {
                        String xpath = xpathPart.substring(1, xpathPart.indexOf("\"", 1));
                        return By.xpath(xpath);
                    }
                }
            }
        } catch (Exception e) {
        }
        
        throw new IllegalArgumentException("Unable to determine locator for element: " + elementString + 
                                         ". Consider using explicit locators instead of WebElement references.");
    }
    
    protected void enterPasscodeSequence(String passcode) {
        if (passcode == null || passcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Passcode cannot be null or empty");
        }
        
        for (char digit : passcode.toCharArray()) {
            By digitLocator = By.xpath("//android.widget.TextView[@text='" + digit + "']");
            clickElement(digitLocator);
        }
    }
    
    protected void navigateBack() {
        driver.navigate().back();
    }
    
    public abstract boolean isPageLoaded();
    
    protected abstract String getPageName();
}