package API.objects;


import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class ContextMessage {

    @SerializedName("hydra:member")
    ArrayList<Message> hydraMember;

}
