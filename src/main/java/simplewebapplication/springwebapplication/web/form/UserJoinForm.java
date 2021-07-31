package simplewebapplication.springwebapplication.web.form;

import lombok.Data;

@Data
public class UserJoinForm {

    private String userId;
    private String password;
    private String rePassword;
    private String email;

    public UserJoinForm() {
    }

    public UserJoinForm(String userId, String password, String rePassword, String email) {
        this.userId = userId;
        this.password = password;
        this.rePassword = rePassword;
        this.email = email;
    }
}
