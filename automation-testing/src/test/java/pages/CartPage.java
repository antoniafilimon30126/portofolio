package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    private WebDriver driver;
    private By cartItem = By.cssSelector(".cart_item .inventory_item_name");
    private By cartItems = By.cssSelector(".cart_item");
    private By removeBackpackBtn = By.id("remove-sauce-labs-bike-light");
    private By continueShoppingBtn = By.id("continue-shopping");
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductDisplayedInCart(){
        return driver.findElement(cartItem).isDisplayed();
    }
    public String getProductName(){
        return driver.findElement(cartItem).getText();
    }
    public void removeBackpackFromCart() {
        driver.findElement(removeBackpackBtn).click();
    }
    public int getNumberOfProductsInCart() {
        return driver.findElements(cartItems).size();
    }
    public void continueShopping(){
        driver.findElement(continueShoppingBtn).click();
    }
}
