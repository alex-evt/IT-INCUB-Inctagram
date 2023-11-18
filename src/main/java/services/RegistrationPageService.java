package services;

import io.qameta.allure.Step;
import models.User;
import pages.external.temp.mail.TempMailPage;
import pages.signUp.CongratulationPage;
import pages.signUp.PrivacyPolicyPage;
import pages.signUp.RegistrationPage;
import pages.signUp.TermsOfServicePage;

import static text.data.PagesURL.REGISTRATION_PAGE_URL;
import static text.data.PagesURL.TEMP_MAIL_URL;

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


    @Step("User registration without email confirmation")
    public RegistrationPage fillInAllFieldsAndClickAgreements(User user) {
        registrationPage
                .open(REGISTRATION_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .fillInConfirmationPassword(user.getPasswordConfirm())
                .clickAgreementsCheckbox();
        return new RegistrationPage();
    }


    public RegistrationPage registrationWithoutClickSignUp(User user) {
        registrationPage
                .open(REGISTRATION_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .fillInConfirmationPassword(user.getPasswordConfirm())
                .clickAgreementsCheckbox();
        return new RegistrationPage();
    }

    @Step("Open temporary mail page and get email")
    public String openTempMailAndGetCurrentEmail() {
        return new TempMailPage()
                .open(TEMP_MAIL_URL).getCurrentEmail();
    }

    @Step("User registration with email confirmation")
    public CongratulationPage registrationWithConfirmation(User user) {
        new TempMailPage()
                .openNewTabToRegistrationPage(REGISTRATION_PAGE_URL)
                .fillInUserName(user.getUserName())
                .fillInEmail(user.getEmail())
                .fillInPassword(user.getPassword())
                .fillInConfirmationPassword(user.getPasswordConfirm())
                .clickAgreementsCheckbox()
                .clickSignUp()
                .clickOkButton();

        new RegistrationPage()
                .switchToTempEmailPage();

        new TempMailPage()
                .getReceivedMail()
                .switchToEmailFrame()
                .getSetUpAccountButtonInEmail();
        return new TempMailPage().switchToCongratulationPage();
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
