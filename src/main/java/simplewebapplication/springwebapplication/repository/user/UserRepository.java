package simplewebapplication.springwebapplication.repository.user;

import simplewebapplication.springwebapplication.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    // 유저 정보 저장
    void save(User user);

    // 아이디로 한 유저 찾기
    Optional<User> findById(String id);
}
