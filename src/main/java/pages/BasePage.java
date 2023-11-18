package pages;

import pages.components.HeaderComponent;

public abstract class BasePage<Header extends HeaderComponent> extends BaseModel {

    public abstract Header getHeader();
}
