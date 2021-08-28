package simplewebapplication.springwebapplication.web.form;

import lombok.Data;

@Data
public class ChangePasswordForm {

    private String userId;
    private String currentPassword;
    private String newPassword;
    private String rePassword;

    public ChangePasswordForm() {
    }

    public ChangePasswordForm(String userId) {
        this.userId = userId;
    }
}
