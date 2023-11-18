package services;

import pages.components.HeaderComponent;

public abstract class HeaderComponentService<Page> {

    HeaderComponent<Page> headerComponent = new HeaderComponent<>();

    public Page clickLogo() {
        headerComponent.clickLogo();
        return (Page) this;
    }

    public Page changeLanguageToRussian() {
        headerComponent
                .clickLanguageChanger();
        headerComponent
                .clickRussianLanguage();
        return (Page) this;
    }


}
