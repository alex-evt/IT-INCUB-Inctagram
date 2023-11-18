package pages.signIn;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;

public class ForgotPasswordPage extends BaseModel {

    @FindBy(xpath = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement sendLinkButton;

    @FindBy(xpath = "//a[@href='/sign-in']")
    private WebElement backToSignIn;

    @FindBy(xpath = "//span[@id='recaptcha-anchor']")
    private WebElement recaptcha;

}
