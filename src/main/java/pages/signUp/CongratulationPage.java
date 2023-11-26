package pages.signUp;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import pages.signIn.LoginPage;
import utils.Waiter;

public class CongratulationPage extends BaseModel {

    @FindBy(xpath = "//a[@href='/sign-in']")
    private WebElement signInButton;

    @FindBy(xpath = "//p[contains(@class, 'text')]")
    private WebElement confirmEmailText;


    public LoginPage clickSignInButton() {
        Waiter.waitVisibilityOfElement10Second(signInButton).click();
        return new LoginPage();
    }


    public String getConfirmationText() {
        return Waiter.waitVisibilityOfElement5Second(confirmEmailText).getText();
    }

    @Step("Open {url}")
    public CongratulationPage open(String url) {
        driver.get(url);
        return this;
    }
}
