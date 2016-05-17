package dropboxweb;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxRequestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.util.Locale;

/**
 * Created by ginger on 16.05.16.
 */
public abstract class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public DbxClient client;
    String accessToken = "F8YpKIAn2zAAAAAAAAAAJYaElEPSBiH62Zczpivev5buItIub7A0unC2kGrmdELT";
    String url = "https://www.dropbox.com/home";
    String email = "optiletestclient@gmail.com";
    String password = "at-tester1";

    @BeforeClass
    public void init() throws IOException {
        DbxRequestConfig config = new DbxRequestConfig("dropbox", Locale.ENGLISH.toString());
        client = new DbxClient(config, accessToken);
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages", Locale.ENGLISH.toString());
        driver = new FirefoxDriver(profile);
        driver.get(url);
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("regular-login-forms")));
    }

    @AfterClass
    public void quit(){
        driver.quit();
    }

}
