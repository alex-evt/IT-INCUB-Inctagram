package API.tempmail.adapter;

import API.tempmail.objects.Account;
import API.tempmail.objects.ContextMessage;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;


import static API.tempmail.utils.StringConstant.MESSAGES_ENDPOINT;
import static API.tempmail.utils.StringConstant.ME_ENDPOINT;

@Log4j2
public class MailAdapter extends BaseAdapter {

    private final String bearerToken;
    private final String id;


    public MailAdapter(String bearerToken, String id) {
        this.bearerToken = bearerToken;
        this.id = id;
    }

    private static Account mailUtil(Response response) {
        String id = response.jsonPath().get("id").toString();
        String email = response.jsonPath().get("address").toString();
        String quota = response.jsonPath().get("quota").toString();
        String used = response.jsonPath().get("used").toString();
        boolean isDisabled = response.jsonPath().getBoolean("isDisabled");
        boolean isDeleted = response.jsonPath().getBoolean("isDeleted");
        String createdAt = response.jsonPath().get("createdAt").toString();
        String updatedAt = response.jsonPath().get("updatedAt").toString();
        return new Account(id, email, quota, used, isDisabled, isDeleted, createdAt, updatedAt);
    }


    public Account getSelf() {
        try {
            Response response = getWithAuth(ME_ENDPOINT, bearerToken);
            if (response.statusCode() == 200) {
                return mailUtil(response);
            }
        } catch (Exception e) {
            log.error("");
        }
        return new Account();
    }

    private String getLastMessageDownloadURL() {
        String contextMessage = getWithAuth(MESSAGES_ENDPOINT, bearerToken).asString();
        ContextMessage context = converter.fromJson(contextMessage, ContextMessage.class);
        return context.getHydraMember().get(0).getDownloadUrl();
    }


    public String getLastMessageConfirmationLink() {
        Response res = getWithAuth(getLastMessageDownloadURL(), bearerToken);

        String html = res.asString();
        int startIndex = html.indexOf("https://inctagram");
        int endIndex = html.indexOf("\" style=3D");
        String link = html.substring(startIndex, endIndex);
        return link.replace("&amp;", "&").replaceAll("(?<!code|email)=+", "").replace("3D", "")
                .replaceAll("\\s", "");
    }

    public int getTotalMessage() {
        Response response = getWithAuth(MESSAGES_ENDPOINT, bearerToken);
        return response.jsonPath().getInt("'hydra:totalItems'");
    }


    public String getEmailAddress() {
        Response response = getWithAuth(ME_ENDPOINT, bearerToken);
        return response.jsonPath().getString("address");
    }

    public Response deleteEmailAccount() {
        return delete(bearerToken, id);
    }
}
