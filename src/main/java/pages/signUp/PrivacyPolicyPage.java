package pages.signUp;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BaseModel;
import utils.Waiter;

public class PrivacyPolicyPage extends BaseModel {

    @FindBy(xpath = "//h1")
    private WebElement title;

    @FindBy(xpath = "//p")
    private WebElement privacyText;

    public String getTitle() {
        return title.getText();
    }

    public String getPrivacyText() {
        return Waiter.waitVisibilityOfElement5Second(privacyText).getText();
    }
}
