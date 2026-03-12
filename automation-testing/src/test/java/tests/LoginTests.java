package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;
import tests.BaseTest;

public class LoginTests extends BaseTest {

    @Test
    public void loginWithValidCredentials() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        Thread.sleep(5000);

        Assert.assertTrue( driver.getCurrentUrl().contains("inventory.html"),
                "Userul NU a ajuns pe pagina de produse"
        );
    }

    @Test
    public void loginWithInvalidCredentials() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");
        Thread.sleep(3000);

        Assert.assertTrue( loginPage.getErrorMessage().contains("Epic sadface"),
                "Mesajul de eroare nu este afisat"
        );
    }

    @Test
    public void loginWithEmptyCredentials() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");
        Thread.sleep(3000);

        Assert.assertTrue( loginPage.getErrorMessage().contains("Epic sadface"),
                "Mesajul de eroare nu este afisat"
        );
    }

    @Test
    public void logoutUser() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);

        // open menu
        inventoryPage.openMenu();
        Thread.sleep(2000);

        // logout
        inventoryPage.logout();
        Thread.sleep(2000);

        // verify redirect to login page
        Assert.assertTrue(
                driver.getCurrentUrl().contains("saucedemo.com"),
                "Utilizatorul nu a fost redirectionat pe pagina de login dupa logout"
        );
    }
}
