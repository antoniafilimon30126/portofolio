package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {

    private WebDriver driver;
    private By addToCartBackpackBtn = By.id("add-to-cart-sauce-labs-backpack");
    private By addToCartBikeLightBtn = By.id("add-to-cart-sauce-labs-bike-light");
    private By cartIcon = By.className("shopping_cart_link");
    private By bikeLightProductLink = By.id("item_0_title_link");
    private By backpackProductLink = By.id("item_4_title_link");
    private By sortDropdown = By.className("product_sort_container");
    private By productPrices = By.className("inventory_item_price");
    private By productNames = By.className("inventory_item_name");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");
    private By sideMenu = By.className("bm-menu");
    private By closeMenuBtn = By.id("react-burger-cross-btn");
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
    }
   public void addBackpackToCart() {
        driver.findElement(addToCartBackpackBtn).click();
    }

    public void addBikeLightToCart() {
        driver.findElement(addToCartBikeLightBtn).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }
    public void openBikeLightDetails() {
        driver.findElement(bikeLightProductLink).click();
    }

    public void openBackpackDetails() {
        driver.findElement(backpackProductLink).click();
    }

    public void sortByNameZA(){
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Name (Z to A)");
    }

    public void sortByLowToHigh(){
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText("Price (low to high)");
    }

    public double getFirstProductPrice() {
        List<WebElement> prices = driver.findElements(productPrices);
        return Double.parseDouble(prices.get(0).getText().replace("$", ""));
    }

    public double getLastProductPrice() {
        List<WebElement> prices = driver.findElements(productPrices);
        return Double.parseDouble(
                prices.get(prices.size() - 1).getText().replace("$", "")
        );
    }
    public List<String> getProductNames() {
        return driver.findElements(productNames)
                .stream()
                .map(e -> e.getText())
                .collect(Collectors.toList());
    }
    public void openMenu() {
        driver.findElement(menuButton).click();
    }

    public void logout() {
        driver.findElement(logoutLink).click();
    }

    public boolean isMenuDisplayed(){
        return driver.findElement(By.className("bm-menu")).isDisplayed();
    }

    public void closeMenu(){
        driver.findElement(closeMenuBtn).click();
    }
}
