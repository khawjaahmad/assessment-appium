package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.LogUtils;

public class WalletReadyPage extends BasePage {
    private static final By WALLET_READY_TITLE = By.xpath("//android.widget.TextView[@text='Brilliant, your wallet is ready!']");
    private static final By WALLET_READY_SUBTITLE = By.xpath("//android.widget.TextView[@text='Buy or deposit to get started.']");
    private static final By BUY_CRYPTO_BUTTON = By.xpath("//android.widget.TextView[@text='Buy Crypto']");
    private static final By DEPOSIT_CRYPTO_BUTTON = By.xpath("//android.widget.TextView[@text='Deposit Crypto']");
    private static final By SKIP_BUTTON = By.xpath("//android.widget.TextView[contains(@text, 'Skip')]");

    public WalletReadyPage(AppiumDriver driver) {
        super(driver);
        LogUtils.info("WalletReadyPage initialized");
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = isElementDisplayed(WALLET_READY_TITLE);
        LogUtils.info("WalletReadyPage loaded status: " + isLoaded);
        return isLoaded;
    }

    @Override
    protected String getPageName() {
        return "Wallet Ready Page";
    }

    public DashboardPage clickSkip() {
        LogUtils.action("Clicking Skip button to proceed to dashboard");
        clickElement(SKIP_BUTTON);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return new DashboardPage(driver);
    }

    public WalletReadyPage verifyPageIsLoaded() {
        LogUtils.verification("Verifying Wallet Ready page is loaded", isPageLoaded());
        super.verifyPageIsLoaded("Wallet Ready page should be loaded");
        return this;
    }

    public WalletReadyPage verifyAllElementsDisplayed() {
        LogUtils.step("Verifying all Wallet Ready page elements are displayed");
        verifyWalletReadyTitleDisplayed();
        verifyWalletReadySubtitleDisplayed();
        verifyBuyCryptoButtonDisplayed();
        verifyDepositCryptoButtonDisplayed();
        verifySkipButtonDisplayed();
        return this;
    }

    public WalletReadyPage verifyPageTexts() {
        LogUtils.step("Verifying Wallet Ready page text content");
        
        String expectedTitle = "Brilliant, your wallet is ready!";
        String expectedSubtitle = "Buy or deposit to get started.";
        
        verifyElementText(WALLET_READY_TITLE, expectedTitle, "Wallet Ready title");
        verifyElementText(WALLET_READY_SUBTITLE, expectedSubtitle, "Wallet Ready subtitle");
        
        return this;
    }

    private WalletReadyPage verifyWalletReadyTitleDisplayed() {
        LogUtils.verification("Verifying Wallet Ready title is displayed", isWalletReadyTitleDisplayed());
        verifyElementIsDisplayed(WALLET_READY_TITLE, "Wallet Ready title");
        return this;
    }

    private WalletReadyPage verifyWalletReadySubtitleDisplayed() {
        LogUtils.verification("Verifying Wallet Ready subtitle is displayed", isWalletReadySubtitleDisplayed());
        verifyElementIsDisplayed(WALLET_READY_SUBTITLE, "Wallet Ready subtitle");
        return this;
    }

    private WalletReadyPage verifySkipButtonDisplayed() {
        LogUtils.verification("Verifying Skip button is displayed", isSkipButtonDisplayed());
        verifyElementIsDisplayed(SKIP_BUTTON, "Skip button");
        return this;
    }

    private WalletReadyPage verifyBuyCryptoButtonDisplayed() {
        LogUtils.verification("Verifying Buy Crypto button is displayed", isBuyCryptoButtonDisplayed());
        verifyElementIsDisplayed(BUY_CRYPTO_BUTTON, "Buy Crypto button");
        return this;
    }

    private WalletReadyPage verifyDepositCryptoButtonDisplayed() {
        LogUtils.verification("Verifying Deposit Crypto button is displayed", isDepositCryptoButtonDisplayed());
        verifyElementIsDisplayed(DEPOSIT_CRYPTO_BUTTON, "Deposit Crypto button");
        return this;
    }
    
    private boolean isWalletReadyTitleDisplayed() {
        boolean isDisplayed = isElementDisplayed(WALLET_READY_TITLE);
        LogUtils.debug("Wallet Ready title displayed: " + isDisplayed);
        return isDisplayed;
    }

    private boolean isWalletReadySubtitleDisplayed() {
        boolean isDisplayed = isElementDisplayed(WALLET_READY_SUBTITLE);
        LogUtils.debug("Wallet Ready subtitle displayed: " + isDisplayed);
        return isDisplayed;
    }

    private boolean isSkipButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(SKIP_BUTTON);
        LogUtils.debug("Skip button displayed: " + isDisplayed);
        return isDisplayed;
    }

    private boolean isBuyCryptoButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(BUY_CRYPTO_BUTTON);
        LogUtils.debug("Buy Crypto button displayed: " + isDisplayed);
        return isDisplayed;
    }

    private boolean isDepositCryptoButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(DEPOSIT_CRYPTO_BUTTON);
        LogUtils.debug("Deposit Crypto button displayed: " + isDisplayed);
        return isDisplayed;
    }
}