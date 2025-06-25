package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.Test;
import pages.SplashPage;
import pages.PasscodePage;
import pages.NotificationPage;
import pages.WalletReadyPage;
import pages.DashboardPage;
import utils.LogUtils;

@Feature("Simple Wallet Test")
public class SimpleWalletTest extends BaseTest {
    
    @Test(priority = 1)
    @Description("Verify splash screen appears and displays correctly")
    @Severity(SeverityLevel.CRITICAL)
    public void testSplashScreenAppears() {
        LogUtils.testStart("testSplashScreenAppears");
        
        SplashPage splashPage = getSplashPage();
        
        splashPage.verifyPageIsLoaded()
                 .verifyCreateNewWalletButtonDisplayed()
                 .verifyImportWalletButtonDisplayed()
                 .verifyOptionalElementsIfPresent(); 
        
        LogUtils.testEnd("testSplashScreenAppears", true);
    }
    
    @Test(priority = 2)
    @Description("Verify passcode creation flow - create and confirm passcode")
    @Severity(SeverityLevel.CRITICAL)
    public void testPasscodeCreationFlow() {
        LogUtils.testStart("testPasscodeCreationFlow");
        
        SplashPage splashPage = getSplashPage();
        PasscodePage passcodePage = splashPage.verifyPageIsLoaded()
                                             .clickCreateNewWallet();
        
        String validPasscode = testData.getValidPasscode();
        passcodePage.createPasscodeFlow(validPasscode);
        
        LogUtils.testEnd("testPasscodeCreationFlow", true);
    }
    
    @Test(priority = 3)
    @Description("Verify notification page appears after passcode creation")
    @Severity(SeverityLevel.CRITICAL)
    public void testNotificationPageFlow() {
        LogUtils.testStart("testNotificationPageFlow");
        
        navigateToNotificationPage();
        
        NotificationPage notificationPage = getNotificationPage();
        notificationPage.verifyPageIsLoaded()
                       .verifyEnableNotificationsButtonDisplayed()
                       .verifyNotificationTexts();
        
        WalletReadyPage walletReadyPage = notificationPage.clickEnableNotifications();
        walletReadyPage.verifyPageIsLoaded()
                      .verifyAllElementsDisplayed()
                      .verifyPageTexts();
        
        LogUtils.testEnd("testNotificationPageFlow", true);
    }
    
    @Test(priority = 4)
    @Description("Verify wallet ready page elements and functionality")
    @Severity(SeverityLevel.CRITICAL)
    public void testWalletReadyPage() {
        LogUtils.testStart("testWalletReadyPage");
        
        navigateToWalletReadyPage();
        
        WalletReadyPage walletReadyPage = getWalletReadyPage();
        walletReadyPage.verifyPageIsLoaded()
                      .verifyAllElementsDisplayed()
                      .verifyPageTexts();
        
        LogUtils.testEnd("testWalletReadyPage", true);
    }
    
    @Test(priority = 5)
    @Description("Verify dashboard page elements and navigation")
    @Severity(SeverityLevel.CRITICAL)
    public void testDashboardPage() {
        LogUtils.testStart("testDashboardPage");
        
        navigateToDashboard();
        
        DashboardPage dashboardPage = getDashboardPage();
        dashboardPage.verifyPageIsLoaded()
                    .verifyNavigationButtonsDisplayed();
        
        LogUtils.testEnd("testDashboardPage", true);
    }
}