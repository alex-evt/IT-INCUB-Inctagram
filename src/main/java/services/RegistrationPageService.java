package services;

import io.qameta.allure.Step;
import models.User;
import pages.signUp.PrivacyPolicyPage;
import pages.signUp.RegistrationPage;
import pages.signUp.TermsOfServicePage;

public class RegistrationPageService extends HeaderComponentService<RegistrationPageService> {

    private static final String REGISTRATION_PAGE_URL = "https://inctagram.vercel.app/sign-up";

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

    @Step("Регистрация пользователя")
    public RegistrationPage registration(User user) {
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


    public String getCurrentLanguage() {
        return registrationPage
                .getHeader()
                .getCurrentLanguage();
    }

    public RegistrationPageService openRegistrationPage() {
        registrationPage.open(REGISTRATION_PAGE_URL);
        return this;
    }
}
