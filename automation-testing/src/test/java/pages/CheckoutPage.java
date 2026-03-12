package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;
    private By checkoutBtn = By.id("checkout");
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueBtn = By.id("continue");
    private By cancelBtn = By.id("cancel");
    private By finishBtn = By.id("finish");
    private By confirmationMsg = By.className("complete-header");
    private By errorMessage = By.cssSelector("h3[data-test='error']");
    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }
    public void startCheckout(){
        driver.findElement(checkoutBtn).click();
    }
    public void fillPersonalInfo(String firstName, String lastName, String postalCode){
        driver.findElement(firstNameField).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }
    public void continueCheckout(){
        driver.findElement(continueBtn).click();
    }
    public void cancelCheckout() {
        driver.findElement(cancelBtn).click();
    }
    public void finishCheckout(){
        driver.findElement(finishBtn).click();
    }
    public String getConfirmationMessage(){
        return driver.findElement(confirmationMsg).getText();
    }
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
