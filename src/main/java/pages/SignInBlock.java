package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by ginger on 15.05.16.
 */
public class SignInBlock {
    By emailField = By.cssSelector("input.text-input-input.autofocus");
    By passwordField = By.cssSelector("input.password-input.text-input-input");
    By signinButton = By.cssSelector("button.login-button.button-primary");

    private final WebDriver driver;


    public SignInBlock(WebDriver driver) {
        this.driver = driver;
    }

    public SignInBlock typeUsername(String username) {
        driver.findElement(emailField).sendKeys(username);
        return this;
    }

    public SignInBlock typePassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
        return this;
    }

    public SignInBlock submitEmail() {
        driver.findElement(signinButton).submit();
        return new SignInBlock(driver);
    }

    public SignInBlock signinAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        return submitEmail();
    }
}