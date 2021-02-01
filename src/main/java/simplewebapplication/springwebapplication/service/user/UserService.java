package simplewebapplication.springwebapplication.service.user;

import simplewebapplication.springwebapplication.domain.user.User;

public interface UserService {

    // 회원 가입
    String join(User user);

    // 회원 조회
    User findUser(String userId);

    // 로그인
    // 로그아웃
    // 회원 탈퇴
}
