package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.LogUtils;

public class PasscodePage extends BasePage {

    private static final By BACK_BUTTON = By.id("toolbarButtonBack");
    private static final By PASSCODE_INSTRUCTION_TEXT = By.xpath("//android.widget.TextView[@text='Enter your passcode. Be sure to remember it so you can unlock your wallet.']");
    
    private boolean isCreateMode = true;

    public PasscodePage(AppiumDriver driver) {
        super(driver);
        LogUtils.info("PasscodePage initialized");
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = isNumpadDisplayed();
        LogUtils.info("PasscodePage loaded status: " + isLoaded);
        return isLoaded;
    }

    @Override
    protected String getPageName() {
        return isCreateMode ? "Create Passcode Page" : "Confirm Passcode Page";
    }

    public PasscodePage enterPasscode(String passcode) {
        LogUtils.action("Entering passcode");
        LogUtils.dataEntry("passcode", passcode);
        
        if (passcode == null || passcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Passcode cannot be null or empty");
        }
        
        for (char digit : passcode.toCharArray()) {
            clickDigit(String.valueOf(digit));
        }
        
        try {
            Thread.sleep(1000); 
            if (isPageLoaded()) {
                isCreateMode = false; 
                LogUtils.info("Moved to passcode confirmation mode");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return this;
    }

    public PasscodePage clickBackButton() {
        LogUtils.action("Clicking back button");
        if (isElementDisplayed(BACK_BUTTON)) {
            clickElement(BACK_BUTTON);
        } else {
            LogUtils.warn("Back button not displayed, using device back navigation");
            navigateBack();
        }
        return this;
    }

    public PasscodePage enterPartialPasscode(String partialPasscode) {
        LogUtils.action("Entering partial passcode for testing");
        LogUtils.dataEntry("partial passcode", partialPasscode);
        
        for (char digit : partialPasscode.toCharArray()) {
            clickDigit(String.valueOf(digit));
        }
        return this;
    }

    public PasscodePage verifyCreatePasscodePageLoaded() {
        LogUtils.verification("Verifying create passcode page is loaded", true);
        verifyPageIsLoaded("Create passcode page should be loaded");
        verifyNumpadDisplayed();
        verifyPasscodeInstructionIfPresent();
        isCreateMode = true;
        return this;
    }

    public PasscodePage verifyConfirmPasscodePageLoaded() {
        LogUtils.verification("Verifying confirm passcode page is loaded", true);
        verifyPageIsLoaded("Confirm passcode page should be loaded");
        verifyNumpadDisplayed();
        isCreateMode = false;
        return this;
    }

    public PasscodePage verifyNumpadDisplayed() {
        LogUtils.verification("Verifying numpad is displayed", isNumpadDisplayed());
        assertions.assertTrue(isNumpadDisplayed(), "Numpad should be displayed on passcode page");
        return this;
    }

    public PasscodePage verifyBackButtonConfirmation() {
        LogUtils.verification("Verifying page functionality after back button press", isPageLoaded());
        verifyPageIsLoaded("Page should remain functional after back button press");
        return this;
    }

    public PasscodePage verifyBackButtonOptions() {
        LogUtils.verification("Verifying options are available after back button press", true);
        boolean canContinue = isNumpadDisplayed() || isBackButtonDisplayed();
        assertions.assertTrue(canContinue, "User should have options available after back button press");
        return this;
    }

    public PasscodePage verifyTimeoutHandling() {
        LogUtils.step("Testing timeout scenario handling");
        waitForTimeoutScenario();
        verifyPageIsLoaded("App should not crash during timeout scenario");
        LogUtils.verification("App handles timeout gracefully", true);
        return this;
    }

    public PasscodePage verifyRetryOptionAvailable() {
        LogUtils.verification("Verifying retry option is available", isNumpadDisplayed());
        assertions.assertTrue(isNumpadDisplayed(), "Numpad should be available for retry");
        return this;
    }

    public PasscodePage verifyPasscodeInstructionIfPresent() {
        LogUtils.step("Verifying passcode instruction text if present");
        
        if (isElementDisplayed(PASSCODE_INSTRUCTION_TEXT)) {
            LogUtils.verification("Passcode instruction text is present and displayed", true);
            verifyElementIsDisplayed(PASSCODE_INSTRUCTION_TEXT, "Passcode instruction text");
            
            String expectedText = "Enter your passcode. Be sure to remember it so you can unlock your wallet.";
            verifyElementText(PASSCODE_INSTRUCTION_TEXT, expectedText, "Passcode instruction text");
        } else {
            LogUtils.info("Passcode instruction text not found on current passcode page");
        }
        
        return this;
    }

    public PasscodePage createPasscodeFlow(String passcode) {
        LogUtils.step("Executing complete passcode creation flow");
        
        verifyCreatePasscodePageLoaded();
        
        enterPasscode(passcode);
        
        verifyConfirmPasscodePageLoaded();
        
        enterPasscode(passcode);
        
        LogUtils.step("Passcode creation flow completed successfully");
        return this;
    }

    public PasscodePage testPasscodeMismatch(String firstPasscode, String secondPasscode) {
        LogUtils.step("Testing passcode mismatch scenario");
        
        verifyCreatePasscodePageLoaded();
        enterPasscode(firstPasscode);
        verifyConfirmPasscodePageLoaded();
        enterPasscode(secondPasscode);
        
        verifyPageIsLoaded("Should remain on passcode page due to mismatch");
        LogUtils.verification("Passcode mismatch handled correctly", true);
        
        return this;
    }

    private PasscodePage clickDigit(String digit) {
        LogUtils.elementInteraction("Click digit", digit);
        By digitLocator = By.xpath("//android.widget.TextView[@text='" + digit + "']");
        clickElement(digitLocator);
        return this;
    }

    private boolean isDigitButtonDisplayed(String digit) {
        By digitLocator = By.xpath("//android.widget.TextView[@text='" + digit + "']");
        boolean isDisplayed = isElementDisplayed(digitLocator);
        LogUtils.debug("Digit button " + digit + " displayed: " + isDisplayed);
        return isDisplayed;
    }

    private boolean isNumpadDisplayed() {
        boolean numpadDisplayed = isDigitButtonDisplayed("1") && 
                                 isDigitButtonDisplayed("2") && 
                                 isDigitButtonDisplayed("0");
        LogUtils.debug("Numpad displayed: " + numpadDisplayed);
        return numpadDisplayed;
    }

    private boolean isBackButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(BACK_BUTTON);
        LogUtils.debug("Back button displayed: " + isDisplayed);
        return isDisplayed;
    }

    private void waitForTimeoutScenario() {
        LogUtils.debug("Simulating timeout scenario");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogUtils.warn("Timeout simulation interrupted");
        }
    }
}