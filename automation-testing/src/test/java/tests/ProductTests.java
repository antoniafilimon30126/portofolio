package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.InventoryPage;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductTests extends BaseTest{
    @Test
    public void sortProductsByNameZA() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Thread.sleep(1500);

        //sortare produse
        inventoryPage.sortByNameZA();
        Thread.sleep(2000);
        // afisare nume produse dupa sortare
        List<String> actualProductNames = inventoryPage.getProductNames();
        Thread.sleep(1500);


        // creare lista dupa sortare
        List<String> expectedProductNames = new ArrayList<>(actualProductNames);
        Collections.sort(expectedProductNames, Collections.reverseOrder());
        Thread.sleep(1500);

        // verificare sortare
        Assert.assertEquals(
                actualProductNames,
                expectedProductNames,
                "Produsele nu sunt sortate corect dupa nume (Z → A)"
        );
    }

    @Test
    public void sortProductsByPriceLowToHigh() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        InventoryPage inventoryPage = new InventoryPage(driver);
        Thread.sleep(1500);

        //sortare produse
        inventoryPage.sortByLowToHigh();
        Thread.sleep(1500);
        // afisare nume produse dupa sortare
        double firstPrice = inventoryPage.getFirstProductPrice();
        double lastPrice = inventoryPage.getLastProductPrice();

        Assert.assertTrue(
                firstPrice <= lastPrice,
                "Produsele nu sunt sortate corect de la low la high"
        );
    }
    @Test
    public void openProductDetails() throws InterruptedException{

        // login
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        //deschidere detalii produs
        InventoryPage inventoryPage = new InventoryPage(driver);
        inventoryPage.openBackpackDetails();
        Thread.sleep(1500);

        Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory-item.html"),
                "Pagina de detalii a produsului nu a fost deschisa"
        );
    }
}
