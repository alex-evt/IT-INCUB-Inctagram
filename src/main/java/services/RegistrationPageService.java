package services;

import API.tempmail.adapter.MailAdapter;
import io.qameta.allure.Step;
import models.User;
import pages.signUp.CongratulationPage;
import pages.signUp.PrivacyPolicyPage;
import pages.signUp.RegistrationPage;
import pages.signUp.TermsOfServicePage;

import static text.data.PagesURL.REGISTRATION_PAGE_URL;

public class RegistrationPageService extends HeaderComponentService<RegistrationPageService> {


    RegistrationPage registrationPage = new RegistrationPage();

    public TermsOfServicePage clickTermsOfService() {
        return registrationPage
                .open(REGISTRATION_PAGE_URL)
                .clickServiceTermsLink();
    }

    public PrivacyPolicyPage clickPrivacyPolicy() {
        return registrationPage
                .open(REGISTRATION_PAGE_URL)
                .clickPrivacyPolicyLink();
    }

    @Step("User registration without email confirmation")
    public RegistrationPage registrationWithoutConfirmation(User user) {
        registrationPage
                .open(REGISTRATION_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .fillInConfirmationPassword(user.getPasswordConfirm())
                .clickAgreementsCheckbox()
                .clickSignUp();
        return new RegistrationPage();
    }

    @Step("User registration with email confirmation")
    public CongratulationPage registrationWithConfirmation(MailAdapter tempEmail , User user) {
        new RegistrationPage()
                .open(REGISTRATION_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .fillInConfirmationPassword(user.getPasswordConfirm())
                .clickAgreementsCheckbox()
                .clickSignUp()
                .clickOkButton();

        wait(10000);
        String confirmationLink = tempEmail.getLastMessageConfirmationLink();
        new CongratulationPage().open(confirmationLink);
        wait(10000);
        return new CongratulationPage();
    }

    public String getCurrentLanguage() {
        return registrationPage
                .getHeader()
                .getCurrentLanguage();
    }

    public RegistrationPageService openRegistrationPage() {
        registrationPage.open(REGISTRATION_PAGE_URL);
        return this;
    }

    public void wait(int seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
