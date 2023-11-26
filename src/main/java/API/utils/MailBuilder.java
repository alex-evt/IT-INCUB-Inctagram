package API.utils;

import API.adapter.BaseAdapter;
import API.adapter.MailAdapter;
import API.objects.Domains;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;

import javax.security.auth.login.LoginException;

import static API.utils.StringConstant.ACCOUNTS_ENDPOINT;
import static API.utils.StringConstant.TOKEN_ENDPOINT;

@Log4j2
public class MailBuilder extends BaseAdapter {


    public MailBuilder() {
    }


    public static MailAdapter login(String email, String password) throws LoginException {
        String jsonData = "{\"address\" : \"" + email.trim().toLowerCase() + "\",\"password\" : \"" + password.trim().toLowerCase() + "\"}";
        Response response = postWithoutAuth(TOKEN_ENDPOINT, jsonData);
        if (response.statusCode() == 200) {
//            System.out.println("LOGIN TOKEN: " + response.jsonPath().getString("token"));
            return new MailAdapter(response.jsonPath().getString("token"), response.jsonPath().getString("id"));
        }
        if (response.statusCode() == 401) {
            throw new LoginException(response.statusCode() + " 'This account no longer exists'");
        } else {
            throw new LoginException();
        }
    }


    public static MailAdapter createEmail(String email, String password) throws LoginException {
        try {
            String jsonData = "{\"address\" : \"" + email.trim().toLowerCase() + "\",\"password\" : \"" + password.trim().toLowerCase() + "\"}";
            Response response = postWithoutAuth(ACCOUNTS_ENDPOINT, jsonData);
            if (response.statusCode() == 201) {
                log.info("Email '" + email + "' with password '" + password + "' was successfully created");
                return login(email.trim().toLowerCase(), password.trim());
            }
            if (response.statusCode() == 422) {
                String newEmail = createRandomEmail(password).getEmailAddress();
                log.error("Account with email " + email + " is already exists. Created new email " + newEmail);
                return login(newEmail, password);
            }
            if (response.getStatusCode() == 429) {
                throw new LoginException("Too many requests! Error 429 Rate limited");
            } else {
                throw new LoginException("Couldn't create an account! Try again");
            }
        } catch (Exception e) {
            log.error("Couldn't create an account. Error: " + e.getMessage());
            throw new LoginException(e.getMessage());
        }
    }


    public static MailAdapter createRandomEmail(String password) {
        try {
            String email = ApiUtility.createRandomString(11).trim().toLowerCase() + "@" + Domains.getFirstRandomDomain();
            return createEmail(email, password);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }
}
