package simplewebapplication.springwebapplication.domain.user;

import simplewebapplication.springwebapplication.domain.board.Board;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id // @GeneratedValue // GeneratedValue 는 기본 키 매핑 자동 생성 방법임.
    @Column(name = "user_id", length = 30, nullable = false, unique = true)
    private String id;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @Column(length = 150)
    private String token;

    @Column(length = 150)
    private String tokenExp;

    @Column(length = 100)
    private String avatar;

    /**
     * 게시글 작성자가 본인의 글만을 확인하고 싶을 경우 필요한 것이라 생각함.
     * mappedBy 는 연관관계 주인의 변수
     * boards 는 단순 읽기만 가능함.
     */
    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<>();

    protected User() {};

    public User(String id, String password, String email) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.role = UserRoleType.USER;
    }

    // 우선은 Lombok 을 사용하지 않음
    // setter 는 사용하지 않음

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserRoleType getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }

    public String getTokenExp() {
        return tokenExp;
    }

    public String getAvatar() {
        return avatar;
    }
}
