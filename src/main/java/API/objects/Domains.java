package API.objects;

import com.google.gson.Gson;
import io.restassured.response.Response;

import static API.adapter.BaseAdapter.requestGET;

public class Domains {


    public static String getFirstRandomDomain() {
        Response response = requestGET("/domains");
        Gson gson = new Gson();
        ContextDomain domainArray = gson.fromJson(response.asString(), ContextDomain.class);
        return domainArray.getHydraMember().get(0).getDomain();
    }
}
