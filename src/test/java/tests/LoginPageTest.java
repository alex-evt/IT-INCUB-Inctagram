package tests;

import models.User;
import org.testng.Assert;
import org.testng.annotations.Test;
import services.LoginPageService;

public class LoginPageTest extends BaseTest{

    @Test
    public void verifySuccessfulLogin() {
        String email = "13d9786547d4e2@beaconmessenger.com";
        String password = "VWASD1zvwd!";

        LoginPageService loginPageService = new LoginPageService();
        User user = User.builder()
                .email(email)
                .password(password).build();

       String homeButtonText = loginPageService
                .login(user)
                .getTextHomeButton();
        Assert.assertEquals(homeButtonText, "Home");
    }

}
