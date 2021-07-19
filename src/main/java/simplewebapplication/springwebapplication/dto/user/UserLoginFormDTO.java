package simplewebapplication.springwebapplication.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginFormDTO {

    private String id;
    private String password;

    public UserLoginFormDTO() {
    }

    public UserLoginFormDTO(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
