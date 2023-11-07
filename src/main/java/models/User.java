package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class User {
    private String userName;
    private String email;
    private String password;
    private String passwordConfirm;
}
