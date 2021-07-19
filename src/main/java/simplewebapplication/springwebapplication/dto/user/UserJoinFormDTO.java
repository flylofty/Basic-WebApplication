package simplewebapplication.springwebapplication.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserJoinFormDTO {

    private String id;
    private String password;
    private String rePassword;
    private String email;

    public UserJoinFormDTO() {
    }

    public UserJoinFormDTO(String id, String password, String rePassword, String email) {
        this.id = id;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
    }
}
