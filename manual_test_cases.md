# Manual Test Cases - Trust Wallet Create Wallet Functionality

## Test Suite Overview

**Application:** Trust Wallet Mobile App  
**Feature:** Create Wallet Functionality  
**Test Environment:** Android Device/Emulator  
**Test Type:** Functional, Negative, Edge Cases  

---

## Test Case 1: Happy Path - Successful Wallet Creation

**Test ID:** TC_WALLET_001  
**Priority:** P0 (Critical)  
**Test Type:** Functional  
**Estimated Time:** 3-5 minutes  

### Preconditions
- Trust Wallet app is installed on the device
- App is launched for the first time
- No existing wallet is present
- Device has sufficient storage space

### Test Steps
1. Launch Trust Wallet application
2. Verify splash screen appears with application branding
3. Locate and verify "Create New Wallet" button is visible and enabled
4. Locate and verify "Import Wallet" button is visible and enabled
5. Tap "Create New Wallet" button
6. Verify passcode creation screen appears with:
   - Instruction text about creating passcode
   - Numeric keypad (0-9)
   - Visual indicators for passcode length
7. Enter a valid 6-digit passcode: `123456`
8. Verify passcode confirmation screen appears with:
   - Instruction text about confirming passcode
   - Numeric keypad remains visible
   - Visual indicators show empty state
9. Re-enter the same passcode: `123456`
10. Verify notification permission screen appears (if applicable)
11. Choose to either "Enable Notifications" or "Skip"
12. Verify "Wallet Ready" screen appears with:
    - Success message: "Brilliant, your wallet is ready!"
    - Subtitle: "Buy or deposit to get started."
    - "Buy Crypto" button
    - "Deposit Crypto" button
    - "Skip" button
13. Tap "Skip" button
14. Verify dashboard loads successfully

### Expected Results
- Splash screen displays correctly with both wallet options
- Passcode creation screen appears with all required elements
- Passcode confirmation screen appears after entering first passcode
- Notification permission screen handles user choice appropriately
- Wallet ready page shows success message with action buttons
- Dashboard loads with empty wallet state showing:
  - Wallet name/title
  - Balance: $0.00 (or equivalent)
  - "Your wallet is empty" message
  - Action buttons: Send, Receive, Buy, Sell
  - Bottom navigation: Home, Trending, Swap, Earn, Discover

### Test Data
- **Valid Passcode:** 123456

### Post-conditions
- New wallet is created successfully
- User is on the main dashboard
- Wallet shows empty state

---

## Test Case 2: Passcode Validation - Short Passcode

**Test ID:** TC_WALLET_002  
**Priority:** P1 (High)  
**Test Type:** Negative Testing  
**Estimated Time:** 2-3 minutes  

### Preconditions
- Trust Wallet app is launched
- User has navigated to passcode creation screen

### Test Steps
1. Navigate to "Create New Wallet" → Passcode creation screen
2. Enter a passcode shorter than 6 digits: `123`
3. Observe visual feedback on screen
4. Attempt any available action (if system allows)
5. Verify system behavior and user guidance

### Expected Results
- System should not allow proceeding with incomplete passcode
- User remains on passcode creation screen
- Visual indicators show incomplete state (e.g., empty circles)
- No error message appears until user attempts to proceed
- User can continue entering remaining digits
- App remains responsive and stable

### Test Data
- **Short Passcode:** 123

### Error Scenarios
- If system crashes → **Critical Bug (P0)**
- If system allows proceeding → **Security Vulnerability (P0)**
- If no visual feedback → **Usability Issue (P2)**

---

## Test Case 3: Passcode Validation - Mismatched Passcodes

**Test ID:** TC_WALLET_003  
**Priority:** P1 (High)  
**Test Type:** Negative Testing  
**Estimated Time:** 3-4 minutes  

### Preconditions
- User has successfully entered first passcode
- User is on passcode confirmation screen

### Test Steps
1. Complete passcode creation with first passcode: `111111`
2. Verify transition to confirmation screen
3. Enter different passcode on confirmation: `222222`
4. Observe system response and feedback
5. Verify available recovery options
6. Test retry functionality

### Expected Results
- System detects passcode mismatch
- Clear error message or feedback is provided
- User is guided back to passcode creation or given retry option
- Previous passcode entry is cleared for security
- User can retry with correct passcode
- No security vulnerabilities (passcodes not exposed)

### Test Data
- **First Passcode:** 111111
- **Second Passcode:** 222222

### Recovery Testing
- Verify user can successfully create wallet after mismatch
- Ensure no partial data corruption occurs

---

## Test Case 4: Navigation - Back Button During Passcode Creation

**Test ID:** TC_WALLET_004  
**Priority:** P2 (Medium)  
**Test Type:** Functional  
**Estimated Time:** 2-3 minutes  

