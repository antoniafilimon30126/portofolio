## Bug 1: Checkout allows submission with empty Postal Code

**Severity:** Medium  
**Priority:** High

**Environment**
Browser: Chrome  
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login with valid credentials
2. Add a product to the cart
3. Open Cart and click Checkout
4. Enter First Name and Last Name
5. Leave Postal Code empty
6. Click Continue

**Expected result**
User should receive a validation error indicating that the Postal Code field is required.

**Actual result**
The checkout process continues without validation.


## Bug 2: Cart badge update delay after removing product

**Severity:** Low  
**Priority:** Medium

**Environment**
Browser: Chrome
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login
2. Add a product to the cart
3. Open cart
4. Remove the product

**Expected result**
Cart badge should disappear immediately.

**Actual result**
Cart badge remains visible until page refresh.

## Bug 3: Product sorting not applied correctly

**Severity:** Medium  
**Priority:** Medium

**Environment**
Browser: Chrome
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login
2. Open sorting dropdown
3. Select "Price (low to high)"

**Expected result**
Products should be sorted by ascending price.

**Actual result**
Product order does not change correctly.

## Bug 4: Navigation menu closes when clicking outside

**Severity:** Low  
**Priority:** Low

**Environment**
Browser: Chrome
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login
2. Open navigation menu
3. Click outside the menu area

**Expected result**
Menu should remain open until closed by user.

**Actual result**
Menu closes automatically.

## Bug 5: Cart remains accessible after logout via browser back button

**Severity:** Medium  
**Priority:** Medium

**Environment**
Browser: Chrome  
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login with valid credentials
2. Add a product to the cart
3. Logout from the application
4. Press the browser Back button

**Expected result**
User should remain logged out and be redirected to the login page.

**Actual result**
Cart page becomes visible even after logout.

## Bug 6: Product details page does not highlight selected product

**Severity:** Low  
**Priority:** Low

**Environment**
Browser: Chrome
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login to the application
2. Click on any product from the product list

**Expected result**
Selected product should be clearly highlighted or visually distinguished.

**Actual result**
Product details page opens but there is no visual indication that the product was selected from the list.

## Bug 7: Checkout fields allow numeric characters in name fields

**Severity:** Medium  
**Priority:** Medium

**Environment**
Browser: Chrome  
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login to the application
2. Add a product to the cart
3. Click Checkout
4. Enter numbers in the First Name or Last Name fields
5. Click Continue

**Expected result**
Name fields should only accept alphabetic characters.

**Actual result**
Numeric values are accepted in the First Name and Last Name fields.

## Bug 8: Sorting dropdown resets after page refresh

**Severity:** Low  
**Priority:** Low

**Environment**
Browser: Chrome
OS: macOS 15.7.4
Device: MacBook

**Steps to reproduce**
1. Login to the application
2. Select a sorting option (e.g., Price low to high)
3. Refresh the page

**Expected result**
Selected sorting option should remain active.

**Actual result**
Sorting option resets to the default value after refresh.

