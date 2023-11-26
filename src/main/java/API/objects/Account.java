package API.objects;

import lombok.Data;


@Data
public class Account {

    private String id;
    private String email;
    private String quota;
    private String used;
    private boolean isDisabled;
    private boolean isDeleted;
    private String createdAt;
    private String updatedAt;

    public Account() {
        this.id = "";
        this.email = "";
        this.quota = "";
        this.used = "";
        this.isDisabled = false;
        this.isDeleted = false;
        this.createdAt = "";
        this.updatedAt = "";
    }

    public Account(String id, String email, String quota, String used, boolean isDisabled, boolean isDeleted, String createdAt, String updatedAt) {
        this.id = id;
        this.email = email;
        this.quota = quota;
        this.used = used;
        this.isDisabled = isDisabled;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