### Preconditions
- User is on passcode creation screen

### Test Steps
1. Navigate to passcode creation screen
2. Enter partial passcode: `123`
3. Tap device back button (Android) or app back button (if available)
4. Verify current screen and available options
5. Test forward navigation again
6. Verify data state (partial entry should be cleared)

### Expected Results
- User can navigate back successfully without app crash
- App doesn't become unresponsive or frozen
- User returns to splash screen or previous screen
- Clear options available to proceed (Create/Import wallet buttons)
- Partial passcode entry is cleared for security
- User can restart wallet creation process cleanly

### Navigation Testing
- Test multiple back button presses
- Verify app doesn't exit unexpectedly
- Ensure consistent navigation behavior

---

## Test Case 5: Edge Case - Rapid Passcode Entry

**Test ID:** TC_WALLET_005  
**Priority:** P2 (Medium)  
**Test Type:** Performance/Stress  
**Estimated Time:** 2-3 minutes  

### Preconditions
- User is on passcode creation screen
- Device has normal performance capabilities

### Test Steps
1. Navigate to passcode creation screen
2. Rapidly tap passcode digits in quick succession: `1-2-3-4-5-6`
3. Observe UI response and digit registration
4. Immediately proceed to confirmation screen (if system allows)
5. Rapidly enter confirmation passcode: `1-2-3-4-5-6`
6. Monitor app performance and responsiveness

### Expected Results
- App handles rapid input without performance issues
- All digits are registered correctly in sequence
- No UI lag, freezing, or unresponsive elements
- Visual feedback updates smoothly
- Passcode flow completes successfully
- No duplicate digit registration or skipped inputs

### Performance Criteria
- Response time < 100ms per digit tap
- UI remains smooth throughout interaction
- No memory leaks or performance degradation

---

## Test Case 6: Network Connectivity - Offline Wallet Creation

**Test ID:** TC_WALLET_006  
**Priority:** P1 (High)  
**Test Type:** Connectivity/Reliability  
**Estimated Time:** 3-5 minutes  

### Preconditions
- Device has internet connectivity initially
- Trust Wallet app is available for launch

### Test Steps
1. Disable device internet connection (WiFi + Mobile Data)
2. Launch Trust Wallet app
3. Verify app launch behavior and loading screens
4. Attempt to create new wallet following normal flow
5. Complete passcode creation and confirmation
6. Proceed through notification and wallet ready screens
7. Verify dashboard functionality in offline mode

### Expected Results
- App launches successfully without internet connection
- Core wallet creation functionality works offline
- No network-related error messages during creation
- Local functionality remains accessible
- Appropriate messaging for features requiring internet (if any)
- App doesn't hang waiting for network responses

### Business Impact
- **Critical for users in areas with poor connectivity**
- **Essential for security (local wallet creation)**
- **Failure could result in user abandonment**

---

## Test Case 7: Timeout Handling - Extended Inactivity

**Test ID:** TC_WALLET_007  
**Priority:** P2 (Medium)  
**Test Type:** Reliability/Session Management  
**Estimated Time:** 8-10 minutes  

