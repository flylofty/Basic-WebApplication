package simplewebapplication.springwebapplication.web.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserLoginForm {

    private String id;
    private String password;

    public UserLoginForm() {
    }

    public UserLoginForm(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
