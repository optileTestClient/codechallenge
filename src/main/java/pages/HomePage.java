package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by ginger on 15.05.16.
 */
public class HomePage {
    By addFileButton = By.cssSelector("img.sprite.sprite_web.s_web_upload_16");
    By addNewFolderButton = By.cssSelector("img.sprite.sprite_web.s_web_folder_add");
    By logOutHref = By.cssSelector(".account-dropdown > ul:nth-child(2) > li:nth-child(8) > a:nth-child(1)");
    By headerAccountMenu =
            By.cssSelector("button.header-nav-link.button-as-link.bubble-dropdown-target.bubble-dropdown-target");

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickToAddNewFolder() {
        driver.findElement(addNewFolderButton).click();
    }

    public void clickHeaderAccountMenu(){
        driver.findElement(headerAccountMenu).click();
    }

    public void clickLogOutHref(){
        driver.findElement(logOutHref).click();
    }

    public void clickToAddFile() {
        driver.findElement(addFileButton).click();
    }
}
