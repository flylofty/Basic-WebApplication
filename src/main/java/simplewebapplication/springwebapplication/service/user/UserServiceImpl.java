package simplewebapplication.springwebapplication.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.repository.user.UserRepository;

@Service // @Service 애노테이션은 컴포넌트 스캔의 대상임.
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional // 기본은 readOnly 이지만 이 부분은 DB에 써야하므로 애노테이션을 따로 붙임
    public String join(User user) {

        // 중복 검사 미구현...
        // 데이터베이스 "유니크" 제약 조건 고려...

        userRepository.save(user);

        return user.getId();
    }

    @Override
    public User findUser(String userId) {
        return userRepository.findById(userId);
    }
}
