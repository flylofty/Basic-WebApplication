package simplewebapplication.springwebapplication.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simplewebapplication.springwebapplication.domain.user.User;
import simplewebapplication.springwebapplication.domain.user.UserRoleType;
import simplewebapplication.springwebapplication.repository.user.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service // @Service 애노테이션은 컴포넌트 스캔의 대상임.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional // 기본은 readOnly 이지만 이 부분은 DB에 써야하므로 애노테이션을 따로 붙임
    public String join(User user) {

        // 중복 검사
        if (validateDuplicateMember(user)) {
            userRepository.save(user);
            return user.getId();
        }

        return null;
    }

    @Override
    public User login(String id, String password) {

        return userRepository.findById(id)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public User findUser(String userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 사용자 입니다.");
        }

        return optionalUser.get();
    }

    @Override
    public String createReadOnlyUser() {

        // 우선 임의로 회원을 만듦
        User randomUser = makeRandomUserId();

        String joinedUser = join(randomUser);

        System.out.println("joinedUser = " + joinedUser);

        return joinedUser;
    }

    @Override
    public boolean confirmCurrentPassword(String formUserId, String formCurrentPassword) {
        User findUser = this.findUser(formUserId);

        // 틀렸을 경우
        return findUser == null || !findUser.getPassword().equals(formCurrentPassword);
    }

    @Override
    @Transactional
    public void changePassword(String userId, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User findUser = optionalUser.get();
            findUser.changePassword(newPassword);
        }
    }

    @Override
    @Transactional
    public void deleteAccount(String userId) {
        userRepository.delete(userId);
    }

    private boolean validateDuplicateMember(User user) {

        Optional<User> optionalUser = userRepository.findById(user.getId());

        if (optionalUser.isPresent()) {
            //throw new IllegalStateException("이미 존재하는 회원입니다.");
            return false;
        }

        return true;
    }

    private User makeRandomUserId() {

        // 랜덤유저 만들기
        Set<Integer> set = new HashSet<>();

        while (set.size() < 5) {
            double d = (Math.random() * 10000) % 26;
            set.add((int) d);
        }

        StringBuilder id = new StringBuilder();

        for (Integer num : set) {
            id.append((char)('A' + num));
        }

        String[] email = {"@naver.com", "@daum.net", "gmail.com", "nate.com"};

        double d = (Math.random() * 10000) % 4;

        return new User(id.toString(), "4321", id + email[(int) d], UserRoleType.ADMIN);
    }
}
