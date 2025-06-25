package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.LogUtils;

public class DashboardPage extends BasePage {

    private static final By WALLET_NAME_TITLE = By.id("topBarWalletName");
    private static final By MAIN_BALANCE = By.id("mainBalance");
    private static final By BALANCE_CHANGE_24H = By.id("mainBalanceChange24h");
    private static final By EMPTY_WALLET_MESSAGE = By.xpath("//android.widget.TextView[@text='Your wallet is empty.']");
    private static final By SEND_BUTTON = By.id("HomeSendButton");
    private static final By RECEIVE_BUTTON = By.id("HomeReceiveButton");
    private static final By BUY_BUTTON = By.id("HomeBuyButton");
    private static final By SELL_BUTTON = By.id("HomeSellButton");
    private static final By TRENDING_SECTION = By.xpath("//android.widget.TextView[@text='Trending']");
    private static final By TRENDING_TOKEN_BUTTON = By.id("TrendingTokenNavigationButton");
    private static final By SWAP_BUTTON = By.id("SwapNavigationButton");
    private static final By EARN_BUTTON = By.id("EarnNavigationButton");
    private static final By DISCOVER_BUTTON = By.id("DiscoverNavigationButton");
    private static final By HOME_BUTTON = By.id("HomeNavigationButton");
    private static final By BOTTOM_NAV_BAR = By.xpath("//android.view.View[contains(@bounds, '[0,2208][1080,2337]')]");

    public DashboardPage(AppiumDriver driver) {
        super(driver);
        LogUtils.info("DashboardPage initialized");
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = isElementDisplayed(WALLET_NAME_TITLE) && 
                          isElementDisplayed(MAIN_BALANCE) &&
                          isElementDisplayed(SEND_BUTTON) &&
                          isElementDisplayed(RECEIVE_BUTTON);
        LogUtils.info("DashboardPage loaded status: " + isLoaded);
        return isLoaded;
    }

    @Override
    protected String getPageName() {
        return "Dashboard Page";
    }

    public void clickSend() {
        LogUtils.action("Clicking Send button");
        clickElement(SEND_BUTTON);
    }

    public void clickReceive() {
        LogUtils.action("Clicking Receive button");
        clickElement(RECEIVE_BUTTON);
    }

    public void clickBuy() {
        LogUtils.action("Clicking Buy button");
        clickElement(BUY_BUTTON);
    }

    public void clickSell() {
        LogUtils.action("Clicking Sell button");
        clickElement(SELL_BUTTON);
    }

    public void navigateToTrending() {
        LogUtils.action("Navigating to Trending section");
        clickElement(TRENDING_TOKEN_BUTTON);
    }

    public void navigateToHome() {
        LogUtils.action("Navigating to Home");
        clickElement(HOME_BUTTON);
    }

    public void navigateToSwap() {
        LogUtils.action("Navigating to Swap");
        clickElement(SWAP_BUTTON);
    }

    public void navigateToEarn() {
        LogUtils.action("Navigating to Earn");
        clickElement(EARN_BUTTON);
    }

    public void navigateToDiscover() {
        LogUtils.action("Navigating to Discover");
        clickElement(DISCOVER_BUTTON);
    }

    public String getWalletName() {
        String walletName = getText(WALLET_NAME_TITLE);
        LogUtils.info("Wallet name: " + walletName);
        return walletName;
    }

    public String getMainBalance() {
        String balance = getText(MAIN_BALANCE);
        LogUtils.info("Main balance: " + balance);
        return balance;
    }

    public String getBalanceChange24h() {
        String change = getText(BALANCE_CHANGE_24H);
        LogUtils.info("24h balance change: " + change);
        return change;
    }

    public String getEmptyWalletMessage() {
        String message = getText(EMPTY_WALLET_MESSAGE);
        LogUtils.info("Empty wallet message: " + message);
        return message;
    }

    public DashboardPage verifyPageIsLoaded() {
        LogUtils.verification("Verifying Dashboard page is loaded", isPageLoaded());
        super.verifyPageIsLoaded("Dashboard page should be loaded after clicking skip");
        return this;
    }

    public DashboardPage verifyDashboardDisplayed() {
        LogUtils.step("Verifying dashboard is displayed");
        waitForElementToLoad(WALLET_NAME_TITLE);
        verifyPageIsLoaded();
        return this;
    }

    public DashboardPage verifyDashboardElementsPresent() {
        LogUtils.step("Verifying dashboard elements are present");
        verifyElementIsDisplayed(SEND_BUTTON, "Send button");
        verifyElementIsDisplayed(RECEIVE_BUTTON, "Receive button");
        verifyElementIsDisplayed(MAIN_BALANCE, "Main balance");
        verifyElementIsDisplayed(WALLET_NAME_TITLE, "Wallet name title");
        return this;
    }

    public DashboardPage verifyNavigationButtonsDisplayed() {
        LogUtils.step("Verifying all navigation buttons are displayed");
        verifyElementIsDisplayed(TRENDING_TOKEN_BUTTON, "Trending Token navigation button");
        verifyElementIsDisplayed(SWAP_BUTTON, "Swap navigation button");
        verifyElementIsDisplayed(EARN_BUTTON, "Earn navigation button");
        verifyElementIsDisplayed(DISCOVER_BUTTON, "Discover navigation button");
        return this;
    }

    public boolean isTrendingTokenButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(TRENDING_TOKEN_BUTTON);
        LogUtils.debug("Trending Token button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isSwapButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(SWAP_BUTTON);
        LogUtils.debug("Swap button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isEarnButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(EARN_BUTTON);
        LogUtils.debug("Earn button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isDiscoverButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(DISCOVER_BUTTON);
        LogUtils.debug("Discover button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isHomeButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(HOME_BUTTON);
        LogUtils.debug("Home button displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isEmptyWalletMessageDisplayed() {
        boolean isDisplayed = isElementDisplayed(EMPTY_WALLET_MESSAGE);
        LogUtils.debug("Empty wallet message displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isTrendingSectionDisplayed() {
        boolean isDisplayed = isElementDisplayed(TRENDING_SECTION);
        LogUtils.debug("Trending section displayed: " + isDisplayed);
        return isDisplayed;
    }

    public boolean isBottomNavigationDisplayed() {
        boolean isDisplayed = isElementDisplayed(BOTTOM_NAV_BAR);
        LogUtils.debug("Bottom navigation displayed: " + isDisplayed);
        return isDisplayed;
    }

    public DashboardPage waitForPageToLoad() {
        LogUtils.info("Waiting for Dashboard page to load completely");
        waitForElementToLoad(WALLET_NAME_TITLE);
        waitForElementToLoad(MAIN_BALANCE);
        waitForElementToLoad(SEND_BUTTON);
        waitForElementToLoad(RECEIVE_BUTTON);
        return this;
    }
}