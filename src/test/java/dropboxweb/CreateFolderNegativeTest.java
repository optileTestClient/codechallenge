package dropboxweb;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignInBlock;
import util.ErrorDetail;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by ginger on 16.05.16.
 */
public class CreateFolderNegativeTest extends BaseTest{
    String emptyString = "";
    String moreThanMaxSizeName = RandomStringUtils.randomAlphabetic(256);
    String onlyDotName = ".";
    String doubleDotName = "..";
    String notAllowedSymbolsName = "\"";

    @DataProvider(name = "invalidFolderNames")
    public Object[][] simpleDataProvider() {
        return new Object[][]{
                {emptyString, ErrorDetail.PROVIDE_NAME_FOR_NEW_FOLDER.toString()},
                {moreThanMaxSizeName, ErrorDetail.MAX_FILE_NAME_LENGTH_IS.toString()},
                {onlyDotName, ErrorDetail.FILE_NAME_DOT_NOT_ALLOWED.toString()},
                {doubleDotName, ErrorDetail.FILE_NAME_DOT_NOT_ALLOWED.toString()},
                {notAllowedSymbolsName, ErrorDetail.FOLLOWING_CHARACTERS_ARE_NOT_ALLOWED.toString()}
        };
    }

    @BeforeClass()
    public void signIn(){
        new SignInBlock(driver).signinAs(email, password);

        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("global-actions")));
    }

    @Test(dataProvider = "invalidFolderNames")
    public void tryCreateFolderWithInvalidName(String newFolderName, String expectedError){
        new HomePage(driver).clickToAddNewFolder();
        driver.switchTo().activeElement().sendKeys(newFolderName);
        driver.switchTo().activeElement().submit();

        try {
            wait.until(ExpectedConditions.attributeContains(By.id("notify"), "class", "server-error"));
        } catch(TimeoutException exception){
            fail("No error creating new folder with name: " + newFolderName);
        }
        String gotErrorText = driver.findElement(By.id("notify-msg")).getText();
        assertEquals(gotErrorText, expectedError, "Incorrect error message");
    }

    @AfterMethod
    public void refresh(){
        driver.navigate().refresh();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("global-actions")));
    }
}
