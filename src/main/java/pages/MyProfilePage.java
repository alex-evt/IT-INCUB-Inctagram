package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.signIn.LoginPage;
import utils.Waiter;

public class MyProfilePage extends BaseModel {

    @FindBy(xpath = "//a[@href='/home']")
    private WebElement homeButton;

    @FindBy(xpath = "//button[@class='MyProfile_nav__item__Tf5iy']")
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

    @FindBy(xpath = "//button[@class='MyProfile_nav__btn__FZ_2X']")
    private WebElement logOutButton;

    @FindBy(xpath = "//a[@id='profile-link-to-settings-profile']")
    private WebElement profileSettingsButton;

    //modal Logout
    @FindBy(xpath = "//button[text()='Yes']")
    private WebElement logoutYesButton;

    @FindBy(xpath = "//button[text()='No']")
    private WebElement logoutNoButton;


    public MyProfilePage clickHomeButton() { //?
        homeButton.click();
        return this;
    }

    @Step("Get 'Home' button text")
    public String getTextHomeButton() {
        return Waiter.waitVisibilityOfElement10Second(homeButton).getText();
    }

    @Step("Click 'Log out' button")
    public MyProfilePage clickLogOutButton() {
        Waiter.waitVisibilityOfElement5Second(logOutButton).click();
        return this;
    }


    @Step("Click 'Yes' in logout modal")
    public LoginPage clickYesButton() {
        Waiter.waitVisibilityOfElement5Second(logoutYesButton).click();
        return new LoginPage();
    }

    @Step("Click 'No' in logout modal")
    public MyProfilePage clickNoButton() {
        Waiter.waitVisibilityOfElement5Second(logoutNoButton).click();
        return new MyProfilePage();
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
