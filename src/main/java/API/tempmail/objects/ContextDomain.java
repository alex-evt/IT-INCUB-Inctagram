package API.tempmail.objects;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class ContextDomain {

    @SerializedName("hydra:member")
    ArrayList<Domain> hydraMember;

}
