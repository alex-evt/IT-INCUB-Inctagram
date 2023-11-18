package pages.signUp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import utils.Waiter;

public class TermsOfServicePage extends BaseModel {

    @FindBy(xpath = "//p")
    private WebElement agreementText;

    public String getAgreementText() {
        return Waiter.waitVisibilityOfElement5Second(agreementText).getText();
    }
}