### Preconditions
- User is on passcode creation screen
- Device remains active (screen doesn't lock)

### Test Steps
1. Navigate to passcode creation screen
2. Leave app inactive without any interaction for 5 minutes
3. Return to app and test responsiveness
4. Attempt to enter passcode normally
5. Verify all functionality remains available
6. Complete wallet creation process

### Expected Results
- App remains responsive after extended timeout period
- User can continue with passcode creation without restart
- No session corruption or data loss occurs
- Clear recovery path available if needed
- Security considerations handled appropriately
- Memory usage remains stable

### Timeout Scenarios
- Test various timeout durations: 1min, 5min, 10min
- Verify behavior with screen lock/unlock
- Test with background app switching

---

## Test Case 8: Notification Permissions - Enable Flow

**Test ID:** TC_WALLET_008  
**Priority:** P2 (Medium)  
**Test Type:** Functional/Permissions  
**Estimated Time:** 3-4 minutes  

### Preconditions
- User has completed passcode creation successfully
- Notification permission screen is displayed

### Test Steps
1. Complete wallet creation flow up to notification permission screen
2. Verify screen content:
   - Title: "Keep up with the market!"
   - Description about notifications
   - "Enable Notifications" button
   - "Skip" option
3. Tap "Enable Notifications" button
4. Handle system permission dialog when it appears
5. Grant permission in system dialog
6. Verify progression to wallet ready screen
7. Verify notification preference is saved

### Expected Results
- System permission dialog appears correctly
- User choice is respected and stored properly
- App proceeds to wallet ready screen after permission grant
- Notification preferences are saved for future use
- No app crashes during permission handling
- Appropriate behavior if user denies system permission

### Permission Testing
- Test permission denial scenario
- Verify app behavior with restricted permissions
- Test permission changes in device settings

---

## Test Case 9: Notification Permissions - Skip Flow

**Test ID:** TC_WALLET_009  
**Priority:** P2 (Medium)  
**Test Type:** Functional/Alternative Path  
**Estimated Time:** 2-3 minutes  

### Preconditions
- User has completed passcode creation successfully
- Notification permission screen is displayed

### Test Steps
1. Complete wallet creation flow up to notification permission screen
2. Locate and tap "Skip" button or option
3. Verify immediate progression without system dialog
4. Confirm arrival at wallet ready screen
5. Verify core functionality remains unaffected
6. Test that notifications are not enabled

### Expected Results
- App proceeds immediately without system permission dialog
- No permission requests are made to the system
- User reaches wallet ready screen successfully
- Core app functionality remains completely unaffected
- User can enable notifications later through settings (if available)
- Clean user experience with clear choice respect

### Functional Verification
- Ensure no missing features due to skipped notifications
- Verify settings allow later notification enabling
- Test app behavior consistency

---

## Test Case 10: Dashboard Verification - Empty Wallet State

**Test ID:** TC_WALLET_010  
**Priority:** P1 (High)  
**Test Type:** Functional/UI Verification  
**Estimated Time:** 3-5 minutes  

### Preconditions
- User has completed full wallet creation flow
- User has reached the main dashboard screen

### Test Steps
1. Complete entire wallet creation process and reach dashboard
2. Verify wallet identification elements:
   - Wallet name/title is displayed
   - Wallet address or identifier present
3. Verify balance display:
   - Main balance shows $0.00 or equivalent
   - Balance change indicator (if present)
4. Verify action buttons are present and functional:
   - Send button (tap to verify it responds)
   - Receive button (tap to verify it responds)
   - Buy button (tap to verify it responds)
   - Sell button (tap to verify it responds)
5. Verify empty wallet messaging:
   - "Your wallet is empty" or similar message
   - Appropriate guidance for new users
6. Verify bottom navigation functionality:
   - Home tab (currently active)
   - Trending tab (tap to verify)
   - Swap tab (tap to verify)
   - Earn tab (tap to verify)
   - Discover tab (tap to verify)
7. Test navigation between tabs and return to Home

### Expected Results
- Wallet name/title is prominently displayed
- Main balance correctly shows $0.00 or local currency equivalent
- All action buttons (Send, Receive, Buy, Sell) are visible and responsive
- Empty wallet message provides clear guidance to users
- Bottom navigation tabs are all functional and accessible
- Tab switching works smoothly without crashes
- Visual design is consistent and professional
- No error states or broken UI elements

### UI/UX Verification
- Verify responsive design on different screen sizes
- Check accessibility features (if applicable)
- Ensure consistent branding and styling

---

## Test Execution Strategy

### Priority Classification

**P0 (Critical - Must Pass Before Release):**
- TC_WALLET_001: Happy Path Wallet Creation
- TC_WALLET_010: Dashboard Verification

**P1 (High Priority - Should Pass):**
- TC_WALLET_002: Short Passcode Validation
- TC_WALLET_003: Passcode Mismatch Handling
- TC_WALLET_006: Offline Functionality

**P2 (Medium Priority - Nice to Have):**
- TC_WALLET_004: Back Navigation
- TC_WALLET_005: Rapid Input Performance
- TC_WALLET_007: Timeout Handling
- TC_WALLET_008: Enable Notifications
- TC_WALLET_009: Skip Notifications

### Execution Environment

**Recommended Test Devices:**
- Android 10+ (Primary)
- Various screen sizes (Phone, Tablet)
- Different memory configurations
- Network conditions (WiFi, Mobile, Offline)

**Test Data Requirements:**
- Valid test passcodes: 123456, 654321
- Invalid test passcodes: 123, 12345678
- Mismatch scenarios: 111111 vs 222222

### Bug Reporting Guidelines

**Critical Issues (P0):**
- Complete inability to create wallet
- App crashes during core flow
- Security vulnerabilities
- Data corruption or loss

**High Priority Issues (P1):**
- Validation bypasses
- Poor error handling
- Network dependency issues
- Navigation problems

**Medium Priority Issues (P2):**
- UI/UX inconsistencies
- Performance degradation
- Edge case handling
- Accessibility concerns

### Business Impact Assessment

**Complete Failure Impact:**
- 100% loss of new user onboarding
- Negative app store reviews
- Customer support volume increase
- Potential security liabilities

**Partial Failure Impact:**
- 20-40% user drop-off during onboarding
- Increased support tickets
- Reduced user confidence
- Competitive disadvantage

**Success Criteria:**
- 95%+ successful wallet creation rate
- <5% user drop-off during flow
- Positive user experience metrics
- Zero critical security issues
