package simplewebapplication.springwebapplication.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.domain.user.UserRoleType;
import simplewebapplication.springwebapplication.repository.user.UserRepository;

import java.util.HashSet;
import java.util.Set;

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

        // 중복 검사
        validateDuplicateMember(user);

        userRepository.save(user);

        return user.getId();
    }

    @Override
    public User findUser(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public String createReadOnlyUser() {

        // 우선 임의로 회원을 만듦
        User randomUser = makeRandomUserId();

        String joinedUser = join(randomUser);

        System.out.println("joinedUser = " + joinedUser);

        return joinedUser;
    }

    private void validateDuplicateMember(User user) {
        User findUser = userRepository.findById(user.getId());

        if (findUser != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    private User makeRandomUserId() {

        // 랜덤유저 만들기

        Set<Integer> set = new HashSet<>();

        while (set.size() < 5) {
            Double d = (Math.random() * 10000) % 26;
            set.add(d.intValue());
        }

        StringBuilder id = new StringBuilder();

        for (Integer num : set) {
            id.append((char)('A' + num));
        }

        String[] email = {"@naver.com", "@daum.net", "gmail.com", "nate.com"};

        Double d = (Math.random() * 10000) % 4;

        return new User(id.toString(), "4321", id + email[d.intValue()], UserRoleType.NON);
    }
}
