package dropboxweb;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.SignInBlock;
import util.ErrorDetail;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * Created by ginger on 16.05.16.
 */
public class LogInEmailNegativeTest  extends BaseTest{
    String emptyString = " ";
    String withoutSymbol = "ginger";
    String withoutUserName = "@gmail.com";
    String unsuitableEmail = "1@gmail.com";
    String withInvalidSymbol = "*@gmail.com";

    @DataProvider(name = "invalidEmail")
    public Object[][] simpleDataProvider() {
        return new Object[][]{
                {emptyString, ErrorDetail.ENTER_EMAIL.toString()},
                {withoutUserName, ErrorDetail.USERNAME_PORTION_OF_EMAIL_INVALID.toString()},
                {unsuitableEmail, ErrorDetail.INVALID_EMAIL_OR_PASSWORD.toString()},
                {withInvalidSymbol, ErrorDetail.EMAIL_ENTERED_INVALID.toString()},
                {withoutSymbol, ErrorDetail.EMAIL_ADDRESS_MUST_CONTAIN_SYMBOL.toString()}
        };
    }

    @Test(dataProvider = "invalidEmail")
    public void trySignInWithInvalidEmail(String invalidEmail, String expectedError){
        new SignInBlock(driver).signinAs(
                invalidEmail,
                password
        );
        try {
            wait.until(
                    ExpectedConditions.visibilityOfNestedElementsLocatedBy(
                            By.cssSelector("div.text-input-error-wrapper"),
                            By.cssSelector("span.error-message")
                    )
            );
        } catch(TimeoutException exception){
            fail("No error sign in with email: " + invalidEmail);
        }
        String gotErrorText = driver.findElement(By.cssSelector("span.error-message")).getText();
        assertEquals(gotErrorText, expectedError, "Incorrect error message");

    }

    @AfterMethod
    public void refresh(){
        driver.navigate().refresh();
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.credentials-form__fields")));
    }
}
