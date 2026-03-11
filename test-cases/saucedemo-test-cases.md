## Test Case 1: Login – Valid Credentials

**Preconditions:**
1. Application is open on the login page.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Enter a valid username. | Username is accepted. | OK |
| Enter a valid password. | Password is accepted. | OK |
| Click the Login button. | User is authenticated and redirected to the home page. | OK |

**TC STATUS:** PASSED

---

## Test Case 2: Login – Invalid Password

**Preconditions:**
1. Application is open on the login page.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Enter a valid username. | Username is accepted. | OK |
| Enter an invalid password. | Password is entered. | OK |
| Click the Login button. | An error message is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 3: Login – Empty Fields

**Preconditions:**
1. Application is open on the login page.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Leave required fields empty. | Fields remain empty. | OK |
| Click the Login button. | Error message for missing fields is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 4: Cart – Add Product to Cart

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Select a product. | Product details are displayed. | OK |
| Click the "Add to Cart" button. | Product is added to the cart. | OK |

**TC STATUS:** PASSED

---

## Test Case 5: Cart – Remove Product from Cart

**Preconditions:**
1. User is logged in.
2. A product is already in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart. | Cart page is displayed. | OK |
| Click the "Remove" button. | Product is removed from the cart. | OK |

**TC STATUS:** PASSED

---

## Test Case 6: Cart – Add Multiple Products

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Select a product. | Product details are displayed. | OK |
| Click the "Add to Cart" button. | Product is added to the cart. | OK |
| Select another product. | Product details are displayed. | OK |
| Click the "Add to Cart" button. | Second product is added to the cart. | OK |
| Open the cart. | All selected products are displayed in the cart. | OK |

**TC STATUS:** PASSED

## Test Case 7: Checkout – Successful Checkout

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Enter all required personal details. | Data is accepted. | OK |
| Click the "Continue" button. | Checkout overview page is displayed. | OK |
| Click the "Finish" button. | Order is completed successfully. | OK |

**TC STATUS:** PASSED

---

## Test Case 8: Checkout – Missing First Name

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Enter Last Name and Postal Code only. | First Name field remains empty. | OK |
| Click the "Continue" button. | Error message is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 9: Checkout – Missing Last Name

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Enter First Name and Postal Code only. | Last Name field remains empty. | OK |
| Click the "Continue" button. | Error message is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 10: Checkout – Missing Postal Code

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Enter First Name and Last Name only. | Postal Code field remains empty. | OK |
| Click the "Continue" button. | Error message is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 11: Checkout – Cancel from Step One

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Click the "Cancel" button. | User is redirected back to the cart page. | OK |

**TC STATUS:** PASSED

---

## Test Case 12: Checkout – Cancel from Overview

**Preconditions:**
1. User is logged in.
2. At least one product is in the cart.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the cart page. | Cart page is displayed. | OK |
| Click the "Checkout" button. | Checkout page is opened. | OK |
| Fill all required fields. | Data is accepted. | OK |
| Click the "Continue" button. | Checkout overview page is displayed. | OK |
| Click the "Cancel" button. | User is redirected to the products page. | OK |

**TC STATUS:** PASSED

---

## Test Case 13: Navigation – Continue Shopping

**Preconditions:**
1. User is logged in.
2. Cart page is opened.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Click the "Continue Shopping" button. | User is redirected to the products page. | OK |

**TC STATUS:** PASSED

---

## Test Case 14: Product – View Product Details

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Click on a product from the product list. | Product details page is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 15: Products – Sort Products

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the sorting dropdown. | Sorting options are displayed. | OK |
| Select a sorting option. | Products are sorted according to the selected option. | OK |

**TC STATUS:** PASSED

---

## Test Case 16: Navigation – Open Menu

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Click the menu button. | Navigation menu is displayed. | OK |

**TC STATUS:** PASSED

---

## Test Case 17: Navigation – Close Menu

**Preconditions:**
1. User is logged in.
2. Navigation menu is open.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Click the "X" button in the menu. | Navigation menu is closed. | OK |

**TC STATUS:** PASSED

---

## Test Case 18: Navigation – Logout

**Preconditions:**
1. User is logged in.

| Step Actions | Expected Results | Result Status |
|---|---|---|
| Open the navigation menu. | Menu is displayed. | OK |
| Click the "Logout" button. | User is redirected to the login page. | OK |

**TC STATUS:** PASSED
