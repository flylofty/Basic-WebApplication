package simplewebapplication.springwebapplication.repository.user;

import simplewebapplication.springwebapplication.domain.user.User;

public interface UserRepository {

    // 유저 정보 저장
    void save(User user);

    // 아이디로 한 유저 찾기
    User findById(String id);
}
