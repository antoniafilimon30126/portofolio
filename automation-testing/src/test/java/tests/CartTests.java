package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

public class CartTests extends BaseTest{

    @Test
    public void addProductToCart() throws InterruptedException {

        //login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        //add to cart
        InventoryPage inventoryPage = new InventoryPage(driver);
        //inventoryPage.addBackpackToCart();
        //Thread.sleep(1500);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(1500);

        inventoryPage.goToCart();
        Thread.sleep(3000);

        //verif in cart
        CartPage cartPage = new CartPage(driver);

        Assert.assertTrue(
                cartPage.isProductDisplayedInCart(),
                "Produsul nu este afisat in cos"
        );

        Assert.assertEquals(
                cartPage.getProductName(),
                "Sauce Labs Bike Light",
                "Numele produsului din cos e incorect"
        );
    }

    @Test
    public void removeProductFromCart() throws InterruptedException{

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(1000);

        inventoryPage.goToCart();
        Thread.sleep(2000);

        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(
                cartPage.getNumberOfProductsInCart(),
                1,
                "Produsul nu a fost adaugat corect in cos"
        );

        cartPage.removeBackpackFromCart();
        Thread.sleep(1500);

        Assert.assertEquals(
                cartPage.getNumberOfProductsInCart(),
                0,
                "Produsul nu a fost eliminat din cos"
        );
    }

    @Test
    public void addMultipleProductsToCart() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        //add multiple products
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        Thread.sleep(1000);
        inventoryPage.addBikeLightToCart();
        Thread.sleep(1000);

        inventoryPage.goToCart();
        Thread.sleep(2000);

        CartPage cartPage = new CartPage(driver);

        Assert.assertEquals(
                cartPage.getNumberOfProductsInCart(),
                2,
                "Nu au fost adaugate ambele produse in cos"
        );
    }

    @Test
    public void continueShoppingFromCart() throws InterruptedException{
        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        // adaugare produse
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.addBackpackToCart();
        Thread.sleep(1000);

        inventoryPage.goToCart();
        Thread.sleep(2000);

        CartPage cartPage = new CartPage(driver);
        cartPage.continueShopping();
        Thread.sleep(1000);


        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "Utilizatorul nu a fost redirectionat pe pagina de produse dupa Continue Shopping"
        );
    }
}
