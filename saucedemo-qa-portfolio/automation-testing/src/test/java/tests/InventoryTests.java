package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

public class InventoryTests extends BaseTest {

    @Test
    public void openNavigationMenu() throws InterruptedException{
        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        // deschidere meniu
        inventoryPage.openMenu();
        Thread.sleep(1500);

        // verificare
        Assert.assertTrue(
                inventoryPage.isMenuDisplayed(),
                "Meniul de navigare nu a fost afisat"
        );
    }

    @Test
    public void closeNavigationMenu() throws InterruptedException{
        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        // deschidere meniu
        inventoryPage.openMenu();
        Thread.sleep(1500);

        inventoryPage.closeMenu();
        Thread.sleep(1500);

        // verificare
        Assert.assertFalse(
                inventoryPage.isMenuDisplayed(),
                "Meniul de navigare nu a fost inchis"
        );
    }
}
