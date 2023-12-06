package API.tempmail.objects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Domain {

    private String id;
    private String domain;
    private boolean isActive;
    private boolean isPrivate;
    private String createdAt;
    private String updatedAt;

}
