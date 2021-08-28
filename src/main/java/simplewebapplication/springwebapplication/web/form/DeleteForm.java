package simplewebapplication.springwebapplication.web.form;

import lombok.Data;

@Data
public class DeleteForm {

    private String userId;
    private String confirmPassword;

    public DeleteForm() {
    }

    public DeleteForm(String userId, String confirmPassword) {
        this.userId = userId;
        this.confirmPassword = confirmPassword;
    }

    public DeleteForm(String userId) {
        this.userId = userId;
    }
}
