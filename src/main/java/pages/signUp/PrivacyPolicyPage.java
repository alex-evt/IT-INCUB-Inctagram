package pages.signUp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import utils.Waiter;

public class PrivacyPolicyPage extends BaseModel {

    @FindBy(xpath = "//p")
    private WebElement privacyText;

    public String getPrivacyText() {
        return Waiter.waitVisibilityOfElement5Second(privacyText).getText();
    }
}
