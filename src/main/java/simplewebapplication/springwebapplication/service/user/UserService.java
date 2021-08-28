package simplewebapplication.springwebapplication.service.user;

import simplewebapplication.springwebapplication.domain.user.User;

public interface UserService {

    // 회원 가입
    String join(User user);

    // 로그인
    User login(String id, String password);

    // 회원 조회
    User findUser(String userId);

    // 비회원 로그인
    String createReadOnlyUser();

    // 현재 비밀번호 확인
    boolean confirmCurrentPassword(String formUserId, String formCurrentPassword);

    // 비밀번호 변경
    void changePassword(String userId, String newPassword);

    // 회원 탈퇴
    void deleteAccount(String userId);
}
