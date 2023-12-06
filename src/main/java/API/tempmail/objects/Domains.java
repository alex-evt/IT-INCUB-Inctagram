package API.tempmail.objects;

import API.tempmail.adapter.BaseAdapter;
import com.google.gson.Gson;
import io.restassured.response.Response;

public class Domains {


    public static String getFirstRandomDomain() {
        Response response = BaseAdapter.requestGET("/domains");
        Gson gson = new Gson();
        ContextDomain domainArray = gson.fromJson(response.asString(), ContextDomain.class);
        return domainArray.getHydraMember().get(0).getDomain();
    }
}
