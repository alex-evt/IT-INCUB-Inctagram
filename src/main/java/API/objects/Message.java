package API.objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String id;
    private String msgid;
    private String downloadUrl;
    private String sourceUrl;

}
