package pages.signUp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import pages.signIn.LoginPage;
import utils.Waiter;

import java.util.List;

public class CongratulationPage extends BaseModel {

    @FindBy(xpath = "//a[@href='/sign-in']")
    private WebElement signInButton;

    @FindBy(xpath = "//p[contains(@class, 'text')]")
    private WebElement confirmEmailText;


    @FindBy(xpath = "//div[contains(@class, 'Loader_app')]")
    private List<WebElement> loaders;
    @FindBy(xpath = "//div[contains(@class, 'Loader_app')]")
    private WebElement loader;


    public boolean isSignInDisplayed() {
//        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(driver.getWindowHandle());

        String originalWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(winHandle)) {
                driver.close();

                driver.switchTo().window(winHandle);
                break;
            }
        }
        return Waiter.waitVisibilityOfElement10Second(signInButton).isDisplayed();
    }

    public LoginPage getSignInButton() {
        signInButton.click();
        return new LoginPage();
    }

    public boolean isLoadersInvisible() {
        return Waiter.waitInvisibilityOfElements10Second(loaders);
    }

    public boolean isLoaderInvisible() {
        Waiter.waitVisibilityOfElement10Second(loader);
        return Waiter.waitInvisibilityOfElement10Second(loader);
    }


//    public String getConfirmEmailText() {
//        return Waiter.waitTextToPresentBe5Second(confirmEmailText);
//    }

    public String getConfirmEmailText() {
        driver.switchTo().window(driver.getWindowHandle());

        String originalWindow = driver.getWindowHandle();
        for (String winHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(winHandle)) {
                driver.close();

                driver.switchTo().window(winHandle);
                break;
            }
        }
        return Waiter.waitVisibilityOfElement5Second(confirmEmailText).getText();
    }
}
