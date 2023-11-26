package services;

import io.qameta.allure.Step;
import pages.MyProfilePage;
import pages.signIn.LoginPage;

public class MyProfileService {

    @Step("Logout from the account")
    public LoginPage logOut(){
        new MyProfilePage().clickLogOutButton().clickYesButton();
        return new LoginPage();
    }

    @Step("Cancel logout from the account")
    public MyProfilePage cancelLogout(){
        new MyProfilePage().clickLogOutButton().clickNoButton();
        return new MyProfilePage();
    }

}
