package pages;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import utils.LogUtils;

public class NotificationPage extends BasePage {

    private static final By NOTIFICATION_TITLE = By.xpath("//android.widget.TextView[@text='Keep up with the market!']");
    private static final By NOTIFICATION_DESCRIPTION = By.id("infoDialogContent");
    private static final By ENABLE_NOTIFICATIONS_BUTTON = By.id("buttonTitle");
    private static final By SKIP_BUTTON = By.id("secondaryAction");

    public NotificationPage(AppiumDriver driver) {
        super(driver);
        LogUtils.info("NotificationPage initialized");
    }

    @Override
    public boolean isPageLoaded() {
        boolean isLoaded = isElementDisplayed(NOTIFICATION_DESCRIPTION) && isElementDisplayed(SKIP_BUTTON);
        LogUtils.info("NotificationPage loaded status: " + isLoaded);
        return isLoaded;
    }

    @Override
    protected String getPageName() {
        return "Notification Page";
    }

    public WalletReadyPage clickEnableNotifications() {
        LogUtils.action("Clicking Enable Notifications button");
        clickElement(ENABLE_NOTIFICATIONS_BUTTON);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        return new WalletReadyPage(driver);
    }

    public NotificationPage verifyPageIsLoaded() {
        LogUtils.verification("Verifying Notification page is loaded", isPageLoaded());
        super.verifyPageIsLoaded("Notification page should be loaded");
        return this;
    }

    public NotificationPage verifyEnableNotificationsButtonDisplayed() {
        LogUtils.verification("Verifying Enable Notifications button is displayed", isEnableNotificationsButtonDisplayed());
        verifyElementIsDisplayed(ENABLE_NOTIFICATIONS_BUTTON, "Enable Notifications button");
        return this;
    }

    public NotificationPage verifyNotificationTexts() {
        LogUtils.step("Verifying Notification page text content");
        
        verifyElementContainsText(NOTIFICATION_TITLE, "Keep up with the market", "Notification title");
        
        verifyElementContainsText(NOTIFICATION_DESCRIPTION, "Turn on notifications", "Notification description");
        
        return this;
    }

    private boolean isEnableNotificationsButtonDisplayed() {
        boolean isDisplayed = isElementDisplayed(ENABLE_NOTIFICATIONS_BUTTON);
        LogUtils.debug("Enable Notifications button displayed: " + isDisplayed);
        return isDisplayed;
    }
}