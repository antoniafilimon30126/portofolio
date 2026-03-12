package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckoutPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CheckoutTests extends BaseTest{

    @Test
    public void completeCheckoutFlow() throws InterruptedException{

        //login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(1000);

        inventoryPage.goToCart();
        Thread.sleep(2000);

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(2000);

        checkoutPage.fillPersonalInfo("Antonia", "Filimon", "12345");
        checkoutPage.continueCheckout();
        Thread.sleep(3000);

        checkoutPage.finishCheckout();
        Thread.sleep(2000);

        // verificare finala
        Assert.assertEquals(
                checkoutPage.getConfirmationMessage(),
                "Thank you for your order!",
                "Mesajul de confirmare nu este afisat corect"
        );
    }

    @Test
    public void checkoutWithoutFirstName() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(500);
        inventoryPage.goToCart();
        Thread.sleep(1000);

        // start checkout
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(1000);

        // completare informatii checkout fara FirstName
        checkoutPage.fillPersonalInfo("", "Filimon", "12345");
        checkoutPage.continueCheckout();
        Thread.sleep(1000);

        // verificare
        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: First Name is required",
                "Mesajul de eroare pentru First Name lipsa nu este afisat corect"
        );
    }

    @Test
    public void checkoutWithoutLastName() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(500);
        inventoryPage.goToCart();
        Thread.sleep(1000);

        // start checkout
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(1000);

        // completare informatii checkout fara LastName
        checkoutPage.fillPersonalInfo("Antonia", "", "12345");
        checkoutPage.continueCheckout();
        Thread.sleep(1000);

        // verificare
        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: Last Name is required",
                "Mesajul de eroare pentru Last Name lipsa nu este afisat corect"
        );
    }


    @Test
    public void checkoutWithoutPostalCode() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(500);
        inventoryPage.goToCart();
        Thread.sleep(1000);

        // start checkout
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(1000);

        // completare informatii checkout fara PostalCode
        checkoutPage.fillPersonalInfo("Antonia", "Filimon", "");
        checkoutPage.continueCheckout();
        Thread.sleep(1000);

        // verificare
        Assert.assertEquals(
                checkoutPage.getErrorMessage(),
                "Error: Postal Code is required",
                "Mesajul de eroare pentru Postal Code lipsa nu este afisat corect"
        );
    }

    @Test
    public void cancelCheckoutFromStepOne() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(500);
        inventoryPage.goToCart();
        Thread.sleep(1000);

        // start checkout
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(2000);

        // cancel checkout
        checkoutPage.cancelCheckout();
        Thread.sleep(2000);

        // verificare user ajuns la cos
        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart.html"),
                "Utilizatorul nu a fost redirecționat inapoi in cos"
        );
    }

    @Test
    public void cancelCheckoutFromOverview() throws InterruptedException{
        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(500);
        inventoryPage.goToCart();
        Thread.sleep(1000);

        // checkout step 1
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.startCheckout();
        Thread.sleep(500);

        checkoutPage.fillPersonalInfo("Antonia", "Filimon", "12345");
        checkoutPage.continueCheckout();
        Thread.sleep(1000);

        // cancel din overview
        checkoutPage.cancelCheckout();
        Thread.sleep(1000);

        // verificare redirectionare catre pagina de produse
        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Utilizatorul nu a fost redirectionat pe pagina de produse"
        );
    }

}

