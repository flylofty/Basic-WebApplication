package simplewebapplication.springwebapplication.dto.user;

import lombok.Data;

@Data
public class UserJoinFormDTO {

    private String userId;
    private String password;
    private String rePassword;
    private String email;

    public UserJoinFormDTO() {
    }

    public UserJoinFormDTO(String userId, String password, String rePassword, String email) {
        this.userId = userId;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
    }
}
