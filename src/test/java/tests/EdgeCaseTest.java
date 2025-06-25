package tests;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import pages.SplashPage;
import pages.PasscodePage;
import utils.LogUtils;

@Epic("Trust Wallet Edge Cases")
@Feature("App Edge Case Scenarios")
public class EdgeCaseTest extends BaseTest {

    @Test(description = "Test app launch behavior without internet connection")
    @Story("Network Connectivity")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify app handles gracefully when launched without internet connection")
    public void testAppLaunchWithoutInternet() {
        LogUtils.testStart("testAppLaunchWithoutInternet");
        
        SplashPage splashPage = getSplashPage();
        splashPage.verifyPageIsLoaded()
                 .verifyOptionalElementsIfPresent()
                 .verifyAppHandlesNoNetworkGracefully();
        
        LogUtils.testEnd("testAppLaunchWithoutInternet", true);
    }

    @Test(description = "Test passcode creation with invalid input")
    @Story("Input Validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify app handles invalid passcode input gracefully")
    public void testPasscodeCreationWithInvalidInput() {
        LogUtils.testStart("testPasscodeCreationWithInvalidInput");
        
        SplashPage splashPage = getSplashPage();
        PasscodePage passcodePage = splashPage.verifyPageIsLoaded()
                                             .clickCreateNewWallet();
        
        String shortPasscode = testData.getShortPasscode();
        passcodePage.verifyCreatePasscodePageLoaded()
                   .enterPartialPasscode(shortPasscode)
                   .verifyNumpadDisplayed();
        
        LogUtils.testEnd("testPasscodeCreationWithInvalidInput", true);
    }

    @Test(description = "Test rapid passcode entry")
    @Story("Performance")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify app handles rapid passcode entry without issues")
    public void testRapidPasscodeEntry() {
        LogUtils.testStart("testRapidPasscodeEntry");
        
        SplashPage splashPage = getSplashPage();
        PasscodePage passcodePage = splashPage.verifyPageIsLoaded()
                                             .clickCreateNewWallet();
        
        String validPasscode = testData.getValidPasscode();
        passcodePage.verifyCreatePasscodePageLoaded()
                   .enterPasscode(validPasscode)
                   .verifyConfirmPasscodePageLoaded()
                   .enterPasscode(validPasscode);
        
        LogUtils.testEnd("testRapidPasscodeEntry", true);
    }

    @Test(description = "Test passcode mismatch scenario")
    @Story("Input Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify app handles passcode mismatch appropriately")
    public void testPasscodeMismatch() {
        LogUtils.testStart("testPasscodeMismatch");
        
        SplashPage splashPage = getSplashPage();
        PasscodePage passcodePage = splashPage.verifyPageIsLoaded()
                                             .clickCreateNewWallet();
        
        String[] mismatchedPasscodes = testData.getMismatchedPasscodes();
        passcodePage.testPasscodeMismatch(mismatchedPasscodes[0], mismatchedPasscodes[1]);
        
        LogUtils.testEnd("testPasscodeMismatch", true);
    }

    @Test(description = "Test timeout handling during passcode creation")
    @Story("Performance")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify app handles timeout scenarios gracefully")
    public void testTimeoutHandling() {
        LogUtils.testStart("testTimeoutHandling");
        
        SplashPage splashPage = getSplashPage();
        PasscodePage passcodePage = splashPage.verifyPageIsLoaded()
                                             .clickCreateNewWallet();
        
        passcodePage.verifyCreatePasscodePageLoaded()
                   .verifyTimeoutHandling()
                   .verifyRetryOptionAvailable();
        
        LogUtils.testEnd("testTimeoutHandling", true);
    }
}