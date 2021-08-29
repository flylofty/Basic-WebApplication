package simplewebapplication.springwebapplication.web.user;

import lombok.Getter;

@Getter
public class SessionUser {

    private String id;
    private String role;

    public SessionUser() {
    }

    public SessionUser(String id, String role) {
        this.id = id;
        this.role = role;
    }
}
