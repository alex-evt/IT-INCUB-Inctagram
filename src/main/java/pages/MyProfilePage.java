package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.Waiter;

public class MyProfilePage extends BaseModel {

    @FindBy(xpath = "//a[@href='/home']")
    private WebElement homeButton;

    @FindBy(xpath = "//button[contains(text(), 'Create')]")
    private WebElement createButton;

    @FindBy(xpath = "//a[text()='My profile']")
    private WebElement myProfileButton;

    @FindBy(xpath = "//a[@href='/messenger']")
    private WebElement messengerButton;

    @FindBy(xpath = "//a[@href='/search']")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@href='/favourites']")
    private WebElement favouritesButton;

    @FindBy(xpath = "//a[@href='/statistics']")
    private WebElement statisticsButton;

    @FindBy(xpath = "//button[contains(text(), 'Log out')]")
    private WebElement logOutButton;

    @FindBy(xpath = "//a[@id='profile-link-to-settings-profile']")
    private WebElement profileSettingsButton;

    public MyProfilePage clickHomeButton() { //?
        homeButton.click();
        return this;
    }

    public String getTextHomeButton() {
        return Waiter.waitVisibilityOfElement10Second(homeButton).getText();
    }

    public MyProfilePage clickCreateButton() { //?
        createButton.click();
        return this;
    }

    public MyProfilePage clickMyProfileButton() {
        myProfileButton.click();
        return this;
    }
}
