package dropboxweb;

import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SignInBlock;

import java.util.List;

import static org.testng.Assert.fail;

/**
 * Created by ginger on 16.05.16.
 */

public class CreateFolderPositiveTest extends BaseTest {
    String alphabeticString = "create" + RandomStringUtils.randomAlphabetic(5);
    String numericString = "create" + RandomStringUtils.randomNumeric(5);
    String minSizeName = "create" + RandomStringUtils.randomAlphabetic(1);
    String maxSizeName = "create" + RandomStringUtils.randomAlphabetic(249);
    String alreadyExistingName = "createOlderFolder";

    @DataProvider(name = "validFolderNames")
    public Object[][] simpleDataProvider() {
        return new Object[][]{
                {minSizeName},
                {alphabeticString},
                {numericString},
                {maxSizeName},
                {alreadyExistingName}
        };
    }

    @BeforeClass()
    public void signIn() throws DbxException {
        List<DbxEntry> listOfFiles = client.searchFileAndFolderNames("/", "createOlderFolder");
        if (listOfFiles.size()!=0){
            client.createFolder("/createOlderFolder");
        }

        new SignInBlock(driver).signinAs(email, password);

        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("global-actions")));
    }

    @Test(dataProvider = "validFolderNames")
    public void createFolderWithValidName(String newFolderName){
        new HomePage(driver).clickToAddNewFolder();
        driver.switchTo().activeElement().sendKeys(newFolderName);
        driver.switchTo().activeElement().submit();

        try {
            wait.until(ExpectedConditions.attributeContains(By.id("notify"), "class", "server-success"));
        } catch(TimeoutException exception){
            fail("Error creating new folder with name: " + newFolderName);
        }
        System.out.println("Folder " + newFolderName + " was successfully created");
    }

    @AfterMethod
    public void refresh(){
        driver.navigate().refresh();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("global-actions")));
    }

    @AfterClass
    public void deleteFolders() throws DbxException {
        List<DbxEntry> listOfFiles = client.searchFileAndFolderNames("/", "create");
        listOfFiles.stream().forEach(file -> {
            try {
                client.delete("/" + file.name);
            } catch (DbxException e) {
                System.out.println("Error deleting " + file.name + " file");
            }
        });
    }
}
