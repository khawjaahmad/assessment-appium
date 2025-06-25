package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.LogUtils;

public class SplashPage extends BasePage {
    private static final By CREATE_NEW_WALLET_BUTTON = By.id("CreateNewWalletButton");
    private static final By IMPORT_WALLET_BUTTON = By.id("ImportWalletButton");
    private static final By TERMS_AND_PRIVACY_TEXT = By.xpath("//android.widget.TextView[contains(@text, 'By tapping any button you agree and consent to')]");
    private static final By SPLASH_TITLE_TEXT = By.xpath("//android.widget.TextView[contains(@text, 'the power of your digital assets')]");

    public SplashPage(AppiumDriver driver) {
        super(driver);
        LogUtils.info("SplashPage initialized");
    }

    @Override
    public boolean isPageLoaded() {
        try {
            boolean isLoaded = isElementDisplayed(CREATE_NEW_WALLET_BUTTON) && 
                              isElementDisplayed(IMPORT_WALLET_BUTTON);
            LogUtils.info("SplashPage loaded status: " + isLoaded);
            return isLoaded;
        } catch (Exception e) {
            LogUtils.error("Error checking if SplashPage is loaded: " + e.getMessage());
            return false;
        }
    }

    @Override
    protected String getPageName() {
        return "Splash Page";
    }

    public PasscodePage clickCreateNewWallet() {
        LogUtils.action("Clicking Create New Wallet button");
        clickElement(CREATE_NEW_WALLET_BUTTON);
        return new PasscodePage(driver);
    }

    public SplashPage verifyPageIsLoaded() {
        LogUtils.verification("Verifying Splash page is loaded", isPageLoaded());
        super.verifyPageIsLoaded("Splash page should be loaded");
        return this;
    }

    public SplashPage verifyCreateNewWalletButtonDisplayed() {
        LogUtils.verification("Verifying Create New Wallet button is displayed", isCreateNewWalletButtonDisplayed());
        verifyElementIsDisplayed(CREATE_NEW_WALLET_BUTTON, "Create New Wallet button");
        return this;
    }

    public SplashPage verifyImportWalletButtonDisplayed() {
        LogUtils.verification("Verifying Import Wallet button is displayed", isImportWalletButtonDisplayed());
        verifyElementIsDisplayed(IMPORT_WALLET_BUTTON, "Import Wallet button");
        return this;
    }

    public SplashPage verifyAppHandlesNoNetworkGracefully() {
        LogUtils.verification("Verifying app handles no network connection gracefully", isPageLoaded());
        verifyPageIsLoaded("App should not crash and should load without network connection");
        
        boolean appStillFunctional = isElementDisplayed(CREATE_NEW_WALLET_BUTTON) || 
                                   isElementDisplayed(IMPORT_WALLET_BUTTON);
        assertions.assertTrue(appStillFunctional, "App should remain functional without network connection");
        return this;
    }

    public SplashPage verifyOptionalElementsIfPresent() {
        LogUtils.step("Verifying optional splash page elements if present");
        
        if (isElementDisplayed(TERMS_AND_PRIVACY_TEXT)) {
            LogUtils.verification("Terms and Privacy text is present and displayed", true);
            verifyElementIsDisplayed(TERMS_AND_PRIVACY_TEXT, "Terms and Privacy text");
        } else {
            LogUtils.info("Terms and Privacy text not found on current splash page");
        }
        
        if (isElementDisplayed(SPLASH_TITLE_TEXT)) {
            LogUtils.verification("Splash title text is present and displayed", true);
            verifyElementIsDisplayed(SPLASH_TITLE_TEXT, "Splash title text");
        } else {
            LogUtils.info("Splash title text not found on current splash page");
        }
        
        return this;
    }

    public boolean isCreateNewWalletButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(CREATE_NEW_WALLET_BUTTON);
        LogUtils.debug("Create New Wallet button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isImportWalletButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(IMPORT_WALLET_BUTTON);
        LogUtils.debug("Import Wallet button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isSplashPageLoaded() {
        return isPageLoaded();
    }

    public void verifySplashScreenDisplayed() {
        verifyPageIsLoaded();
    }
}